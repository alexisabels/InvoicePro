<%@ page import="com.example.invoicepro.entities.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Listado de Usuarios</title>
</head>
<body>
<h1>Usuarios</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Fecha de registro</th>
    </tr>
    <%
        List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
        for (Usuario usuario : usuarios) {
    %>
    <tr>
        <td><%= usuario.getId() %></td>
        <td><%= usuario.getNombre() %></td>
        <td><%= usuario.getFechaCreacion() %></td>


    </tr>
    <%
        }
    %>
</table>
</body>
</html>
