package com.monframework.framework.exception;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FrameworkException extends RuntimeException {
    private final int statusCode;
    private final String errorPage;

    public FrameworkException(String message, int statusCode, String errorPage) {
        super(message);
        this.statusCode = statusCode;
        this.errorPage = errorPage;
    }

    public void sendErrorResponse(HttpServletRequest request, HttpServletResponse response) 
        throws IOException, ServletException {
        response.setStatus(statusCode);
        request.setAttribute("javax.servlet.error.status_code", statusCode);
        request.setAttribute("javax.servlet.error.message", getMessage());
        request.setAttribute("javax.servlet.error.exception", this);
        
        if (errorPage != null) {
            request.getRequestDispatcher(errorPage).forward(request, response);
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h1>Erreur " + statusCode + "</h1>");
            out.println("<p>" + getMessage() + "</p>");
        }
    }
}