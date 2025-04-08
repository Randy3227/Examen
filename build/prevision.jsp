<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Ajouter Prevision</h1>
    <form action="Prevision" method="POST">
        <label for="libelle">Entrer le libellé : </label>
        <textarea name="libelle" id="libelle" required></textarea><br>
        <label for="montant">Entrer le montant : </label>
        <input type="number" name="montant" id="montant" required><br>
        <button type="submit">Valider</button>
    </form>
    <a href="Depense">Ajouter dépense</a><br>
    <a href="Etat">Voir l'état</a>
</body>
</html>