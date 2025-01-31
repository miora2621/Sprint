package com.monframework.controllers;

import com.monframework.framework.auth.UserPrincipal;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AuthServlet", value = {"/login", "/logout"})
public class AuthServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Simulation base de données
        if ("admin".equals(username) && "admin123".equals(password)) {
            UserPrincipal admin = new UserPrincipal("admin", new String[]{"ADMIN"});
            request.getSession().setAttribute("currentUser", admin);
            response.sendRedirect(request.getContextPath() + "/admin-test");
        } 
        else if ("manager".equals(username) && "manager123".equals(password)) {
            UserPrincipal manager = new UserPrincipal("manager", new String[]{"MANAGER"});
            request.getSession().setAttribute("currentUser", manager);
            response.sendRedirect(request.getContextPath() + "/manager/dashboard");
        }
        else if ("user".equals(username) && "user123".equals(password)) {
            UserPrincipal user = new UserPrincipal("user", new String[]{});
            request.getSession().setAttribute("currentUser", user);
            response.sendRedirect(request.getContextPath() + "/private-test");
        } else {
            request.setAttribute("error", "Identifiants incorrects");
            request.getRequestDispatcher("login-form.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().endsWith("/logout")) {
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/public-test");
        } else {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}