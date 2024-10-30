package com.monframework.controllers;

import com.monframework.framework.annotation.*;
import com.monframework.framework.ModelView;
import com.monframework.framework.Multipart;

@Controller
public class FileController {
    
    @Url("/upload-form")
    @GET
    public ModelView showUploadForm() {
        return new ModelView("uploadForm.jsp");
    }
    
    @Url("/upload")
    @POST
    public ModelView handleFileUpload(
            @Param(name = "description") String description,
            @Param(name = "file") Multipart file) {
        
        System.out.println("=== DEBUG UPLOAD ===");
        System.out.println("Description: " + description);
        System.out.println("Fichier: " + (file != null ? file.getFilename() : "null"));
        System.out.println("Taille: " + (file != null ? file.getSize() : "0"));
        
        ModelView modelView = new ModelView("uploadResult.jsp");
        modelView.addObject("description", description != null ? description : "Aucune description");
        
        if (file != null && file.getSize() > 0) {
            modelView.addObject("filename", file.getFilename());
            modelView.addObject("contentType", file.getContentType());
            modelView.addObject("size", file.getSize() + " octets");
        } else {
            modelView.addObject("error", "Aucun fichier n'a été uploadé");
        }
        
        return modelView;
    }
}