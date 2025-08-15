<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Inscription</title>
    <style>
        .error { color: red; margin-bottom: 10px; }
        .form-group { margin-bottom: 15px; }
        input { padding: 5px; margin: 5px; }
        .btn { background-color: #007bff; color: white; padding: 10px 15px; border: none; cursor: pointer; }
    </style>
</head>
<body>
    <h2>Formulaire d'inscription</h2>
    
    <!-- Récupération des erreurs et valeurs depuis la session -->
    <c:set var="errors" value="${sessionScope.validationErrors}" />
    <c:set var="formValues" value="${sessionScope.formValues}" />
    
    <!-- Nettoyage de la session après récupération -->
    <c:if test="${not empty errors}">
        <c:remove var="validationErrors" scope="session" />
        <c:remove var="formValues" scope="session" />
    </c:if>
    
    <!-- Affichage des erreurs -->
    <c:if test="${not empty errors}">
        <div class="error">
            <h3>Erreurs de validation :</h3>
            <c:forEach items="${errors}" var="error">
                <p>${error.key}: ${error.value}</p>
            </c:forEach>
        </div>
    </c:if>

    <form action="register" method="post">
        <div class="form-group">
            <label for="username">Nom d'utilisateur:</label><br>
            <input type="text" id="username" name="username" value="${formValues.username}" placeholder="3-20 caractères">
            <c:if test="${not empty errors.username}">
                <span class="error">${errors.username}</span>
            </c:if>
        </div>
        
        <div class="form-group">
            <label for="email">Email:</label><br>
            <input type="email" id="email" name="email" value="${formValues.email}" placeholder="votre@email.com">
            <c:if test="${not empty errors.email}">
                <span class="error">${errors.email}</span>
            </c:if>
        </div>
        
        <button type="submit" class="btn">S'inscrire</button>
    </form>
</body>
</html>