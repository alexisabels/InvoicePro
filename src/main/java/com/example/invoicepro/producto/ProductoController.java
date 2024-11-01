package com.example.invoicepro.producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductoController", urlPatterns = {"/productos", "/agregarProducto", "/editarProducto", "/eliminarProducto", "/stock"})
public class ProductoController extends HttpServlet {

    private final DaoProductos daoProductos = new DaoProductos();

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
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/productos":
                handleProductosRequest(request, response);
                break;
            case "/stock":
                handleStockRequest(request, response);
                break;
            case "/eliminarProducto":
                handleDeleteProduct(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void handleAddProduct(HttpServletRequest request, HttpServletResponse response) throws   ServletException, IOException {
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

    private void handleDeleteProduct(HttpServletRequest request, HttpServletResponse response)  {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            daoProductos.deleteProduct(id);
            response.sendRedirect(request.getContextPath() + "/productos");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void handleEditProduct(HttpServletRequest request, HttpServletResponse response) throws  IOException {
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

}
