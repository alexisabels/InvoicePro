<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="navbar.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!doctype html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Venta de Productos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous" />
</head>
<body>
<div class="container mt-5">
    <div class="row">
        <!-- Panel de Productos Disponibles (Izquierda) -->
        <div class="col-md-6">
            <h2>Productos Disponibles</h2>
            <form action="${pageContext.request.contextPath}/buscarProductosVenta" method="post" class="mb-3">
                <input class="form-control me-2" type="search" name="query" placeholder="Escribe ID o Nombre" aria-label="Buscar">
                <button class="btn btn-outline-primary w-100 mt-3" type="submit">Buscar</button>
            </form>
            <c:choose>
                <c:when test="${empty productos}">
                </c:when>
                <c:otherwise>
                    <div id="productosList">
                        <c:forEach var="producto" items="${productos}">
                            <form action="${pageContext.request.contextPath}/addSelectedProduct" method="post" class="mb-2">
                                <div class="card">
                                    <div class="card-body">
                                        <h5 class="card-title">${producto.nombre}</h5>
                                        <p class="card-text">Precio: ${producto.precio} - Cantidad disponible: ${producto.cantidad}</p>
                                        <input type="hidden" name="id" value="${producto.id}">
                                        <input type="hidden" name="nombre" value="${producto.nombre}">
                                        <input type="hidden" name="precio" value="${producto.precio}">
                                        <input type="hidden" name="cantidadDisponible" value="${producto.cantidad}">
                                        <button class="btn btn-primary" type="submit">Añadir</button>
                                    </div>
                                </div>
                            </form>
                        </c:forEach>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Panel de Productos Seleccionados (Derecha) -->
        <div class="col-md-6">
            <h2>Productos Seleccionados</h2>
            <form id="formAddVenta" class="row g-3 needs-validation" novalidate action="${pageContext.request.contextPath}/agregarVenta" method="post">
                <div class="col-md-6">
                    <label for="userId" class="form-label">ID Usuario</label>
                    <input type="text" class="form-control" id="userId" name="idUsuario" required>
                </div>
                <div class="col-md-6">
                    <label for="clientId" class="form-label">ID Cliente</label>
                    <input type="text" class="form-control" id="clientId" name="idCliente" required>
                </div>
                <c:forEach var="producto" items="${sessionScope.selectedProducts}">
                    <div class="d-flex align-items-center mb-2">
                        <input type="hidden" name="idProducto_${producto.id}" value="${producto.id}">
                        <span class="me-2">${producto.nombre} (${producto.precio})</span>
                        <input type="number" class="form-control me-2" id="cantidad_${producto.id}" name="cantidad_${producto.id}" min="1" max="${producto.cantidad}" value="1" data-precio="${producto.precio}">
                    </div>
                </c:forEach>
                <div class="col-md-4">
                    <label for="total" class="form-label">Total</label>
                    <input type="text" class="form-control" id="total" name="total" readonly>
                </div>


                <div class="col-12">
                    <a href="${pageContext.request.contextPath}/nueva-venta?action=clear" class="btn btn-secondary me-2">Limpiar Productos</a>
                    <button class="btn btn-primary" type="submit">Vender</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    document.addEventListener('input', function(event) {
        if (event.target.matches('input[name^="cantidad_"]')) {
            calcularTotal();
        }
    });

    function calcularTotal() {
        let total = 0;
        document.querySelectorAll('input[name^="cantidad_"]').forEach(function(cantidadInput) {
            const cantidad = parseFloat(cantidadInput.value) || 0;
            const precio = parseFloat(cantidadInput.dataset.precio) || 0;
            total += cantidad * precio;
        });
        document.getElementById("total").value = total.toFixed(2);
    }

    document.addEventListener('DOMContentLoaded', calcularTotal);


</script>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</body>
</html>