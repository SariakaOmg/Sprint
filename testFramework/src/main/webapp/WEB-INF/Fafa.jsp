<%@ page import="java.util.ArrayList" %>
<%
    ArrayList<Object> items = (ArrayList<Object>) request.getAttribute("key");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
    <h1>Liste</h1>
    <ul>
    <% if (items != null) {
        for (Object item : items) { %>
        <li><%= item %></li>
    <%  }
    } %>
    </ul>
</body>
</html>