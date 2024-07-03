package com.example.invoicepro;

import com.example.invoicepro.dao.DaoProductos;
import com.example.invoicepro.entities.Producto;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/productos":
                listarProductos(request, response);
                break;
            case "/usuarios":
                // implementar lógica para usuarios
                break;
            case "/ventas":
                // implementar lógica para ventas
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Producto> productos = daoProductos.listProducts(); // ejemplo con categoría 1
            request.setAttribute("productos", productos);
                request.getRequestDispatcher("/productos.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
