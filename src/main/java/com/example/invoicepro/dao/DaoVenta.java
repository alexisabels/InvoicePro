package com.example.invoicepro.dao;


import com.example.invoicepro.entities.Venta;
import jakarta.persistence.EntityManager;

import java.sql.SQLException;

public class DaoVenta extends BaseJPADao{

    public void addVenta(Venta venta) throws SQLException, Exception {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(venta);
        em.getTransaction().commit();
        em.close();
    }
}
