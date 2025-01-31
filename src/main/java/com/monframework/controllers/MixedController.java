package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.ModelView;

@Controller
public class MixedController {
    
    // Méthode publique - pas d'autorisation
    @Url("/public-info")
    @GET
    public ModelView publicInfo() {
        ModelView mv = new ModelView("public-info.jsp");
        mv.addObject("message", "Information publique - accessible à tous");
        return mv;
    }
    
    // Méthode protégée au niveau méthode
    @Url("/user-profile")
    @GET
    @RequireAuth // Juste authentification requise
    public ModelView userProfile() {
        ModelView mv = new ModelView("user-profile.jsp");
        mv.addObject("message", "Profil utilisateur - authentification requise");
        return mv;
    }
    
    // Méthode avec rôle spécifique
    @Url("/moderator-panel")
    @GET
    @RequireRole({"MODERATOR", "ADMIN"})
    public ModelView moderatorPanel() {
        ModelView mv = new ModelView("moderator-panel.jsp");
        mv.addObject("message", "Panel modérateur - rôle MODERATOR ou ADMIN requis");
        return mv;
    }
}