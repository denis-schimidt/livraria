package com.schimidt.jsf.dao;

import com.schimidt.jsf.modelo.Usuario;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class UsuarioDao implements Serializable{
    private static final long serialVersionUID = 8266663420317357304L;
    @Inject
    private EntityManager em;

    public Optional<Usuario> obterUsuarioPor(final Usuario usuario) {

        if (StringUtils.isNotBlank(usuario.getEmail()) && StringUtils.isNotBlank(usuario.getSenha())) {
            CriteriaBuilder builder = em.getCriteriaBuilder();

            CriteriaQuery<Usuario> query = builder.createQuery(Usuario.class);
            Root<Usuario> from = query.from(Usuario.class);

            List<Predicate> predicates = List.of(builder.equal(from.get("email"), usuario.getEmail().trim().toLowerCase()),
                    builder.equal(from.get("senha"), usuario.getSenha()));

            query.where(predicates.toArray(new Predicate[0]));

            try {
                return Optional.ofNullable(em.createQuery(query).getSingleResult());

            } catch (NoResultException e) {
                e.printStackTrace();
            }
        }

        return Optional.empty();
    }
}
