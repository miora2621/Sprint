package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.ModelView;
import com.monframework.framework.validation.*;

@Controller
@RequireRole("MANAGER") // Toutes les méthodes nécessitent le rôle MANAGER
public class ManagerController {
    
    @Url("/manager-dashboard")
    @GET
    public ModelView managerDashboard() {
        ModelView mv =  new ModelView("manager-dashboard.jsp");
        mv.addObject("message", "Tableau de bord Manager");
        return mv;
    }

    // Méthode nécessitant un rôle supplémentaire
    @Url("/manager-reports")
    @GET
    @RequireRole({"MANAGER", "SENIOR_MANAGER"})
    public ModelView managerReports() {
        ModelView mv =  new ModelView("manager-reports.jsp");
        mv.addObject("message", "Rapports managers");
        return mv;
    }
}