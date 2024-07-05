<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navbar</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        body {
            display: flex;
            background-color: white;
        }

        .sidebar {
            width: 250px;
            height: 100vh;
            background-color: #343a40;
            color: white;
            position: fixed;
        }

        .sidebar a {
            color: white;
            display: block;
            padding: 35px;
            text-align: center;
            font-size: large;
            text-decoration: none;
        }

        .sidebar .new {
            background-color: rgb(70, 112, 12);
            color: white;
        }

        .sidebar .new:hover {
            background-color: rgb(110, 180, 5);
        }

        .sidebar a:hover {
            background-color: #495057;
        }

        .content {
            margin-left: 250px;
            padding: 20px;
            width: 100%;
        }
    </style>
</head>

<body>
<div class="sidebar">
    <h2 class="text-center mt-4">InvoicePro</h2>
    <a href="${pageContext.request.contextPath}/"><i class="fas fa-home"></i> Inicio</a>
    <a href="${pageContext.request.contextPath}/productos"><i class="fas fa-box-open"></i> Productos</a>
    <a href="${pageContext.request.contextPath}/stock"><i class="fa-solid fa-layer-group"></i> Stock actual</a>
    <a href="${pageContext.request.contextPath}/clientes"><i class="fas fa-users"></i> Clientes</a>
    <a href="${pageContext.request.contextPath}/usuarios"><i class="fa-solid fa-user-tie"></i></i> Usuarios</a>
    <a href="${pageContext.request.contextPath}/ventas"><i class="fa-solid fa-receipt"></i> Ventas</a>
    <a class="new" href="${pageContext.request.contextPath}/nueva-venta"><i class="fa-solid fa-cart-shopping"></i> <strong>NUEVA VENTA</strong></a>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

</body>
</html>
