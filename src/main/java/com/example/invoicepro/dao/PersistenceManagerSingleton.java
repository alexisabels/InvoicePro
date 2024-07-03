package com.example.invoicepro.dao;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceManagerSingleton {
    public static final String UNIDAD_PERSISTENCIA = "InvoiceProPU";

    private static PersistenceManagerSingleton instance = null;
    private EntityManagerFactory emf;

    private PersistenceManagerSingleton() {
    }

    public static PersistenceManagerSingleton getInstance() {
        if (instance == null) {
            instance = new PersistenceManagerSingleton();
        }
        return instance;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(UNIDAD_PERSISTENCIA);
        }
        return emf;
    }

    public void closeEntityManagerFactory() {
        if (emf != null) {
            emf.close();
            emf = null;
        }
    }
}
