<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Rapports Admin</title><style>body { font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 20px; }</style></head>
<body>
    <h1>📈 ${message}</h1>
    <p>✅ <strong>Test réussi !</strong> AdminController - autorisation héritée de la classe.</p>
    <a href="${pageContext.request.contextPath}/login-form">← Retour aux tests</a>
</body>
</html>

<!-- admin-delete.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Suppression Admin</title><style>body { font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 20px; }</style></head>
<body>
    <h1>🗑️ ${message}</h1>
    <p>✅ <strong>Test réussi !</strong> Méthode avec @RequireRole({"ADMIN"}) - surcharge la classe.</p>
    <p>🔒 <strong>Autorisation :</strong> Plus restrictive que la classe (ADMIN seulement vs ADMIN/MANAGER)</p>
    <a href="${pageContext.request.contextPath}/login-form">← Retour aux tests</a>
</body>
</html>