<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Admin Page</title>
</head>
<body>
    <h1>Zone Admin</h1>
    <p>${message}</p>
    <p>Rôles: ${sessionScope.currentUser.roles}</p>
</body>
</html>