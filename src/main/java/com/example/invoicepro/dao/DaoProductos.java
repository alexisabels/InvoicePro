package com.example.invoicepro.dao;

import com.example.invoicepro.entities.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
}
