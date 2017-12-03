package com.schimidt.jsf.service;

import com.schimidt.jsf.dao.DAO;
import com.schimidt.jsf.dao.JPAUtil;
import com.schimidt.jsf.modelo.Autor;
import com.schimidt.jsf.modelo.Livro;
import javax.persistence.EntityManager;

public class LivroService {
    private final EntityManager em = JPAUtil.newEntityManager();
    private final DAO<Livro> livroDao = new DAO<>(Livro.class, em);
    private final DAO<Autor> autorDao = new DAO<>(Autor.class, em);

    public void salvarLivroComAutores(Livro livro) {

        try {
            em.getTransaction().begin();
            livro.getAutores().forEach(autorDao::atualiza);
            livroDao.adiciona(livro);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            em.close();
        }
    }
}
