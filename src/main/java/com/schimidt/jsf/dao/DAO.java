package com.schimidt.jsf.dao;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class DAO<T> {

    private final Class<T> classe;
    private final EntityManager em;

    public DAO(Class<T> classe, final EntityManager em) {
        this.classe = classe;
        this.em = em;
    }

    public void adiciona(T t) {
        em.persist(t);
    }

    public void remove(T t) {
        em.remove(em.merge(t));
    }

    public void removePorId(Integer id) {
        T entidade = buscaPorId(id);

        if (entidade != null) {
            em.remove(entidade);
        }
    }

    public void atualiza(T t) {
        em.merge(t);
    }

    public List<T> listaTodos() {
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
        query.select(query.from(classe));

        TypedQuery<T> typedQuery = em.createQuery(query);
        typedQuery.setHint("org.hibernate.cacheable", "true");

        return typedQuery.getResultList();
    }

    public T buscaPorId(Integer id) {
        return em.find(classe, id);
    }

    public int contaTodos() {
        long result = (Long) em.createQuery("select count(n) from livro n")
                .getSingleResult();

        return (int) result;
    }

    public List<T> listaTodosPaginada(int firstResult, int maxResults, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(classe);
        final Root<T> from = query.from(classe);
        query.select(from);

        if (filtros != null) {
            final List<Predicate> predicates = filtros.entrySet()
                    .stream()
                    .map(entry -> builder.like(from.get(entry.getKey()), "%" + entry.getValue() + "%"))
                    .collect(toList());
            query.where(predicates.toArray(new Predicate[0]));
        }

        if (StringUtils.isNotBlank(campoOrdenacao) && sentidoOrdenacao != null) {
            final Path<Object> pathCampoOrdenacao = from.get(campoOrdenacao);
            query.orderBy("ASCENDING".equals(sentidoOrdenacao.name()) ? builder.asc(pathCampoOrdenacao) : builder.desc(pathCampoOrdenacao));
        }

        return em.createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();
    }
}
