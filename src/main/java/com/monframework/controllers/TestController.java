package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.exception.*;
import com.monframework.framework.ModelView;
import com.monframework.framework.annotation.Url;

@Controller
public class TestController {
    
    @Url("/test-error")
    @GET
    public ModelView testError() {
        // Exemple avec FrameworkException
        throw new FrameworkException("Ceci est une erreur de test simulée", 400, "/error.jsp");
    }
    
    @Url("/trigger-npe")
    @GET
    public ModelView triggerNPE() {
        try {
            // Simulation d'une NPE
            String nullString = null;
            nullString.length();
            return new ModelView("test.jsp");
        } catch (NullPointerException e) {
            // Transformation en FrameworkException
            throw new FrameworkException("Erreur de traitement: objet null", 500, "/error.jsp");
        }
    }
}