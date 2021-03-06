package com.schimidt.jsf.dao;

import com.schimidt.jsf.modelo.Genero;
import com.schimidt.jsf.modelo.Livro;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class LivroDao implements Serializable {
    private static final long serialVersionUID = -6463566670384543950L;
    @Inject
    private EntityManager em;
    @Inject
    private DAO<Livro> dao;

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
            int indicePonto = campoOrdenacao.indexOf(".");
            Path<Object> pathCampoOrdenacao = indicePonto > 0 ? from.get(campoOrdenacao.substring(0, indicePonto)) : from.get(campoOrdenacao);

            query.orderBy("ASCENDING".equals(sentidoOrdenacao.name()) ? builder.asc(pathCampoOrdenacao) : builder.desc(pathCampoOrdenacao));
        }

        return em.createQuery(query)
                .setFirstResult(firstResult)
                .setMaxResults(maxResults)
                .getResultList();
    }

    public Long contaTodosFiltrado(Map<String, Object> filtros) {
        final CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        final Root<Livro> from = query.from(Livro.class);
        query.select(builder.count(from));

        if (filtros != null) {
            final List<Predicate> predicates = filtros.entrySet()
                    .stream()
                    .map(getEntryPredicateFunction(builder, from))
                    .collect(toList());
            query.where(predicates.toArray(new Predicate[0]));
        }

        return em.createQuery(query)
                .getSingleResult();
    }

    public int contaTodos() {
        return dao.contaTodos();
    }

    private Function<Map.Entry<String, Object>, Predicate> getEntryPredicateFunction(CriteriaBuilder builder, Root<Livro> from) {
        return entry -> {

            int indicePonto = entry.getKey().indexOf(".");
            String nomeCampo = indicePonto > 0 ? entry.getKey().substring(0, indicePonto) : entry.getKey();

            if ("genero".equals(nomeCampo)) {
                return builder.equal(from.get(nomeCampo), Genero.valueOf(entry.getValue().toString()));
            }

            if ("preco".equals(nomeCampo)) {
                return builder.le(from.get(nomeCampo), Double.valueOf(entry.getValue().toString()));
            }

            if ("dataLancamento".equals(nomeCampo)) {

                try {
                    Calendar dataLancamento = Calendar.getInstance();
                    dataLancamento.setTime((Date) entry.getValue());

                    return builder.lessThanOrEqualTo(from.get(nomeCampo), dataLancamento);

                } catch (DateTimeParseException e) {
                    return builder.and();
                }
            }

            return builder.like(from.get(nomeCampo), "%" + entry.getValue() + "%");
        };
    }
}
