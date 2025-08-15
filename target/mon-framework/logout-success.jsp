<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head><title>Déconnexion</title><style>body { font-family: Arial, sans-serif; max-width: 600px; margin: 50px auto; padding: 20px; }</style></head>
<body>
    <h1>🚪 ${message}</h1>
    <p>Vous pouvez maintenant tester l'accès aux pages protégées (vous devriez être redirigé vers le login).</p>
    <a href="${pageContext.request.contextPath}/login-form">← Se reconnecter</a>
</body>
</html>
