<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Info Publique</title><style>body { font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 20px; }</style></head>
<body>
    <h1>📄 ${message}</h1>
    <p>✅ <strong>Test réussi !</strong> Page publique accessible sans authentification.</p>
    <a href="${pageContext.request.contextPath}/login-form">← Aller aux tests</a>
</body>
</html>