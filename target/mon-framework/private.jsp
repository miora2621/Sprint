<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Private Page</title>
</head>
<body>
    <h1>Zone Privée</h1>
    <p>${message}</p>
    <p>Connecté en tant que: ${sessionScope.currentUser.username}</p>
    <a href="${pageContext.request.contextPath}/logout">Déconnexion</a>
</body>
</html>