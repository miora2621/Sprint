package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.ModelView;

@Controller
@RequireAuth // Toutes les méthodes nécessitent au minimum une authentification
public class SecureController {
    
    // Hérite de l'authentification de classe
    @Url("/secure-home")
    @GET
    public ModelView secureHome() {
        ModelView mv = new ModelView("secure-home.jsp");
        mv.addObject("message", "Accueil sécurisé - authentification héritée de la classe");
        return mv;
    }
    
    // Ajoute une exigence de rôle en plus de l'authentification de classe
    @Url("/secure-premium")
    @GET
    @RequireRole({"PREMIUM", "ADMIN"})
    public ModelView premiumContent() {
        ModelView mv = new ModelView("premium-content.jsp");
        mv.addObject("message", "Contenu premium - authentification + rôle PREMIUM/ADMIN");
        return mv;
    }
}

