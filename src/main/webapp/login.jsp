<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Connexion</title>
</head>
<body>
    <h2>Connexion</h2>
    
    <c:if test="${not empty error}">
        <div style="color:red">${error}</div>
    </c:if>
    
    <c:if test="${not empty message}">
        <div style="color:green">${message}</div>
    </c:if>
    
    <form action="login" method="post">
        <div>
            <label>Nom d'utilisateur:</label><br>
            <input type="text" name="username" required>
            <small>(Essayez: admin/password ou user/password)</small>
        </div>
        <br>
        <div>
            <label>Mot de passe:</label><br>
            <input type="password" name="password" required>
        </div>
        <br>
        <button type="submit">Se connecter</button>
    </form>
    
    <br>
    <a href="public">Page publique (pas d'auth requise)</a><br>
    <a href="private">Page privée (auth requise)</a><br>
    <a href="admin-only">Page admin (rôle ADMIN requis)</a><br>
    <a href="user-or-admin">Page USER ou ADMIN</a>
</body>
</html>