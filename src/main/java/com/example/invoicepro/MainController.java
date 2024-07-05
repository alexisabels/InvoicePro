package com.example.invoicepro;

import com.example.invoicepro.dao.DaoProductos;
import com.example.invoicepro.dao.DaoUsuarios;
import com.example.invoicepro.entities.Producto;
import com.example.invoicepro.entities.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
@WebServlet(name = "mainController", urlPatterns = {"/", "/productos", "/usuarios", "/ventas", "/stock", "/agregarProducto", "/editarProducto", "/agregarUsuario", "/eliminarProducto"})
public class MainController extends HttpServlet {

    private DaoProductos daoProductos = new DaoProductos();
    private DaoUsuarios daoUsuarios = new DaoUsuarios();

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
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();



        switch (path) {
            case "/":
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;

            case "/productos":
                handleProductosRequest(request, response);
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
            case "/ventas":
                // implementar lógica para ventas
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
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
}
