package com.example.invoicepro.dao;

import com.example.invoicepro.entities.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;

public class DaoProductos extends BaseJPADao {
    public DaoProductos() {
    }

    public List<Producto> listProducts() throws SQLException, Exception {
        List<Producto> productos = null;
        EntityManager em = getEntityManager();
        Query allProductsQuery = em.createNamedQuery("Productos.findAll", Producto.class);
        productos = allProductsQuery.getResultList();
        for (Producto p : productos) {
            em.refresh(p);
        }
        em.close();
        return productos;
    }

    public List<Producto> listStockProducts() throws SQLException, Exception {
        List<Producto> productos = null;
        EntityManager em = getEntityManager();
        TypedQuery<Producto> consulta = em.createQuery("SELECT p FROM Producto p WHERE p.enStock = true", Producto.class);
        productos = consulta.getResultList();
        for (Producto p : productos) {
            em.refresh(p);
        }
        em.close();
        return productos;
    }

    public List<Producto> searchProducts(String query) throws SQLException, Exception {
        EntityManager em = getEntityManager();
        List<Producto> productos = null;
        try {
            int id = Integer.parseInt(query);
            Producto producto = em.find(Producto.class, id);
            if (producto != null) {
                productos = List.of(producto);
            } else {
                productos = List.of();
            }
        } catch (NumberFormatException e) {
            // SI NO ES UN NUMERO, BUSCAR POR NOMBRE
            TypedQuery<Producto> consulta = em.createQuery("SELECT p FROM Producto p WHERE p.nombre LIKE :nombre AND p.enStock = true", Producto.class);
            consulta.setParameter("nombre", "%" + query + "%");
            productos = consulta.getResultList();
        }
        em.close();
        return productos;
    }

    public List<Producto> searchStockProducts(String query) throws SQLException, Exception {
        EntityManager em = getEntityManager();
        List<Producto> productos = null;
        try {
            int id = Integer.parseInt(query);
            Producto producto = em.find(Producto.class, id);
            if (producto != null) {
                productos = List.of(producto);
            } else {
                productos = List.of();
            }
        } catch (NumberFormatException e) {
            // SI NO ES UN NUMERO, BUSCAR POR NOMBRE
            TypedQuery<Producto> consulta = em.createQuery("SELECT p FROM Producto p WHERE p.nombre LIKE :nombre", Producto.class);
            consulta.setParameter("nombre", "%" + query + "%");
            productos = consulta.getResultList();
        }
        em.close();
        return productos;
    }

    public void addProduct(Producto producto) throws SQLException, Exception {
    EntityManager em = getEntityManager();
    em.getTransaction().begin();
    em.persist(producto);
    em.getTransaction().commit();
    em.close();
}

    public void deleteProduct(int id) {
        EntityManager em = getEntityManager();
        Producto p = em.find(Producto.class, id);
        if (p != null) {
            EntityTransaction utx = em.getTransaction();
            utx.begin();
            em.remove(p);
            utx.commit();
        }
        em.close();
    }

}
