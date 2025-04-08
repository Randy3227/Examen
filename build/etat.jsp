<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, models.*" %>
<%
List<Prevision> previsions = (List<Prevision>) request.getAttribute("previsions");
int id = (int)request.getAttribute("id");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Etat</h1>
    <table border="1">
    <tr>
        <th>Prevision</th>
        <th>Montant</th>
        <th>Depense</th>
        <th>Reste</th>
    </tr>
    <% for(Prevision p : previsions){ %>
        <tr>
            <td><%= p.getLibelle() %></td>
            <td><%= p.getMontant() %></td>
            <td><%= Etat.somme_depense_par_prevision_par_user(p.getId(), id) %></td>
            <td><%= p.getMontant() - Etat.somme_depense_par_prevision_par_user(p.getId(), id) %></td>
        </tr>
    <% } %>
</table>
</body>
</html>