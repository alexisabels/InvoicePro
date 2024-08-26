<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="navbar.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="content">

    <form id="formAddVenta" class="row g-3 needs-validation w-25 m-auto mt-5" novalidate action="${pageContext.request.contextPath}/agregarVenta" method="post">
        <div class="col-md-4">
            <label for="idProducto" class="form-label">ID Producto</label>
            <input type="number" class="form-control" id="idProducto" name="idProducto" required>
            <div class="valid-feedback">
                Looks good!
            </div>
        </div>
        <div class="col-md-4">
            <label for="Cantidad" class="form-label">Cantidad</label>
            <input type="number" class="form-control" id="cantidad" name="cantidad" required>

        </div>
        <div class="col-md-4">
            <label for="precioUnitario" class="form-label">Precio unitario</label>
            <div class="input-group">
                <input type="number" class="form-control" id="precioUnitario" name="precioUnitario" aria-describedby="inputGroupPrepend"
                       required>

            </div>
        </div>
        <div class="col-md-6">
            <label for="idCliente" class="form-label">ID Cliente</label>
            <input type="number" class="form-control" id="idCliente" name="idCliente" required>

        </div>
        <div class="col-md-6">
            <label for="idUsuario" class="form-label">ID Usuario</label>
            <input type="number" class="form-control" id="idUsuario" name="idUsuario" required>

        </div>
        <div class="col-md-3">
            <label for="validationCustom05" class="form-label">Total</label>
            <input type="text" class="form-control" id="validationCustom05" required>

            </input>

            <div class="col-12">
                <button class="btn btn-primary" type="submit">Submit form</button>
            </div> </div>
    </form>


</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
