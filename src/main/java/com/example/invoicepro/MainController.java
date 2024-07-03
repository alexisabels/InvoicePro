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

@WebServlet(name = "mainController", urlPatterns = {"/productos", "/usuarios", "/ventas"})
public class MainController extends HttpServlet {

    private DaoProductos daoProductos = new DaoProductos();
    private DaoUsuarios daoUsuarios = new DaoUsuarios();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/productos":
                List<Producto> productos = null;
                try {
                    productos = daoProductos.listProducts();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                request.setAttribute("productos", productos);
                request.getRequestDispatcher("/productos.jsp").forward(request, response);
                break;
            case "/usuarios":
                List<Usuario> usuarios = null;
                try {
                    usuarios = daoUsuarios.listUsers();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                request.setAttribute("usuarios", usuarios);
                request.getRequestDispatcher("/usuarios.jsp").forward(request, response);
                break;
            case "/ventas":
                // implementar lógica para ventas
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }
}
