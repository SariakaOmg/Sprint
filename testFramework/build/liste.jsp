<%@ page import="main.java.vola.*" %>
<%@ page import="java.util.ArrayList" %>

<% 
 ArrayList<Cheque> ch=(ArrayList <Cheque>)  request.getAttribute("cheques");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1>Liste des cheques</h1>
    <table border=1>
    <tr>
        <th>Numero Compte</th>
        <th>Numero Cheque</th>
        <th>Date de validite</th>
        <th>Action</th>
    </tr>

    <% for(Cheque a: ch) {%>
    <tr>
    <td><%= a.getNumeroCompte() %></td>
    <td><%= a.getNumeroCheque() %></td>
    <td><%= a.getDate().toString() %></td>
    <td><a href="cheque/mod/<%=a.getId() %>">Modifier</a>
    <a href="cheque/delete/<%=a.getId() %>" onclick="effacer()">Supprimer</a></td>
    </tr>
    <% }%>
    </table>

    <script>
        function effacer(){
            confirm("Voulez-vous supprimer ce cheque?");
        }
    </script>
</body>
</html>