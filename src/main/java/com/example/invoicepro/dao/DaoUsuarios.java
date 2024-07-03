package com.example.invoicepro.dao;

import com.example.invoicepro.entities.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.sql.SQLException;
import java.util.List;

public class DaoUsuarios extends BaseJPADao {
    public DaoUsuarios() {
    }

    public List<Usuario> listUsers() throws SQLException, Exception {
        List<Usuario> usuarios = null;
        EntityManager em = getEntityManager();
        Query allUsersQuery = em.createNamedQuery("Usuarios.findAll", Usuario.class);
        usuarios = allUsersQuery.getResultList();
        for (Usuario u : usuarios) {
            em.refresh(u);
        }
        em.close();
        return usuarios;
    }
}
