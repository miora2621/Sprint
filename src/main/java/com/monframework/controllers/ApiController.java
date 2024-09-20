package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ApiController {
    
    @GET("/api-users")
    @RestApi
    public List<Map<String, Object>> getUsers() {
        return Arrays.asList(
            createUser(1, "Alice", "alice@example.com"),
            createUser(2, "Bob", "bob@example.com")
        );
    }
    
    @GET("/api-user/{id}")
    @RestApi
    public Map<String, Object> getUserById(@Param(name = "id") int id) {
        return createUser(id, "User " + id, "user" + id + "@example.com");
    }
    
    private Map<String, Object> createUser(int id, String name, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("id", id);
        user.put("name", name);
        user.put("email", email);
        user.put("active", true);
        return user;
    }
}