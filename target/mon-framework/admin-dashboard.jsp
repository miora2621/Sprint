<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard Admin</title>
    <style>body { font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 20px; }</style>
</head>
<body>
    <h1>📊 ${message}</h1>
    <p>✅ <strong>Test réussi !</strong> Vous avez accédé au AdminController.</p>
    <p>🔒 <strong>Autorisation :</strong> Héritée de la classe @RequireAuth(roles = {"ADMIN", "MANAGER"})</p>
    <a href="${pageContext.request.contextPath}/login-form">← Retour aux tests</a>
</body>
</html>