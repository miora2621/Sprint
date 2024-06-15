package com.monframework.framework;

import com.monframework.framework.annotation.Controller;
import com.monframework.framework.annotation.GET;
import com.monframework.framework.annotation.Param;

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
            
            // Vérifier si le package existe
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
            } else {
                errors.add("Erreur: Le package " + controllerPackage + " n'existe pas ou est vide");
            }
            
        } catch (IOException e) {
            errors.add("Erreur lors du scan des contrôleurs: " + e.getMessage());
        }
    }

    private void processControllerMethods(Class<?> controllerClass) {
        Method[] methods = controllerClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(GET.class)) {
                GET getAnnotation = method.getAnnotation(GET.class);
                String url = getAnnotation.value();
                if (url.isEmpty()) {
                    url = "/" + controllerClass.getSimpleName().replace("Controller", "").toLowerCase() 
                          + "/" + method.getName();
                }
                
                // Vérification des URLs dupliquées
                if (urlMappings.containsKey(url)) {
                    errors.add("Erreur: URL dupliquée - " + url + 
                              " (Déjà mappée à " + urlMappings.get(url).getClassName() + "." + 
                              urlMappings.get(url).getMethodName() + ")");
                } else {
                    urlMappings.put(url, new Mapping(controllerClass.getName(), method.getName()));
                }
            }
        }
    }

    private Method findMethod(Class<?> controllerClass, String methodName) {
        for (Method method : controllerClass.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new RuntimeException("Méthode " + methodName + " non trouvée dans " + controllerClass.getName());
    }

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());
        String cleanPath = path.split("\\?")[0];
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            if (!errors.isEmpty()) {
                out.println("<h1>Erreurs de configuration</h1>");
                errors.forEach(error -> out.println("<p style='color:red'>" + error + "</p>"));
                return;
            }
            
            Mapping mapping = urlMappings.get(cleanPath);
            
            if (mapping != null) {
                try {
                    Class<?> controllerClass = Class.forName(mapping.getClassName());
                    Object controllerInstance = controllerClass.getDeclaredConstructor().newInstance();
                    
                    // Modification ici: Utiliser la nouvelle méthode findMethod
                    Method method = findMethod(controllerClass, mapping.getMethodName());
                    
                    Object[] args = prepareMethodArguments(method, request);
                    Object result = method.invoke(controllerInstance, args);
                    
                    if (result instanceof String) {
                        out.println(result.toString());
                    } 
                    else if (result instanceof ModelView) {
                        ModelView modelView = (ModelView) result;
                        for (HashMap.Entry<String, Object> entry : modelView.getData().entrySet()) {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                        RequestDispatcher dispatcher = request.getRequestDispatcher(modelView.getUrl());
                        dispatcher.forward(request, response);
                        return;
                    } 
                    else {
                        out.println("<h1>Erreur: Type de retour non supporté</h1>");
                    }
                    
                } catch (Exception e) {
                    out.println("<h1>Erreur lors de l'exécution</h1>");
                    out.println("<p style='color:red'>" + e.toString() + "</p>");
                    e.printStackTrace();
                }
            } else {
                out.println("<h1>Erreur 404</h1>");
                out.println("<p style='color:red'>Aucune méthode associée à ce chemin: " + cleanPath + "</p>");
            }
        }
    }

    private Object[] prepareMethodArguments(Method method, HttpServletRequest request) {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        
        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            
            // Si c'est HttpServletRequest, on l'injecte directement
            if (param.getType().equals(HttpServletRequest.class)) {
                args[i] = request;
                continue;
            }
            
            // Vérifier si le paramètre a l'annotation @Param
            Param paramAnnotation = param.getAnnotation(Param.class);
            if (paramAnnotation != null) {
                String paramName = paramAnnotation.name();
                String paramValue = request.getParameter(paramName);
                
                // Conversion du type si nécessaire
                args[i] = convertParameterValue(paramValue, param.getType());
            } else {
                args[i] = null; // Ou une valeur par défaut
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