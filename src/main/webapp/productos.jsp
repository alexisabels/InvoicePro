<%@ page import="com.example.invoicepro.entities.Producto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="content">
    <!-- Aquí irá el contenido específico de la página de productos -->
    <h1 class="mb-4">Productos</h1>
    <div class="mb-3 d-flex justify-content-left align-items-center">
        <div>
            <button id="addProductButton" class="btn btn-primary"><i class="fas fa-plus me-2"></i>Añadir</button>
        </div>
        <div class="d-flex ms-3">
            <form id="searchForm" action="${pageContext.request.contextPath}/productos" method="get" class="d-flex">
                <input class="form-control me-2" type="search" name="query" placeholder="Escribe ID o Nombre" aria-label="Buscar">
                <button class="btn btn-outline-primary" type="submit">Buscar</button>
                <c:if test="${not empty param.query}">
                    <a class="btn btn-danger ms-2 d-flex align-items-center" href="${pageContext.request.contextPath}/productos">
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
                    <th scope="col">En Stock</th>
                    <th scope="col">Acciones</th>
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
                        <td class="<c:if test='${producto.enStock}'>bg-success text-white rounded</c:if><c:if test='${!producto.enStock}'>bg-danger text-white rounded</c:if>">
                            <c:if test="${producto.enStock}">Sí</c:if>
                            <c:if test="${!producto.enStock}">No</c:if>
                        </td>
                        <td>
                            <div class="dropdown">
                                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton${producto.id}" data-bs-toggle="dropdown" aria-expanded="false">
                                    Acciones
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton${producto.id}">
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/editarProducto?id=${producto.id}"><i class="fa-solid fa-pen"></i> Editar</a></li>
                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/eliminarProducto?id=${producto.id}"><i class="fa-solid fa-trash" style="color: #ff0040;"></i> Eliminar</a></li>
                                </ul>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
