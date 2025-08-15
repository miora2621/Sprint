<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Dashboard Utilisateur</title>
</head>
<body>
    <h1>Dashboard Utilisateur</h1>
    <p>${message}</p>
    <p>Vous êtes connecté en tant qu'utilisateur normal.</p>
    
    <h3>Testez vos permissions :</h3>
    <ul>
        <li><a href="public">Page publique ✅</a></li>
        <li><a href="private">Page privée ✅</a></li>
        <li><a href="admin-only">Page admin seulement ❌ (devrait être refusé)</a></li>
        <li><a href="user-or-admin">Page USER ou ADMIN ✅</a></li>
    </ul>
    
    <br>
    <a href="logout">Se déconnecter</a>
</body>
</html>