<%@ page import="com.example.invoicepro.entities.Producto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="content">
    <!-- Aquí irá el contenido específico de la página de productos -->
    <h1>Productos</h1>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Descripción</th>
            <th>Precio</th>
            <th>Cantidad</th>
            <th>Foto URL</th>
            <th>En Stock</th>
        </tr>
        </thead>
        <tbody>
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
            <td class="<c:if test="${producto.enStock}">bg-success</c:if><c:if test="${!producto.enStock}">bg-danger</c:if>">
                <c:if test="${producto.enStock}">Sí</c:if>
                <c:if test="${!producto.enStock}">No</c:if>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

</div>

