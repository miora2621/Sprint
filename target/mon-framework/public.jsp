<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Page publique</title>
</head>
<body>
    <h1>Page publique</h1>
    <p>${message}</p>
    <p>Cette page est accessible à tous, sans authentification.</p>
    
    <h3>Navigation :</h3>
    <ul>
        <li><a href="login-form">Se connecter</a></li>
        <li><a href="private">Page privée (auth requise)</a></li>
        <li><a href="admin-only">Page admin seulement</a></li>
        <li><a href="user-or-admin">Page USER ou ADMIN</a></li>
    </ul>
</body>
</html>