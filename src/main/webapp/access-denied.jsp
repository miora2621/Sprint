<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Accès Refusé</title><style>body { font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 20px; } .error { color: red; }</style></head>
<body>
    <div class="error">
        <h1>🚫 Accès Refusé</h1>
        <p>${message}</p>
    </div>
    <a href="${pageContext.request.contextPath}/login-form">← Retour au login</a>
</body>
</html>