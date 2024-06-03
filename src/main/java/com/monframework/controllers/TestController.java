package com.monframework.controllers;

import com.monframework.framework.ModelView;
import com.monframework.framework.annotation.Controller;
import com.monframework.framework.annotation.GET;

@Controller
public class TestController {
    @GET("/test")
    public ModelView testMethod() {
        ModelView modelView = new ModelView("test.jsp");
        modelView.addObject("message", "Bonjour depuis TestController!");
        modelView.addObject("nombre", 42);
        return modelView;
    }
}
