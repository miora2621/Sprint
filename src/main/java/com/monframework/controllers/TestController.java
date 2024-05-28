package com.monframework.controllers;

import com.monframework.framework.annotation.Controller;
import com.monframework.framework.annotation.GET;

@Controller
public class TestController {
    @GET("/test")
    public String testMethod() {
        return "Ceci est le résultat de testMethod() dans TestController";
    }
    
}
