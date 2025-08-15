<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Debug Session</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .info { background-color: #d1ecf1; color: #0c5460; padding: 15px; border: 1px solid #bee5eb; border-radius: 4px; margin-bottom: 20px; }
        table { border-collapse: collapse; width: 100%; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h1>🔍 Debug - État de la session</h1>
    
    <div class="info">
        <h3>Informations de session :</h3>
        <table>
            <tr><th>Propriété</th><th>Valeur</th></tr>
            <tr><td>Authentifié</td><td><strong>${authenticated}</strong></td></tr>
            <tr><td>Nom d'utilisateur</td><td><strong>${username}</strong></td></tr>
            <tr><td>Rôles</td><td><strong>${roles}</strong></td></tr>
        </table>
    </div>
    
    <h3>Actions de test :</h3>
    <ul>
        <li><a href="login-form">Se connecter</a></li>
        <li><a href="logout">Se déconnecter</a></li>
        <li><a href="public">Page publique</a> (accessible à tous)</li>
        <li><a href="private">Page privée</a> (authentification requise)</li>
        <li><a href="admin-only">Page admin</a> (rôle ADMIN requis)</li>
        <li><a href="user-or-admin">Page USER ou ADMIN</a> (rôle USER ou ADMIN requis)</li>
    </ul>
    
    <hr>
    <small>Cette page vous aide à diagnostiquer les problèmes d'authentification.</small>
</body>
</html>