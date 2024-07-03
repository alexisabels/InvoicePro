<%@ page import="com.example.invoicepro.entities.Producto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Listado de Productos</title>
</head>
<body>
<h1>Productos</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Descripción</th>
        <th>Precio</th>
        <th>Cantidad</th>
        <th>Foto URL</th>
    </tr>
    <%
        List<Producto> productos = (List<Producto>) request.getAttribute("productos");
        for (Producto producto : productos) {
    %>
    <tr>
        <td><%= producto.getId() %></td>
        <td><%= producto.getNombre() %></td>
        <td><%= producto.getDescripcion() %></td>
        <td><%= producto.getPrecio() %></td>
        <td><%= producto.getCantidad() %></td>
        <td><%= producto.getFotoUrl() %></td>
        <td><%= producto.isEnStock() %></td>

    </tr>
    <%
        }
    %>
</table>
</body>
</html>
