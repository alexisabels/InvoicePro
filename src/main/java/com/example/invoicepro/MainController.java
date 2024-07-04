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
import java.io.IOException;
import java.util.List;

@WebServlet(name = "mainController", urlPatterns = {"/", "/productos", "/usuarios", "/ventas", "/stock"})
public class MainController extends HttpServlet {

    private DaoProductos daoProductos = new DaoProductos();
    private DaoUsuarios daoUsuarios = new DaoUsuarios();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/InvoicePro_war_exploded":
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
}
