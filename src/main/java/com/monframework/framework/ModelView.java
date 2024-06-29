package com.monframework.framework;

import java.util.HashMap;

public class ModelView {
    private String url;
    private HashMap<String, Object> data;

    public ModelView(String url) {
        this.url = url;
        this.data = new HashMap<>();
    }

    public ModelView() {
        this.data = new HashMap<>();
    }

     public void setUrl(String url) {
        this.url = url;
    }


    // Getters et Setters
    public String getUrl() {
        return url;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    // Méthode pour ajouter des données
    public void addObject(String key, Object value) {
        this.data.put(key, value);
    }
}