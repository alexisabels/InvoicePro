package com.example.invoicepro.venta;


import com.example.invoicepro.producto.DaoProductos;
import com.example.invoicepro.producto.Producto;
import com.example.invoicepro.usuario.DaoUsuarios;
import com.example.invoicepro.usuario.Usuario;
import com.example.invoicepro.venta.DaoVenta;
import com.example.invoicepro.venta.DetalleVenta;
import com.example.invoicepro.venta.Venta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "VentaController", urlPatterns = {"/ventas", "/nueva-venta", "/agregarVenta", "/buscarProductosVenta", "/addSelectedProduct"})
public class VentaController extends HttpServlet {

    private final DaoVenta daoVenta = new DaoVenta();
    private final DaoProductos daoProductos = new DaoProductos();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        switch (path) {
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
            case "/ventas":
                handleVentasRequest(request, response);
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
    private void handleVentasRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<Venta> ventas;
        HttpSession session = request.getSession();

        DaoUsuarios daoUsuarios = new DaoUsuarios();

        try {

            ventas = daoVenta.listVentas();


            List<String> nombresUsuarios = new ArrayList<>();
            for (Venta venta : ventas) {
                Usuario usuario = daoUsuarios.findById(venta.getIdUsuario());
                if (usuario != null) {
                    nombresUsuarios.add(usuario.getNombre());
                } else {
                    nombresUsuarios.add("Desconocido");
                }
            }

            request.setAttribute("ventas", ventas);
            request.setAttribute("nombresUsuarios", nombresUsuarios);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        request.getRequestDispatcher("/ventas.jsp").forward(request, response);
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
                producto.setId(producto.getId());
                producto.setCantidad(producto.getCantidad() - cantidad);
                if (producto.getCantidad() > 0) {
                    producto.setEnStock(true);

                }
                try {
                    daoProductos.updateProduct(producto);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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
        session.removeAttribute("clientId");

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

    private void handleSearchProductsToSell(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String query = request.getParameter("query");
        List<Producto> productos = null;

        if (query != null && !query.trim().isEmpty()) {

            productos = daoProductos.searchProducts(query);
        }

        request.setAttribute("productos", productos);

        request.getRequestDispatcher("/nuevaVenta.jsp").forward(request, response);
    }
    private void handleClearSelectedProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("selectedProducts");

        response.sendRedirect(request.getContextPath() + "/nueva-venta");
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

}
