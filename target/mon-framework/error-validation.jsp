<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Erreur de validation</title>
</head>
<body>
    <h1>Erreurs de validation</h1>
    <ul>
        <c:forEach items="${errors}" var="error">
            <li>${error.key}: ${error.value}</li>
        </c:forEach>
    </ul>
    <a href="javascript:history.back()">Retour</a>
</body>
</html>