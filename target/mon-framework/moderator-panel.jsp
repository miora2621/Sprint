<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Panel Modérateur</title><style>body { font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 20px; }</style></head>
<body>
    <h1>🛡️ ${message}</h1>
    <p>✅ <strong>Test réussi !</strong> Rôle MODERATOR ou ADMIN requis au niveau méthode.</p>
    <a href="${pageContext.request.contextPath}/login-form">← Retour aux tests</a>
</body>
</html>