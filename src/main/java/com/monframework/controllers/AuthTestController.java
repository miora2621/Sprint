package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.ModelView;
import com.monframework.framework.validation.*;


@Controller
public class AuthTestController {
    
    // 1. Méthode publique sans restriction
    @Url("/public-test")
    @GET
    public ModelView publicMethod() {
        ModelView mv = new ModelView("public.jsp");
        mv.addObject("message", "Cette page est accessible à tous");
        return mv;
    }

    // 2. Méthode avec auth requise
    @Url("/private-test")
    @GET
    @RequireAuth
    public ModelView privateMethod() {
        ModelView mv =  new ModelView("private.jsp");
        mv.addObject("message", "Zone privée - Authentification requise");
        return mv;
    }

    // 3. Méthode avec rôle spécifique
    @Url("/admin-test")
    @GET
    @RequireRole("ADMIN")
    public ModelView adminMethod() {
        ModelView mv =  new ModelView("admin.jsp");
        mv.addObject("message", "Zone admin - Rôle ADMIN requis");
        return mv;
    }
}