package com.example.invoicepro.dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class BaseJPADao {
    public BaseJPADao() {
    }

    public static EntityManager getEntityManager() {
        EntityManagerFactory emf = PersistenceManagerSingleton.getInstance().getEntityManagerFactory();
        return emf.createEntityManager();
    }
}
