package com.example.invoicepro.cliente;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ClienteController", urlPatterns = {"/clientes", "/agregarCliente"})
public class ClienteController extends HttpServlet {

    private final DaoClientes daoClientes = new DaoClientes();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/agregarCliente".equals(path)) {
            handleAddCliente(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/clientes".equals(path)) {
            handleClientesRequest(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleClientesRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        List<Cliente> clientes;
        HttpSession session = request.getSession();

        try {
            if (query != null && !query.isEmpty()) {

                if (isNumeric(query)) {
                    // Primero intenta buscar por ID
                    List<Cliente> foundById = daoClientes.searchClientesFromId(query);
                    if (!foundById.isEmpty()) {
                        clientes = foundById; // Si hay resultados por ID, usa eso
                    } else {
                        // Si no se encuentra por ID, intenta buscar por teléfono
                        clientes = daoClientes.searchClientes(query);
                    }
                } else {
                    // Si no es numérico, solo busca por teléfono o email
                    clientes = daoClientes.searchClientes(query);
                }
            } else {
                clientes = daoClientes.listClientes();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        request.setAttribute("clientes", clientes);
        request.getRequestDispatcher("/clientes.jsp").forward(request, response);

    }
    private void handleAddCliente(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("mail");
        String telefono = request.getParameter("telefono");

        Cliente cliente = new Cliente();
        cliente.setEmail(correo);
        cliente.setTelefono(telefono);

        try {
            daoClientes.addCliente(cliente);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        HttpSession session = request.getSession();
        session.setAttribute("clientId", cliente.getId());

        response.sendRedirect(request.getContextPath() + "/nueva-venta");

    }
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}
