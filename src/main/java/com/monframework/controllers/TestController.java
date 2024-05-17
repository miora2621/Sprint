package com.monframework.controllers;

import com.monframework.framework.annotation.Controller;
import com.monframework.framework.annotation.GET;

@Controller
public class TestController {
    @GET("/test")
    public void testMethod() {
        // Méthode qui sera appelée pour /test
    }
    
}
