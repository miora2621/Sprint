package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.ModelView;
import com.monframework.framework.MySession;
import javax.servlet.http.HttpServletRequest;

import com.monframework.framework.annotation.Controller;
import com.monframework.framework.annotation.GET;
import com.monframework.framework.annotation.ModelAttribute;
import com.monframework.framework.annotation.Param;
import com.monframework.models.UserForm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

@Controller
public class AuthController {
    
    // Données simulées (remplace une base de données)
    private static final Map<String, List<String>> USER_DATA = Map.of(
        "alice", List.of("Note 1", "Note 2", "Note 3"),
        "bob", List.of("Tâche A", "Tâche B")
    );

    @GET("/login")
    public ModelView showLoginForm() {
        return new ModelView("login.jsp");
    }

    @GET("/do-login")
    public ModelView processLogin(
            @Param(name = "username") String username,
            MySession session) {
        
        ModelView modelView = new ModelView();
        
        if (USER_DATA.containsKey(username)) {
            session.add("username", username);
            session.add("userData", USER_DATA.get(username));
            modelView.setUrl("dashboard.jsp");
        } else {
            modelView.setUrl("login.jsp");
            modelView.addObject("error", "Identifiant invalide");
        }
        
        return modelView;
    }

    @GET("/dashboard")
    public ModelView showDashboard(MySession session) {
        ModelView modelView = new ModelView("dashboard.jsp");
        
        if (session.get("username") == null) {
            modelView.setUrl("login.jsp");
            modelView.addObject("error", "Veuillez vous connecter");
        }
        
        return modelView;
    }

    @GET("/logout")
    public ModelView processLogout(MySession session) {
        session.invalidate();
        return new ModelView("login.jsp");
    }
}