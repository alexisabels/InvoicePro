<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="agregarClienteModal" tabindex="-1" aria-labelledby="agregarClienteModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="agregarClienteModalLabel">Añadir Cliente</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="formAgregarCliente" action="${pageContext.request.contextPath}/agregarCliente" method="post">
                    <div class="mb-3">
                        <label for="mail" class="form-label">Correo</label>
                        <input type="text" class="form-control" id="mail" name="mail" >
                    </div>
                    <div class="mb-3">
                        <label for="telefono" class="form-label">Teléfono</label>
                        <input class="form-control" id="telefono" name="telefono" type="tel" />
                    </div>



                    <button type="submit" class="btn btn-primary">Guardar</button>
                </form>
            </div>
        </div>
    </div>

</div>
