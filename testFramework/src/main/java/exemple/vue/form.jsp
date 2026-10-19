<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulaire MonObjet</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/MonObjetAppel" method="get">
        <label for="id">Id :</label>
        <input type="number" id="id_Objet1" name="id_Objet1" required><br><br>

        <label for="nom">Nom :</label>
        <input type="text" id="nom_Objet1" name="nom_Objet1" required><br><br>

        <button type="submit">Submitee</button>
    </form>
</body>
</html>
