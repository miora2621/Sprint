package com.monframework.framework.exception;

import java.util.Map;

public class ValidationException extends FrameworkException {
    private final Map<String, String> errors;
    private final Map<String, String> formValues;
    private final String redirectUrl;

    public ValidationException(Map<String, String> errors, 
                             Map<String, String> formValues,
                             String redirectUrl) {
        super("Erreur de validation", 400, null);
        this.errors = errors;
        this.formValues = formValues;
        this.redirectUrl = redirectUrl;
    }

    // Getters
    public Map<String, String> getErrors() { return errors; }
    public Map<String, String> getFormValues() { return formValues; }
    public String getRedirectUrl() { return redirectUrl; }
}