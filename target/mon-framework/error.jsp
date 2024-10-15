<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Erreur ${pageContext.errorData.statusCode}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            color: #333;
        }
        .error-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #e74c3c;
            border-radius: 5px;
            background-color: #f9ebea;
        }
        .error-title {
            color: #e74c3c;
        }
        .error-details {
            margin-top: 20px;
            padding: 15px;
            background-color: #f2f2f2;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1 class="error-title">Erreur ${pageContext.errorData.statusCode}</h1>
        <p><strong>Message:</strong> ${pageContext.errorData.throwable.message}</p>
        
        <c:if test="${pageContext.errorData.statusCode == 404}">
            <p>La page que vous avez demandée n'existe pas.</p>
        </c:if>
        
        <div class="error-details">
            <h3>Détails techniques:</h3>
            <p><strong>URL:</strong> ${pageContext.request.requestURI}</p>
            <p><strong>Type d'erreur:</strong> ${pageContext.errorData.throwable.getClass().name}</p>
            
            <c:if test="${pageContext.errorData.statusCode == 500}">
                <h4>StackTrace:</h4>
                <pre>${pageContext.errorData.throwable.stackTrace}</pre>
            </c:if>
        </div>
        
        <a href="${pageContext.request.contextPath}/">Retour à la page d'accueil</a>
    </div>
</body>
</html>