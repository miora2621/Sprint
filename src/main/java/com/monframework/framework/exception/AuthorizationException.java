package com.monframework.framework.exception;

public class AuthorizationException extends FrameworkException {
    public AuthorizationException(String message) {
        super(message, 403, "/access-denied.jsp");
    }
}