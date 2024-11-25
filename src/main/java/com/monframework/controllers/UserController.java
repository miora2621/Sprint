package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.ModelView;
import com.monframework.framework.validation.*;

@Controller
public class UserController {

    @Url("/register-form")
    @GET
    public ModelView showRegistrationForm() {
        return new ModelView("register-form.jsp");
    }
    
    @Url("/register")
    @POST
    public ModelView registerUser(
            @Valid @Param(name = "username") 
            @com.monframework.framework.validation.Size(min=3, max=20, message="Le nom d'utilisateur doit faire entre 3 et 20 caractères") 
            String username,
            
            @Valid @Param(name = "email") 
            @com.monframework.framework.validation.Email 
            String email,
            
            @Valid @Param(name = "password") 
            @com.monframework.framework.validation.Size(min=8, message="Le mot de passe doit faire au moins 8 caractères") 
            String password) {
        
        ModelView modelView = new ModelView("registration-success.jsp");
        modelView.addObject("username", username);
        return modelView;
    }
}