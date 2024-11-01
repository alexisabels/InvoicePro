package com.example.invoicepro.cliente;

import com.example.invoicepro.dao.BaseJPADao;
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
    public List<Cliente> searchClientes(String query) throws SQLException, Exception {
        List<Cliente> clientes = null;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Cliente> searchQuery = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.email LIKE :query OR c.telefono LIKE :query", Cliente.class);
            searchQuery.setParameter("query", "%" + query + "%");
            clientes = searchQuery.getResultList();
        } finally {
            em.close();
        }
        return clientes;
    }
    public List<Cliente> searchClientesFromId(String query) throws SQLException, Exception {
        List<Cliente> clientes = null;
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Cliente> searchQuery = em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.id = :query", Cliente.class);
            searchQuery.setParameter("query", Long.valueOf(query));
            clientes = searchQuery.getResultList();
        } finally {
            em.close();
        }
        return clientes;
    }
}
