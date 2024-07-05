<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="content">
    <h1 class="mb-4">Listado de Clientes</h1>
    <table class="table table-hover table-bordered table-striped">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Correo</th>
            <th scope="col">Teléfono</th>
            <th scope="col">Fecha 1a compra</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="cliente" items="${clientes}">
            <tr>
                <td>${cliente.id}</td>
                <td>${cliente.email}</td>
                <td>${cliente.telefono}</td>
                <td>${cliente.fechaCreacion}</td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
