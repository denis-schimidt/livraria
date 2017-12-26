package com.schimidt.jsf.infra;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import java.io.Serializable;

@Interceptor
@CustomizedTransaction
@Priority(Interceptor.Priority.APPLICATION + 1)
class TransacaoCustomizadaInterceptor implements Serializable {
    private static final long serialVersionUID = 3725067692991420110L;
    @Inject
    private EntityManager entityManager;

    @AroundInvoke
    Object executaTransacao(InvocationContext invocationContext){

        try{
            entityManager.getTransaction().begin();
            Object result = invocationContext.proceed();
            entityManager.getTransaction().commit();

            return result;

        }catch (Exception e){
            entityManager.getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }
}
