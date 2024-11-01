package com.example.invoicepro.venta;


import com.example.invoicepro.dao.BaseJPADao;
import com.example.invoicepro.producto.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.sql.SQLException;
import java.util.List;

public class DaoVenta extends BaseJPADao {

    public void addVenta(Venta venta) throws SQLException, Exception {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(venta);
        em.getTransaction().commit();
        em.close();
    }
    public List<Venta> listVentas() throws SQLException, Exception {
        List<Venta> ventas = null;
        EntityManager em = getEntityManager();
        Query allVentasQuery = em.createNamedQuery("Ventas.findAll", Venta.class);
        ventas = allVentasQuery.getResultList();
        for (Venta v : ventas) {
            em.refresh(v);
        }
        em.close();
        return ventas;
    }


}
