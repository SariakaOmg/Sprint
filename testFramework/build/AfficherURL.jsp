<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String url = (String) request.getAttribute("url"); %>
<html><body>
<h1>Afficher URL</h1>
<p>URL reçue : <%= url != null ? url : request.getRequestURI() %></p>
</body></html>