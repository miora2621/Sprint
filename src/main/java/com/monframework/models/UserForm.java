package com.monframework.models;

import com.monframework.framework.annotation.FormField;

public class UserForm {
    @FormField // Utilisera le nom du champ par défaut
    private String username;
    
    @FormField("user_email") // Utilisera ce nom dans le formulaire
    private String email;
    
    @FormField
    private int age;
    
    // Getters et Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
}