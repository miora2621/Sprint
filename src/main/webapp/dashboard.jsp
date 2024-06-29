<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tableau de bord</title>
</head>
<body>
    <h1>Bienvenue ${sessionScope.username}!</h1>
    
    <h2>Vos données:</h2>
    <ul>
        <c:forEach items="${sessionScope.userData}" var="item">
            <li>${item}</li>
        </c:forEach>
    </ul>
    
    <form action="logout" method="GET">
        <button type="submit">Déconnexion</button>
    </form>
</body>
</html>