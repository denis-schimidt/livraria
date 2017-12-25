package com.schimidt.jsf.infra;

import com.schimidt.jsf.dao.DAO;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import static javax.persistence.Persistence.createEntityManagerFactory;

public class JpaProducer {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = createEntityManagerFactory("livraria");

    @Inject
    private EntityManager entityManager;

    @Produces
    @RequestScoped
    public EntityManager newEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public void close(@Disposes EntityManager em) {
        em.close();
    }

    @Produces
    public <T extends Serializable> DAO<T> newInstance(InjectionPoint injectionPoint){
        ParameterizedType type = (ParameterizedType) injectionPoint.getType();
        Class classe = (Class) type.getActualTypeArguments()[0];

        return new DAO<>(classe, entityManager);
    }
}
