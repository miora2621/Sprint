<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Form</title>
</head>
<body>
    <h1>Create User</h1>
    <form action="user" method="POST">
        <label>Username: <input type="text" name="username"></label><br>
        <label>Email: <input type="email" name="email"></label><br>
        <button type="submit">Submit</button>
    </form>
</body>
</html>