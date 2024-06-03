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

    @Override
    public void init() throws ServletException {
        ServletConfig config = getServletConfig();
        controllerPackage = config.getInitParameter("controller-package");
        scanControllersAndMethods();
    }

    private void scanControllersAndMethods() throws ServletException {
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            String path = controllerPackage.replace('.', '/');
            
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
                        return null;
                    }
                })
                .filter(clazz -> clazz != null && clazz.isAnnotationPresent(Controller.class))
                .forEach(this::processControllerMethods);
            
        } catch (IOException e) {
            throw new ServletException("Failed to scan controllers", e);
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
                urlMappings.put(url, new Mapping(controllerClass.getName(), method.getName()));
            }
        }
    }

   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.substring(contextPath.length());
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
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
                        
                        // Ajouter les données à la requête
                        for (HashMap.Entry<String, Object> entry : modelView.getData().entrySet()) {
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                        
                        // Forward vers la vue
                        RequestDispatcher dispatcher = request.getRequestDispatcher(modelView.getUrl());
                        dispatcher.forward(request, response);
                        return; // Important pour arrêter l'exécution ici
                    } 
                    else {
                        out.println("Type de retour non reconnu");
                    }
                    
                } catch (Exception e) {
                    out.println("<p style='color:red'>Erreur: " + e.getMessage() + "</p>");
                    e.printStackTrace();
                }
            } else {
                out.println("<p style='color:red'>Aucune méthode associée à ce chemin</p>");
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