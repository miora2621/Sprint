package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.ModelView;
import com.monframework.framework.validation.*;

@Controller
public class UserController {
    
    @Url("/register-form")
    @GET
    public ModelView showForm() {
        return new ModelView("register-form.jsp");
    }
    
    @Url("/register")
    @POST
    public ModelView registerUser(
            @Valid @Param(name = "username") 
            @Size(min=3, max=20, message="Le nom doit faire 3-20 caractères")
            String username,
            
            @Valid @Param(name = "email")
            @Email(message="Email invalide")
            String email) {
        
        // Traitement si validation OK
        return new ModelView("registrationsuccess.jsp");
    }
}