package com.monframework.framework.auth;

public class UserPrincipal {
    private String username;
    private String[] roles;
    private boolean authenticated;

    public UserPrincipal(String username, String[] roles) {
        this.username = username;
        this.roles = roles;
        this.authenticated = true;
    }

    public UserPrincipal() {
        this.authenticated = false;
    }

    // Getters et Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String[] getRoles() { return roles; }
    public void setRoles(String[] roles) { this.roles = roles; }
    
    public boolean isAuthenticated() { return authenticated; }
    public void setAuthenticated(boolean authenticated) { this.authenticated = authenticated; }
    
    public boolean hasRole(String role) {
        if (roles == null) return false;
        for (String userRole : roles) {
            if (userRole.equals(role)) return true;
        }
        return false;
    }
    
    public boolean hasAnyRole(String[] requiredRoles) {
        if (requiredRoles == null || requiredRoles.length == 0) return true;
        if (roles == null) return false;
        
        for (String requiredRole : requiredRoles) {
            if (hasRole(requiredRole)) return true;
        }
        return false;
    }
}