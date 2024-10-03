package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.ModelView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monframework.framework.annotation.RestApi;
import com.monframework.framework.annotation.Url;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

@Controller
public class ExampleController {
    
    @Url("/user")
    @GET
    public ModelView getUserForm() {
        return new ModelView("userForm.jsp");
    }
    
    @Url("/user")
    @POST
    public ModelView createUser(
            @Param(name = "username") String username,
            @Param(name = "email") String email) {
        
        ModelView modelView = new ModelView("userResult.jsp");
        modelView.addObject("username", username);
        modelView.addObject("email", email);
        return modelView;
    }
    
    @Url("/api-data")
    @GET
    @RestApi
    public Map<String, String> getData() {
        return Map.of("message", "Données GET");
    }
    
    @Url("/api-data")
    @POST
    @RestApi
    public Map<String, String> postData() {
        return Map.of("message", "Données POST");
    }
}