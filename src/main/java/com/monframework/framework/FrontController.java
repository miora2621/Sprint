package com.monframework.framework;

import com.monframework.framework.annotation.Controller;
import com.monframework.framework.annotation.GET;

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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            // Afficher les erreurs d'initialisation s'il y en a
            if (!errors.isEmpty()) {
                out.println("<h1>Erreurs de configuration</h1>");
                errors.forEach(error -> out.println("<p style='color:red'>" + error + "</p>"));
                return;
            }
            
            String requestURI = request.getRequestURI();
            String contextPath = request.getContextPath();
            String path = requestURI.substring(contextPath.length());
            
            Mapping mapping = urlMappings.get(path);
            
            if (mapping != null) {
                try {
                    Class<?> controllerClass = Class.forName(mapping.getClassName());
                    Object controllerInstance = controllerClass.getDeclaredConstructor().newInstance();
                    Method method = controllerClass.getMethod(mapping.getMethodName());
                    
                    Object result = method.invoke(controllerInstance);
                    
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
                        out.println("<p>La méthode " + mapping.getMethodName() + " dans " + 
                                  mapping.getClassName() + " doit retourner String ou ModelView</p>");
                    }
                    
                } catch (Exception e) {
                    out.println("<h1>Erreur lors de l'exécution</h1>");
                    out.println("<p style='color:red'>" + e.getMessage() + "</p>");
                    e.printStackTrace();
                }
            } else {
                out.println("<h1>Erreur 404</h1>");
                out.println("<p style='color:red'>Aucune méthode associée à ce chemin: " + path + "</p>");
            }
        }
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