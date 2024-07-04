<%@ page import="com.example.invoicepro.entities.Producto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="navbar.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<div class="content">
    <h1 class="mb-4">Usuarios de la empresa</h1>
    <div class="mb-3 d-flex justify-content-left align-items-center">
        <div>
            <button id="addProductButton" class="btn btn-primary"><i class="fas fa-plus me-2"></i>Añadir</button>
        </div>
        <div class="d-flex ms-3">
            <input class="form-control me-2" type="search" placeholder="Buscar" aria-label="Buscar">
            <button class="btn btn-outline-primary" type="submit">Buscar</button>
        </div>
    </div>
    <table class="table table-hover table-bordered table-striped">
        <thead class="thead-dark">
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Nombre</th>
            <th scope="col">Fecha registro</th>
            <th scope="col">Acciones</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="usuario" items="${usuarios}">
            <tr>
                <td>${usuario.id}</td>
                <td>${usuario.nombre}</td>
                <td>${usuario.fechaCreacion}</td>

                <td>
                    <div class="dropdown">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton${usuario.id}" data-bs-toggle="dropdown" aria-expanded="false">
                            Acciones
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton${usuario.id}">
                            <li><a class="dropdown-item" href="editarUsuario?id=${usuario.id}"><i class="fa-solid fa-pen"></i> Editar</a></li>
                            <li><a class="dropdown-item" href="eliminarUsuario?id=${usuario.id}"><i class="fa-solid fa-trash" style="color: #ff0040;"></i> Eliminar</a></li>
                        </ul>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
