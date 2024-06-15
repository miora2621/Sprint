package com.monframework.controllers;

import javax.servlet.http.HttpServletRequest;

import com.monframework.framework.ModelView;
import com.monframework.framework.annotation.Controller;
import com.monframework.framework.annotation.GET;
import com.monframework.framework.annotation.Param;

@Controller
public class TestController {
    @GET("/test")
    public ModelView testMethod() {
        ModelView modelView = new ModelView("test.jsp");
        modelView.addObject("message", "Bonjour depuis TestController!");
        return modelView;
    }

    @GET("/user-form")
    public ModelView showForm() {
        return new ModelView("userForm.jsp");
    }
    
    @GET("/user-submit")
    public ModelView handleSubmit(
            @Param(name = "nom") String nom,
            @Param(name = "age") int age,
            HttpServletRequest request) {
        
        ModelView modelView = new ModelView("userResult.jsp");
        modelView.addObject("nom", nom);
        modelView.addObject("age", age);
        modelView.addObject("method", request.getMethod());
        
        return modelView;
    }
    
    @GET("/search")
    public ModelView search(
            @Param(name = "ville") String ville,
            @Param(name = "code") int code) {
        
        ModelView modelView = new ModelView("searchResults.jsp");
        modelView.addObject("ville", ville);
        modelView.addObject("code", code);
        
        return modelView;
    }
}
