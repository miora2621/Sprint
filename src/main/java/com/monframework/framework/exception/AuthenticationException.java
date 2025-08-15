package com.monframework.framework.exception;

public class AuthenticationException extends FrameworkException {
    public AuthenticationException(String message) {
        super(message, 401, "/login.jsp");
    }
}