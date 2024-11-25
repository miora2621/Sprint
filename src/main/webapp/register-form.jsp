<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Inscription</title>
</head>
<body>
    <h1>Formulaire d'inscription</h1>
    <form action="register" method="POST">
        <label>Nom d'utilisateur (3-20 caractères):
            <input type="text" name="username" required>
        </label><br>
        
        <label>Email:
            <input type="email" name="email" required>
        </label><br>
        
        <label>Mot de passe (min 8 caractères):
            <input type="password" name="password" required>
        </label><br>
        
        <button type="submit">S'inscrire</button>
    </form>
</body>
</html>