package com.example.invoicepro.usuario;

import com.example.invoicepro.dao.BaseJPADao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
    public void addUser(Usuario usuario) throws SQLException, Exception {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
        em.close();
    }
    public void deleteUser(int id) {
        EntityManager em = getEntityManager();
        Usuario u = em.find(Usuario.class, id);
        if (u != null) {
            EntityTransaction utx = em.getTransaction();
            utx.begin();
            em.remove(u);
            utx.commit();
        }
        em.close();
    }
    public Usuario findById(int id) throws SQLException, Exception {
        EntityManager em = getEntityManager();
        Usuario usuario = em.find(Usuario.class, id);
        em.close();
        return usuario;
    }

}
