<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, models.*" %>
<%
List<Prevision> previsions = (List<Prevision>) request.getAttribute("previsions");
String error = (String)request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Ajout de dépense</h1>
    <form action="Depense" method="POST">
        <label for="libelle">Veuillez selectioner le libelle</label>
        <select name="libelle" id="libelle">
            <% if(previsions != null && previsions.size() > 0){ 
                for(Prevision prevision : previsions){ %>
                   <option value="<%= prevision.getId() %>"><%= prevision.getLibelle() %></option>
               <% } %>
            <% } %>
        </select><br>
        <label for="montant">Entrer le montant : </label><br>
        <input type="number" name="montant" id="montant" required>
        <% if(error != "" && error != null){ %>
            <p style="color : red"><%= error %></p>
        <% }  %>
        <button type="submit">Valider</button>
    </form>
    <a href="Etat">Voir l'état</a>
</body>
</html>