<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Résultat de l'upload</title>
</head>
<body>
    <h1>Résultat de l'upload</h1>
    
    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
    
    <p><strong>Description:</strong> ${description}</p>
    
    <c:if test="${not empty filename}">
        <h2>Détails du fichier:</h2>
        <p><strong>Nom du fichier:</strong> ${filename}</p>
        <p><strong>Type:</strong> ${contentType}</p>
        <p><strong>Taille:</strong> ${size}</p>
    </c:if>

    <a href="upload-form">Uploader un autre fichier</a>
</body>
</html>