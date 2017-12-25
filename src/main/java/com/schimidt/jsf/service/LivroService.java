package com.schimidt.jsf.service;

import com.schimidt.jsf.dao.DAO;
import com.schimidt.jsf.modelo.Autor;
import com.schimidt.jsf.modelo.Livro;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;

public class LivroService implements Serializable {
    private static final long serialVersionUID = 8004577458265842953L;

    @Inject
    private DAO<Livro> livroDao;
    @Inject
    private DAO<Autor> autorDao;

    @Transactional
    public void salvarLivroComAutores(Livro livro) {
        livro.getAutores().forEach(autorDao::atualiza);

        if (livro.getId() == null) {
            livroDao.adiciona(livro);

        } else {
            livroDao.atualiza(livro);
        }
    }
}
