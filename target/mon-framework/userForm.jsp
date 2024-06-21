<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Form</title>
</head>
<body>
    <h1>User Registration</h1>
    <form action="user-submit" method="GET">
        <label>Username: <input type="text" name="username"></label><br>
        <label>Email: <input type="email" name="user_email"></label><br>
        <label>Age: <input type="number" name="age"></label><br>
        <button type="submit">Submit</button>
    </form>
</body>
</html>