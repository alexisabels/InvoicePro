package com.example.invoicepro.usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UsuarioController", urlPatterns = {"/usuarios", "/agregarUsuario", "/eliminarUsuario"})
public class UsuarioController extends HttpServlet {

    private final DaoUsuarios daoUsuarios = new DaoUsuarios();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/agregarUsuario".equals(path)) {
            handleAddUser(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();

        switch (path) {
            case "/usuarios":
                handleUsuariosRequest(request, response);
                break;
            case "/eliminarUsuario":
                handleDeleteUser(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
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

    private void handleAddUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nombre = request.getParameter("nombre");
        String contrasena = request.getParameter("contraseña");

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setContraseña(contrasena);
        try {
            daoUsuarios.addUser(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect(request.getContextPath() + "/usuarios");
    }

    private void handleDeleteUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            daoUsuarios.deleteUser(id);
            response.sendRedirect(request.getContextPath() + "/usuarios");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
