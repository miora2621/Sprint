package com.monframework.framework;

import com.monframework.framework.annotation.Controller;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class FrontController extends HttpServlet {
    
    private List<String> controllerNames = new ArrayList<>();
    private boolean isScanned = false;
    private String controllerPackage;

    @Override
    public void init() throws ServletException {
        ServletConfig config = getServletConfig();
        controllerPackage = config.getInitParameter("controller-package");
        scanControllers();
    }

    private void scanControllers() throws ServletException {
        if (isScanned) return;
        
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
                .forEach(clazz -> controllerNames.add(clazz.getSimpleName()));
            
            isScanned = true;
        } catch (IOException e) {
            throw new ServletException("Failed to scan controllers", e);
        }
    }


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Mon Framework - Contrôleurs</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Liste des contrôleurs</h1>");
            out.println("<ul>");
            
            if (controllerNames.isEmpty()) {
                out.println("<li>Aucun contrôleur trouvé</li>");
            } else {
                controllerNames.forEach(name -> out.println("<li>" + name + "</li>"));
            }
            
            out.println("</ul>");
            out.println("<p>URL actuelle : " + request.getRequestURI() + "</p>");
            out.println("</body>");
            out.println("</html>");
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