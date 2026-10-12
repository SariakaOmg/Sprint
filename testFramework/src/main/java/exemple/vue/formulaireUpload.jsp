<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload de fichier</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f4f4; display: flex; justify-content: center; padding-top: 60px; }
        form { background: #fff; padding: 25px 30px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,.15); width: 350px; }
        h2 { margin-top: 0; }
        label { display: block; margin-bottom: 6px; font-weight: bold; }
        input[type="file"] { margin-bottom: 15px; width: 100%; }
        button { padding: 8px 18px; border: none; background: #2d7ff9; color: #fff; border-radius: 4px; cursor: pointer; }
        button:hover { background: #1b63cc; }
    </style>
</head>
<body>
    <form action="Mae/exam" method="get" enctype="multipart/form-data">
        <h2>Uploader un fichier</h2>

        <label for="fichier">Choisir un fichier :</label>
        <input type="file" id="fichier" name="Objet1" required>

        <button type="submit">Envoyer</button>
    </form>
</body>
</html>
