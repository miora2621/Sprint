package com.monframework.framework.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends FrameworkException {
    private final Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        super("Erreur de validation", 400, "/error-validation.jsp");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}