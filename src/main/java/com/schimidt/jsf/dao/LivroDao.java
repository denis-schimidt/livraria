package com.schimidt.jsf.dao;

import com.schimidt.jsf.modelo.Genero;
import com.schimidt.jsf.modelo.Livro;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class LivroDao {
    private EntityManager em;

    public LivroDao(EntityManager em) {
        this.em = em;
    }

    public List<Livro> listaTodosPaginada(int firstResult, int maxResults, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object>
            filtros) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Livro> query = builder.createQuery(Livro.class);
        final Root<Livro> from = query.from(Livro.class);
        query.select(from);

        if (filtros != null) {
            final List<Predicate> predicates = filtros.entrySet()
                    .stream()
                    .map(getEntryPredicateFunction(builder, from))
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

    private Function<Map.Entry<String, Object>, Predicate> getEntryPredicateFunction(CriteriaBuilder builder, Root<Livro> from) {
        return entry -> {

            if ("genero".equals(entry.getKey())) {
                return builder.equal(from.get(entry.getKey()), Genero.valueOf(entry.getValue().toString()));
            }

            return builder.like(from.get(entry.getKey()), "%" + entry.getValue() + "%");
        };
    }
}
