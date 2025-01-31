<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion - Test Sprint 16</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input, select { width: 100%; padding: 8px; margin-bottom: 10px; }
        button { background: #007cba; color: white; padding: 10px 20px; border: none; cursor: pointer; }
        .error { color: red; margin-bottom: 10px; }
        .test-links { margin-top: 30px; padding: 20px; background: #f0f0f0; }
        .test-links h3 { margin-top: 0; }
        .test-links a { display: block; margin: 5px 0; color: #007cba; }
    </style>
</head>
<body>
    <h1>Connexion - Test Sprint 16</h1>
    
    <% if (request.getAttribute("error") != null) { %>
        <div class="error">${error}</div>
    <% } %>
    
    <form method="post" action="${pageContext.request.contextPath}/login">
        <div class="form-group">
            <label>Nom d'utilisateur :</label>
            <input type="text" name="username" required>
        </div>
        
        <div class="form-group">
            <label>Mot de passe :</label>
            <input type="password" name="password" required>
        </div>
        
        <div class="form-group">
            <label>Rôle de test :</label>
            <select name="role" required>
                <option value="user">USER (rôle de base)</option>
                <option value="admin">ADMIN (tous les droits)</option>
                <option value="manager">MANAGER (gestion)</option>
                <option value="moderator">MODERATOR (modération)</option>
                <option value="premium">PREMIUM (contenu premium)</option>
            </select>
        </div>
        
        <button type="submit">Se connecter</button>
    </form>
    
    <div class="test-links">
        <h3>🧪 Liens de test après connexion</h3>
        
        <h4>Pages publiques (accessibles sans connexion) :</h4>
        <a href="${pageContext.request.contextPath}/public-info">📄 Information publique</a>
        
        <h4>Pages nécessitant une authentification :</h4>
        <a href="${pageContext.request.contextPath}/user-profile">👤 Profil utilisateur (auth requise)</a>
        <a href="${pageContext.request.contextPath}/secure/home">🏠 Accueil sécurisé (auth requise)</a>
        
        <h4>AdminController (classe @RequireAuth avec rôles ADMIN/MANAGER) :</h4>
        <a href="${pageContext.request.contextPath}/admin/dashboard">📊 Dashboard Admin (ADMIN/MANAGER)</a>
        <a href="${pageContext.request.contextPath}/admin/reports">📈 Rapports (ADMIN/MANAGER)</a>
        
        <h4>Pages avec rôles spécifiques :</h4>
        <a href="${pageContext.request.contextPath}/moderator-panel">🛡️ Panel Modérateur (MODERATOR/ADMIN)</a>
        <a href="${pageContext.request.contextPath}/secure/premium">⭐ Contenu Premium (PREMIUM/ADMIN)</a>
        
        <h4>Page très restrictive :</h4>
        <form method="post" action="${pageContext.request.contextPath}/admin/delete-all" style="display: inline;">
            <button type="submit" style="background: red;">🗑️ Suppression (ADMIN seulement)</button>
        </form>
    </div>
</body>
</html>