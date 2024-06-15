<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Formulaire Utilisateur</title>
</head>
<body>
    <h1>Entrez vos informations</h1>
    <form action="user-submit" method="GET">
        <label for="nom">Nom:</label>
        <input type="text" id="nom" name="nom" required><br><br>
        
        <label for="age">Âge:</label>
        <input type="number" id="age" name="age" required><br><br>
        
        <input type="submit" value="Envoyer">
    </form>
</body>
</html>