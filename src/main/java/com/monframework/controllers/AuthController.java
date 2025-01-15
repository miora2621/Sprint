package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.ModelView;
import com.monframework.framework.MySession;
import com.monframework.framework.auth.UserPrincipal;

@Controller
public class AuthController {
    
    @Url("/login-form")
    @GET
    public ModelView showLoginForm() {
        return new ModelView("login.jsp");
    }
    
    @Url("/login")
    @POST
    public ModelView login(
            @Param(name = "username") String username,
            @Param(name = "password") String password,
            MySession session) {
        
        // Debug
        System.out.println("Tentative de connexion - Username: " + username + ", Password: " + password);
        
        // Validation simple (en réalité, vous vérifieriez en base de données)
        if ("admin".equals(username) && "password".equals(password)) {
            UserPrincipal user = new UserPrincipal(username, new String[]{"ADMIN", "USER"});
            session.add("currentUser", user);
            
            System.out.println("Connexion admin réussie - Utilisateur stocké en session");
            
            ModelView mv = new ModelView("dashboard.jsp");
            mv.addObject("message", "Connexion réussie en tant qu'admin!");
            mv.addObject("username", username);
            return mv;
        } else if ("user".equals(username) && "password".equals(password)) {
            UserPrincipal user = new UserPrincipal(username, new String[]{"USER"});
            session.add("currentUser", user);
            
            System.out.println("Connexion user réussie - Utilisateur stocké en session");
            
            ModelView mv = new ModelView("user-dashboard.jsp");
            mv.addObject("message", "Connexion réussie en tant qu'utilisateur!");
            mv.addObject("username", username);
            return mv;
        } else {
            System.out.println("Échec de connexion - Identifiants incorrects");
            ModelView mv = new ModelView("login.jsp");
            mv.addObject("error", "Identifiants incorrects. Utilisez admin/password ou user/password");
            return mv;
        }
    }
    
    @Url("/logout")
    @GET
    public ModelView logout(MySession session) {
        session.delete("currentUser");
        session.invalidate();
        
        ModelView mv = new ModelView("login.jsp");
        mv.addObject("message", "Déconnexion réussie");
        return mv;
    }
    
    // Page pour déboguer la session
    @Url("/debug-session")
    @GET
    public ModelView debugSession(MySession session) {
        UserPrincipal user = (UserPrincipal) session.get("currentUser");
        
        ModelView mv = new ModelView("debug.jsp");
        
        if (user != null) {
            mv.addObject("authenticated", user.isAuthenticated());
            mv.addObject("username", user.getUsername());
            mv.addObject("roles", String.join(", ", user.getRoles()));
            System.out.println("Debug - User trouvé: " + user.getUsername() + ", Rôles: " + String.join(", ", user.getRoles()));
        } else {
            mv.addObject("authenticated", false);
            mv.addObject("username", "Aucun");
            mv.addObject("roles", "Aucun");
            System.out.println("Debug - Aucun utilisateur en session");
        }
        
        return mv;
    }
}