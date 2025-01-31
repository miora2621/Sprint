<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Public Page</title>
</head>
<body>
    <h1>Page Publique</h1>
    <p>${message}</p>
    <a href="${pageContext.request.contextPath}/secured/home">Accès sécurisé</a>
</body>
</html>