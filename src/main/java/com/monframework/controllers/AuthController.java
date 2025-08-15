package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.ModelView;
import com.monframework.framework.MySession;
import com.monframework.framework.auth.UserPrincipal;

@Controller
public class AuthController {
    
    // Afficher le formulaire de connexion
    @Url("/login-form")
    @GET
    public ModelView loginForm() {
        ModelView mv = new ModelView("login-form.jsp");
        mv.addObject("title", "Connexion");
        return mv;
    }
    
    // Traiter la connexion
    @Url("/login")
    @POST
    public ModelView login(@Param(name = "username") String username,
                          @Param(name = "password") String password,
                          @Param(name = "role") String role,
                          MySession session) {
        
        // Simulation d'authentification simple
        if (username != null && password != null && !username.trim().isEmpty()) {
            
            // Créer l'utilisateur avec le rôle choisi
            String[] roles;
            switch (role) {
                case "admin":
                    roles = new String[]{"ADMIN", "USER"};
                    break;
                case "manager":
                    roles = new String[]{"MANAGER", "USER"};
                    break;
                case "moderator":
                    roles = new String[]{"MODERATOR", "USER"};
                    break;
                case "premium":
                    roles = new String[]{"PREMIUM", "USER"};
                    break;
                case "user":
                default:
                    roles = new String[]{"USER"};
                    break;
            }
            
            UserPrincipal user = new UserPrincipal(username, roles);
            session.add("currentUser", user);
            
            ModelView mv = new ModelView("login-success.jsp");
            mv.addObject("message", "Connexion réussie ! Bienvenue " + username);
            mv.addObject("username", username);
            mv.addObject("roles", String.join(", ", roles));
            return mv;
        }
        
        ModelView mv = new ModelView("login-form.jsp");
        mv.addObject("error", "Nom d'utilisateur et mot de passe requis");
        return mv;
    }
    
    // Déconnexion
    @Url("/logout")
    @GET
    public ModelView logout(MySession session) {
        session.delete("currentUser");
        ModelView mv = new ModelView("logout-success.jsp");
        mv.addObject("message", "Vous êtes déconnecté");
        return mv;
    }
    
    // Page d'accès refusé
    @Url("/access-denied")
    @GET
    public ModelView accessDenied() {
        ModelView mv = new ModelView("access-denied.jsp");
        mv.addObject("message", "Accès refusé - Rôles insuffisants");
        return mv;
    }
}