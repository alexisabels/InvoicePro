package com.example.invoicepro;

import com.example.invoicepro.dao.DaoClientes;
import com.example.invoicepro.dao.DaoProductos;
import com.example.invoicepro.dao.DaoUsuarios;
import com.example.invoicepro.dao.DaoVenta;
import com.example.invoicepro.entities.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "mainController", urlPatterns = {"/", "/productos", "/clientes", "/usuarios", "/nueva-venta", "/agregarVenta", "/ventas", "/stock", "/agregarProducto", "/editarProducto", "/agregarUsuario", "/eliminarProducto", "/eliminarUsuario"})
public class MainController extends HttpServlet {

    private DaoProductos daoProductos = new DaoProductos();
    private DaoUsuarios daoUsuarios = new DaoUsuarios();
    private DaoClientes daoClientes = new DaoClientes();
    private DaoVenta daoVenta = new DaoVenta();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/agregarProducto":
                handleAddProduct(request, response);
                break;
            case "/editarProducto":
                handleEditProduct(request, response);
                break;
            case "/agregarUsuario":
                handleAddUser(request, response);
                break;
            case "/agregarVenta":
                handleAddVenta(request, response);
                break;
            case "/buscarProductosVenta":
                try {
                    handleSearchProductsToSell(request, response);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                break;
            case "/addSelectedProduct":
                handleAddSelectedProduct(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        String action = request.getParameter("action");



        switch (path) {
            case "/":
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;

            case "/productos":
                handleProductosRequest(request, response);
                break;
            case "/clientes":
                handleClientesRequest(request, response);
                break;
            case "/stock":
                handleStockRequest(request, response);
                break;
            case "/usuarios":
                handleUsuariosRequest(request, response);
                break;
            case "/eliminarProducto":
                handleDeleteProduct(request, response);
                break;
            case "/eliminarUsuario":
                handleDeleteUser(request, response);
                break;
            case "/ventas":
                // implementar lógica para ventas
                break;

            case "/nueva-venta":
                if ("clear".equals(action)) {
                    handleClearSelectedProducts(request, response);
                } else {
                    request.getRequestDispatcher("/nuevaVenta.jsp").forward(request, response);
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void handleRemoveSelectedProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        HttpSession session = request.getSession();
        List<Producto> selectedProducts = (List<Producto>) session.getAttribute("selectedProducts");

        if (selectedProducts != null) {
            selectedProducts.removeIf(producto -> producto.getId() == id);
            session.setAttribute("selectedProducts", selectedProducts);
        }

        response.sendRedirect(request.getContextPath() + "/nueva-venta");
    }


    private void handleAddSelectedProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int cantidadDisponible = Integer.parseInt(request.getParameter("cantidadDisponible"));

        Producto productoSeleccionado = new Producto();
        productoSeleccionado.setId(id);
        productoSeleccionado.setNombre(nombre);
        productoSeleccionado.setPrecio(precio);
        productoSeleccionado.setCantidad(cantidadDisponible);

        HttpSession session = request.getSession();
        List<Producto> selectedProducts = (List<Producto>) session.getAttribute("selectedProducts");

        if (selectedProducts == null) {
            selectedProducts = new ArrayList<>();
        }

        selectedProducts.add(productoSeleccionado);
        session.setAttribute("selectedProducts", selectedProducts);


        response.sendRedirect(request.getContextPath() + "/nueva-venta");
    }

    private void handleAddVenta(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

        Venta venta = new Venta();
        venta.setIdCliente(idCliente);
        venta.setIdUsuario(idUsuario);

        HttpSession session = request.getSession();
        List<Producto> selectedProducts = (List<Producto>) session.getAttribute("selectedProducts");

        List<DetalleVenta> detalles = new ArrayList<>();

        double total = 0.0;

        if (selectedProducts != null) {
            for (Producto producto : selectedProducts) {
                int cantidad = Integer.parseInt(request.getParameter("cantidad_" + producto.getId()));

                // Crear un nuevo detalle de venta
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setIdProducto(producto.getId());
                detalleVenta.setCantidad(cantidad);
                detalleVenta.setPrecioUnitario(producto.getPrecio());
                detalleVenta.setVenta(venta);
  detalles.add(detalleVenta);
  total += cantidad * producto.getPrecio();
            }
        }
        venta.setDetalles(detalles);
        venta.setTotal(total);

        try {
            daoVenta.addVenta(venta);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        session.removeAttribute("selectedProducts");

        response.sendRedirect(request.getContextPath() + "/nueva-venta");
    }

    private void handleClearSelectedProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("selectedProducts");

        response.sendRedirect(request.getContextPath() + "/nueva-venta");
    }
    private void handleSearchProductsToSell(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String query = request.getParameter("query");
        List<Producto> productos = null;

        if (query != null && !query.trim().isEmpty()) {

            productos = daoProductos.searchProducts(query);
        }

        request.setAttribute("productos", productos);

        request.getRequestDispatcher("/nuevaVenta.jsp").forward(request, response);
    }

    private void handleProductosRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<Producto> productos;
        HttpSession session = request.getSession();

        try {
            if (query != null && !query.isEmpty()) {
                productos = daoProductos.searchProducts(query);
            } else {
                productos = daoProductos.listProducts();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/productos.jsp").forward(request, response);
    }

    private void handleStockRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<Producto> productos;

        try {
            if (query != null && !query.isEmpty()) {
                productos = daoProductos.searchStockProducts(query);
            } else {
                productos = daoProductos.listStockProducts();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("/stock.jsp").forward(request, response);
    }

    private void handleUsuariosRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> usuarios;
        try {
            usuarios = daoUsuarios.listUsers();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/usuarios.jsp").forward(request, response);
    }

    private void handleAddProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        String fotoUrl = request.getParameter("fotoUrl");

        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setCantidad(cantidad);
        producto.setFotoUrl(fotoUrl);
        if (cantidad > 0) {
            producto.setEnStock(true);
        }
        try {
            daoProductos.addProduct(producto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/productos");
    }

    private void handleDeleteProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            daoProductos.deleteProduct(id);
            response.sendRedirect(request.getContextPath() + "/productos");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void handleEditProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        String fotoUrl = request.getParameter("fotoUrl");

        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setCantidad(cantidad);
        producto.setFotoUrl(fotoUrl);
        if (cantidad > 0) {
            producto.setEnStock(true);
        }
        try {
            daoProductos.updateProduct(producto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/productos");
    }


    private void handleAddUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String contraseña = request.getParameter("contraseña");

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setContraseña(contraseña);
        try {
            daoUsuarios.addUser(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/usuarios");
    }

    private void handleDeleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            daoUsuarios.deleteUser(id);
            response.sendRedirect(request.getContextPath() + "/usuarios");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void handleClientesRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<Cliente> clientes;
        HttpSession session = request.getSession();

        try {
            if (query != null && !query.isEmpty()) {
                clientes = daoClientes.searchClientes(query);
            } else {
                clientes = daoClientes.listClientes();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("clientes", clientes);
        request.getRequestDispatcher("/clientes.jsp").forward(request, response);

    }
}



