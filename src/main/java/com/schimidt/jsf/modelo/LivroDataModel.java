package com.schimidt.jsf.modelo;

import com.schimidt.jsf.dao.DAO;
import com.schimidt.jsf.dao.JPAUtil;
import com.schimidt.jsf.dao.LivroDao;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class LivroDataModel extends LazyDataModel<Livro> {

    public LivroDataModel() {
        final EntityManager em = JPAUtil.newEntityManager();
        DAO<Livro> dao = new DAO<>(Livro.class, em);
        super.setRowCount(dao.contaTodos());
        em.close();
    }

    @Override
    public List<Livro> load(int inicio, int quantidade, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
        final EntityManager em = JPAUtil.newEntityManager();
        LivroDao dao = new LivroDao(em);
        List<Livro> livros = dao.listaTodosPaginada(inicio, quantidade, campoOrdenacao, sentidoOrdenacao, filtros);
        em.close();
        return livros;
    }
}
