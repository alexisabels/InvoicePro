<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Navbar</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            display: flex;
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
    <a href="index.jsp">Inicio</a>
    <a href="productos.jsp">Productos</a>
    <a href="usuarios.jsp">Usuarios</a>
    <a href="ventas.jsp">Ventas</a>
    <a class="new" href="nueva-venta.jsp"><strong>NUEVA VENTA</strong></a>
</div>
</body>
</html>
