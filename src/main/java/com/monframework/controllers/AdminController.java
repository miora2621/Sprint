package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.ModelView;

// SPRINT 16: Exemple de contrôleur avec autorisation au niveau de la CLASSE
@Controller
@RequireAuth(roles = {"ADMIN", "MANAGER"}) // Toutes les méthodes nécessitent ADMIN ou MANAGER
public class AdminController {
    
    // Cette méthode hérite de l'autorisation de classe (ADMIN ou MANAGER requis)
    @Url("/admin-dashboard")
    @GET
    public ModelView adminDashboard() {
        ModelView mv = new ModelView("admin-dashboard.jsp");
        mv.addObject("message", "Tableau de bord administrateur");
        return mv;
    }
    
    // Cette méthode SURCHARGE l'autorisation de classe - seuls les ADMIN peuvent accéder
    @Url("/admin-delete-all")
    @POST
    @RequireRole({"ADMIN"}) // Plus restrictif que la classe
    public ModelView deleteAll() {
        ModelView mv = new ModelView("admin-delete.jsp");
        mv.addObject("message", "Suppression effectuée - réservée aux ADMIN seulement");
        return mv;
    }
    
    // Cette méthode hérite de l'autorisation de classe
    @Url("/admin-reports")
    @GET
    public ModelView reports() {
        ModelView mv = new ModelView("admin-reports.jsp");
        mv.addObject("message", "Rapports - accessible aux ADMIN et MANAGER");
        return mv;
    }
}