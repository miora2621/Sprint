<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Connexion</h1>
    <% if (request.getAttribute("error") != null) { %>
        <p style="color:red">${error}</p>
    <% } %>
    <form action="do-login" method="GET">
        <label>Nom d'utilisateur: 
            <input type="text" name="username" required>
        </label>
        <button type="submit">Se connecter</button>
    </form>
</body>
</html>