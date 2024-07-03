package com.example.invoicepro;

import com.example.invoicepro.dao.DaoProductos;
import com.example.invoicepro.dao.PersistenceManagerSingleton;
import com.example.invoicepro.entities.Producto;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DaoProductos daoProductos = new DaoProductos();

        try {
            List<Producto> productos = daoProductos.listProducts();
            for (Producto producto : productos) {
                System.out.println(productos.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PersistenceManagerSingleton.getInstance().closeEntityManagerFactory();
        }
    }
}
