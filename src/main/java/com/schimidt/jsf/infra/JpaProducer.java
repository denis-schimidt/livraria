package com.schimidt.jsf.infra;

import com.schimidt.jsf.dao.DAO;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public class JpaProducer {

    @Produces
    @PersistenceContext
    private EntityManager entityManager;

    @Produces
    public <T extends Serializable> DAO<T> newInstance(InjectionPoint injectionPoint){
        ParameterizedType type = (ParameterizedType) injectionPoint.getType();
        Class classe = (Class) type.getActualTypeArguments()[0];

        return new DAO<>(classe, entityManager);
    }
}
