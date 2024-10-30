package com.monframework.framework;

import com.monframework.framework.annotation.Controller;
import com.monframework.framework.annotation.FormField;
import com.monframework.framework.annotation.GET;
import com.monframework.framework.annotation.ModelAttribute;
import com.monframework.framework.annotation.POST;
import com.monframework.framework.annotation.Param;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monframework.framework.annotation.RestApi;
import com.monframework.framework.annotation.Url;
import com.monframework.framework.exception.FrameworkException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;


public class FrontController extends HttpServlet {
    
    private Map<String, Mapping> urlMappings = new HashMap<>();
    private String controllerPackage;
    private List<String> errors = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        ServletConfig config = getServletConfig();
        controllerPackage = config.getInitParameter("controller-package");
        
        if (controllerPackage == null || controllerPackage.isEmpty()) {
            errors.add("Erreur: Le package des contrôleurs n'est pas configuré dans web.xml");
            return;
        }
        
        scanControllersAndMethods();
        
        if (urlMappings.isEmpty()) {
            errors.add("Avertissement: Aucun contrôleur trouvé dans le package " + controllerPackage);
        }
    }

    private void scanControllersAndMethods() {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = controllerPackage.replace('.', '/');
            
            if (classLoader.getResources(path).hasMoreElements()) {
                Collections.list(classLoader.getResources(path))
                    .stream()
                    .map(url -> new File(url.getFile()))
                    .flatMap(dir -> Arrays.stream(dir.listFiles()))
                    .filter(file -> file.getName().endsWith(".class"))
                    .map(file -> {
                        String className = controllerPackage + '.' + 
                                        file.getName().replace(".class", "");
                        try {
                            return Class.forName(className);
                        } catch (ClassNotFoundException e) {
                            errors.add("Erreur: Classe non trouvée - " + className);
                            return null;
                        }
                    })
                    .filter(clazz -> clazz != null && clazz.isAnnotationPresent(Controller.class))
                    .forEach(this::processControllerMethods);
            }
        } catch (IOException e) {
            errors.add("Erreur lors du scan des contrôleurs: " + e.getMessage());
        }
    }

    private void processControllerMethods(Class<?> controllerClass) {
        Method[] methods = controllerClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Url.class)) {
                String url = method.getAnnotation(Url.class).value();
                String httpMethod = getHttpMethod(method);
                
                if (httpMethod == null) {
                    errors.add("Erreur: Méthode " + method.getName() + " doit avoir @GET ou @POST");
                    continue;
                }
                
                String mappingKey = httpMethod + ":" + url;
                
                if (urlMappings.containsKey(mappingKey)) {
                    Mapping existing = urlMappings.get(mappingKey);
                    errors.add("Erreur: URL dupliquée - " + mappingKey + 
                            " (Déjà mappée à " + existing.getClassName() + "." + 
                            existing.getMethodName() + ")");
                } else {
                    urlMappings.put(mappingKey, new Mapping(controllerClass.getName(), method.getName()));
                }
            }
        }
    }

    private String getHttpMethod(Method method) {
        if (method.isAnnotationPresent(GET.class)) {
            return "GET";
        } else if (method.isAnnotationPresent(POST.class)) {
            return "POST";
        }
        return null;
    }
    

    private Method findMethod(Class<?> controllerClass, String methodName, HttpServletRequest request) 
        throws NoSuchMethodException {
        for (Method method : controllerClass.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                // Vérification spéciale pour les méthodes avec paramètre Multipart
                Parameter[] parameters = method.getParameters();
                boolean hasMultipartParam = Arrays.stream(parameters)
                    .anyMatch(p -> p.getType().equals(Multipart.class));
                
                if (hasMultipartParam && !ServletFileUpload.isMultipartContent(request)) {
                    continue;
                }
                
                return method;
            }
        }
        throw new NoSuchMethodException("Méthode " + methodName + " non trouvée");
    }

    private <T> T createModelFromRequest(Class<T> modelClass, HttpServletRequest request) 
        throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        
        T model = modelClass.getDeclaredConstructor().newInstance();
        Field[] fields = modelClass.getDeclaredFields();
        
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = getFormFieldName(field);
            String paramValue = request.getParameter(fieldName);
            
            if (paramValue != null && !paramValue.isEmpty()) {
                Object convertedValue = convertParameterValue(paramValue, field.getType());
                field.set(model, convertedValue);
            }
        }
        
        return model;
    }

    private String getFormFieldName(Field field) {
        FormField formField = field.getAnnotation(FormField.class);
        return formField != null && !formField.value().isEmpty() 
               ? formField.value() 
               : field.getName();
    }

   private boolean isMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    private void handleMultipartRequest(HttpServletRequest request) throws Exception {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = upload.parseRequest(request);

        // Créez une map pour stocker tous les paramètres
        Map<String, Object> requestParams = new HashMap<>();

        for (FileItem item : items) {
            if (item.isFormField()) {
                // Pour les champs normaux
                requestParams.put(item.getFieldName(), item.getString("UTF-8"));
            } else {
                // Pour les fichiers
                if (item.getSize() > 0) {
                    Multipart multipart = new Multipart();
                    multipart.setFilename(item.getName());
                    multipart.setContentType(item.getContentType());
                    multipart.setInputStream(item.getInputStream());
                    multipart.setSize(item.getSize());
                    requestParams.put(item.getFieldName(), multipart);
                }
            }
        }

        // Stockez la map dans la requête
        request.setAttribute("multipartParams", requestParams);
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            if (isMultipart(request)) {
                handleMultipartRequest(request);
            }

            String requestURI = request.getRequestURI();
            String contextPath = request.getContextPath();
            String path = requestURI.substring(contextPath.length());
            String cleanPath = path.split("\\?")[0];
            String httpMethod = request.getMethod().toUpperCase();
            String mappingKey = httpMethod + ":" + cleanPath;

            // Debug logging
            System.out.println("Trying to access: " + mappingKey);
            System.out.println("Registered mappings: " + urlMappings.keySet());

            Mapping mapping = urlMappings.get(mappingKey);
            
            if (mapping == null) {
                // Utilisez forward au lieu de sendRedirect pour conserver le code d'erreur
                request.setAttribute("javax.servlet.error.status_code", 404);
                request.setAttribute("javax.servlet.error.message", "URL non trouvée: " + cleanPath);
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            try {
                Class<?> controllerClass = Class.forName(mapping.getClassName());
                Object controllerInstance = controllerClass.getDeclaredConstructor().newInstance();
                Method method = findMethod(controllerClass, mapping.getMethodName(), request);
                
                Object[] args = prepareMethodArguments(method, request);
                Object result = method.invoke(controllerInstance, args);
              
                // Gestion des méthodes REST API
                if (method.isAnnotationPresent(RestApi.class)) {
                    handleRestApiResponse(response, result);
                    return;
                }
                if (result instanceof String) {
                    response.getWriter().write(result.toString());
                } else if (result instanceof ModelView) {
                    ModelView modelView = (ModelView) result;
                     for (HashMap.Entry<String, Object> entry : modelView.getData().entrySet()) {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                        RequestDispatcher dispatcher = request.getRequestDispatcher(modelView.getUrl());
                        dispatcher.forward(request, response);
                        return;
                }
                else {
                    response.getWriter().write("<h1>Erreur: Type de retour non supporté</h1>");
                }
                
            } catch (InvocationTargetException e) {
                // Cette exception contient l'exception réelle lancée par la méthode
                Throwable targetException = e.getTargetException();
                if (targetException instanceof FrameworkException) {
                    ((FrameworkException) targetException).sendErrorResponse(request, response);
                } else {
                    new FrameworkException(
                        "Erreur interne: " + targetException.getMessage(),
                        500,
                        "/error.jsp"
                    ).sendErrorResponse(request, response);
                }
            }
            
        } catch (Exception e) {
            new FrameworkException(
                "Erreur inattendue: " + e.getMessage(),
                500,
                "/error.jsp"
            ).sendErrorResponse(request, response);
            e.printStackTrace();
        }
    }

    
    private void handleRestApiResponse(HttpServletResponse response, Object result) 
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        if (result != null) {
            String json = objectMapper.writeValueAsString(result);
            response.getWriter().write(json);
        } else {
            response.getWriter().write("{}");
        }
    }

    private Object[] prepareMethodArguments(Method method, HttpServletRequest request) {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        // Récupérez les paramètres multipart s'ils existent
        Map<String, Object> multipartParams = (Map<String, Object>) request.getAttribute("multipartParams");

        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            
            if (param.getType().equals(HttpServletRequest.class)) {
                args[i] = request;
                continue;
            }
            if (multipartParams != null) {
                Param paramAnnotation = param.getAnnotation(Param.class);
                if (paramAnnotation != null) {
                    args[i] = multipartParams.get(paramAnnotation.name());
                    continue;
                }
            }
            if (param.getType().equals(MySession.class)) {
                args[i] = new MySession(request.getSession());
                continue;
            }
            // Gestion des @ModelAttribute
            if (param.isAnnotationPresent(ModelAttribute.class)) {
                try {
                    args[i] = createModelFromRequest(param.getType(), request);
                } catch (Exception e) {
                    throw new RuntimeException("Erreur lors de la création du modèle", e);
                }
                continue;
            }
            // Gestion des @Param (existant)
            Param paramAnnotation = param.getAnnotation(Param.class);
            if (paramAnnotation != null) {
                String paramName = paramAnnotation.name();
                String paramValue = request.getParameter(paramName);
                args[i] = convertParameterValue(paramValue, param.getType());
            } else {
                args[i] = null;
            }
        }
        return args;
    }

    private Object convertParameterValue(String value, Class<?> targetType) {
        if (value == null) return null;
        
        if (targetType.equals(String.class)) {
            return value;
        } else if (targetType.equals(Integer.class) || targetType.equals(int.class)) {
            return Integer.parseInt(value);
        } else if (targetType.equals(Double.class) || targetType.equals(double.class)) {
            return Double.parseDouble(value);
        } else if (targetType.equals(Boolean.class) || targetType.equals(boolean.class)) {
            return Boolean.parseBoolean(value);
        }
        // Ajouter d'autres conversions au besoin
        return value;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}