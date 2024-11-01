
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="content">
    <h1 class="mb-4">Listado de Ventas</h1>

    <c:choose>
        <c:when test="${empty ventas}">
            <h2 class="text-danger mt-5">No se encontraron ventas.</h2>
        </c:when>
        <c:otherwise>
            <table class="table table-hover table-bordered table-striped">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Cliente</th>
                    <th scope="col">Usuario</th>
                    <th scope="col">Fecha</th>
                    <th scope="col">Total</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="venta" items="${ventas}" varStatus="status">
                    <tr>
                        <td>${venta.id}</td>
                        <td><a href="clientes?query=${venta.idCliente}">${venta.idCliente}</a></td>
                        <td>${nombresUsuarios[status.index]}</td>
                        <td>${venta.fechaVenta}</td>
                        <td>${venta.total} €</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
