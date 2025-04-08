<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <form action="Authenticate" method="POST">
        <label for="name">Entrer votre nom : </label>
        <input type="text" id="name" name="name" placeholder="randy" required><br>
        <label for="password">Entrer votre mot de passe : </label>
        <input type="password" name="password" id="password" placeholder="3227" required><br>
        <% String error=(String) session.getAttribute("error"); if (error !=null) { %>
            <p style="color: red;">
                <%= " mot de passe ou nom "+error %>
            </p>
            <% session.removeAttribute("error");} %><br>
        <button type="submit">Valider</button>
    </form>
</body>
</html>