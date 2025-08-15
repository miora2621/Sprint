<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion Réussie</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 20px; }
        .success { color: green; background: #e8f5e8; padding: 15px; border-radius: 5px; }
        .user-info { background: #f0f8ff; padding: 15px; margin: 20px 0; border-radius: 5px; }
        .test-links { margin-top: 30px; padding: 20px; background: #f0f0f0; }
        .test-links a { display: block; margin: 5px 0; color: #007cba; }
        button { background: #28a745; color: white; padding: 10px 20px; border: none; cursor: pointer; margin: 5px; }
        .danger { background: #dc3545; }
    </style>
</head>
<body>
    <div class="success">
        <h2>✅ ${message}</h2>
    </div>
    
    <div class="user-info">
        <h3>Informations de l'utilisateur connecté :</h3>
        <p><strong>Nom :</strong> ${username}</p>
        <p><strong>Rôles :</strong> ${roles}</p>
    </div>
    
    <div class="test-links">
        <h3>🧪 Testez maintenant le Sprint 16</h3>
        
        <h4>Test 1: AdminController (classe @RequireAuth avec rôles ADMIN/MANAGER)</h4>
        <a href="${pageContext.request.contextPath}/admin/dashboard">📊 Dashboard (héritage classe)</a>
        <a href="${pageContext.request.contextPath}/admin/reports">📈 Rapports (héritage classe)</a>
        <form method="post" action="${pageContext.request.contextPath}/admin/delete-all" style="display: inline;">
            <button type="submit" class="danger">🗑️ Suppression (surcharge méthode - ADMIN seulement)</button>
        </form>
        
        <h4>Test 2: SecureController (classe @RequireAuth simple)</h4>
        <a href="${pageContext.request.contextPath}/secure/home">🏠 Accueil sécurisé (héritage classe)</a>
        <a href="${pageContext.request.contextPath}/secure/premium">⭐ Premium (classe + méthode)</a>
        
        <h4>Test 3: MixedController (pas d'annotation classe)</h4>
        <a href="${pageContext.request.contextPath}/public-info">📄 Public (pas d'auth)</a>
        <a href="${pageContext.request.contextPath}/user-profile">👤 Profil (auth méthode)</a>
        <a href="${pageContext.request.contextPath}/moderator-panel">🛡️ Modération (rôle méthode)</a>
        
        <h4>Actions</h4>
        <button onclick="location.href='${pageContext.request.contextPath}/logout'">🚪 Se déconnecter</button>
        <button onclick="location.href='${pageContext.request.contextPath}/login-form'">🔄 Changer d'utilisateur</button>
    </div>
</body>
</html>