<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload de fichier</title>
</head>
<body>
    <h1>Uploader un fichier</h1>
    <form action="upload" method="POST" enctype="multipart/form-data">
        <label>Description:
            <input type="text" name="description" required>
        </label><br>
        <label>Fichier:
            <input type="file" name="file" required>
        </label><br>
        <button type="submit">Uploader</button>
    </form>
</form>
</body>
</html>