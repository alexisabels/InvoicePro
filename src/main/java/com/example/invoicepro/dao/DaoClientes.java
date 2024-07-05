package com.example.invoicepro.dao;

import com.example.invoicepro.entities.Cliente;
import com.example.invoicepro.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;

public class DaoClientes extends BaseJPADao {
    public DaoClientes() {
    }

    public List<Cliente> listClientes() throws SQLException, Exception {
        List<Cliente> clientes = null;
        EntityManager em = getEntityManager();
        Query allUsersQuery = em.createNamedQuery("Clientes.findAll", Cliente.class);
        clientes = allUsersQuery.getResultList();
        for (Cliente c : clientes) {
            em.refresh(c);
        }
        em.close();
        return clientes;
    }
    public void addCliente(Cliente cliente) throws SQLException, Exception {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
        em.close();
    }

}
