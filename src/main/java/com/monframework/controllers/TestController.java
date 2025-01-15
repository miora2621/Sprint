package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.ModelView;

@Controller
public class TestController {
    
    // Page publique - pas d'autorisation requise
    @Url("/public")
    @GET
    public ModelView publicPage() {
        ModelView mv = new ModelView("public.jsp");
        mv.addObject("message", "Page publique - accessible à tous");
        return mv;
    }
    
    // Sprint 15: Méthode nécessitant juste une authentification
    @Url("/private")
    @GET
    @RequireAuth
    public ModelView privatePage() {
        ModelView mv = new ModelView("private.jsp");
        mv.addObject("message", "Page privée - authentification requise");
        return mv;
    }
    
    // Sprint 15: Méthode nécessitant un rôle spécifique
    @Url("/admin-only")
    @GET
    @RequireRole({"ADMIN"})
    public ModelView adminOnlyPage() {
        ModelView mv = new ModelView("admin-only.jsp");
        mv.addObject("message", "Page admin seulement - rôle ADMIN requis");
        return mv;
    }
    
    // Sprint 15: Méthode nécessitant authentification avec rôles multiples possibles
    @Url("/user-or-admin")
    @GET
    @RequireAuth(roles = {"USER", "ADMIN"})
    public ModelView userOrAdminPage() {
        ModelView mv = new ModelView("user-admin.jsp");
        mv.addObject("message", "Page pour USER ou ADMIN");
        return mv;
    }
}