<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Page USER ou ADMIN</title>
</head>
<body>
    <h1>Page USER ou ADMIN</h1>
    <p>${message}</p>
    <p>Cette page est accessible aux utilisateurs ayant le rôle USER ou ADMIN.</p>
    
    <a href="logout">Se déconnecter</a><br>
    <a href="public">Retour à la page publique</a>
</body>
</html>