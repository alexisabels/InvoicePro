
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="content">
    <h1 class="mb-4">Lista de Productos en Stock</h1>
    <div class="mb-3 d-flex justify-content-left align-items-center">

        <div class="d-flex">
            <form id="searchForm" action="${pageContext.request.contextPath}/stock" method="get" class="d-flex">
                <input class="form-control me-2" type="search" name="query" placeholder="Escribe ID o Nombre" aria-label="Buscar">
                <button class="btn btn-outline-primary" type="submit">Buscar</button>
                <c:if test="${not empty param.query}">
                    <a class="btn btn-danger ms-2 d-flex align-items-center" href="${pageContext.request.contextPath}/stock">
                        <i class="fa-solid fa-rotate-left me-1"></i>Volver
                    </a>
                </c:if>
            </form>
        </div>
    </div>
<c:choose>
    <c:when test="${empty productos}">
        <h2 class="text-danger mt-5">No se encontraron productos.</h2>
    </c:when>
    <c:otherwise>
    <table class="table table-hover table-bordered table-striped">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Nombre</th>
            <th scope="col">Descripción</th>
            <th scope="col">Precio</th>
            <th scope="col">Cantidad</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="producto" items="${productos}">
            <tr>
                <td>${producto.id}</td>
                <td>${producto.nombre}</td>
                <td>${producto.descripcion}</td>
                <td>${producto.precio}</td>
                <td>${producto.cantidad}</td>

            </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:otherwise>
</c:choose>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
