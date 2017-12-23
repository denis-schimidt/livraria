package com.schimidt.jsf.modelo;

import com.schimidt.jsf.dao.JPAUtil;
import com.schimidt.jsf.dao.LivroDao;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class LivroDataModel extends LazyDataModel<Livro> {

    @Override
    public List<Livro> load(int first, int pageSize, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
        EntityManager em = JPAUtil.newEntityManager();
        LivroDao dao = new LivroDao(em);
        List<Livro> livros = dao.listaTodosPaginada(first, pageSize, campoOrdenacao, sentidoOrdenacao, filtros);

        int totalSize = filtros.isEmpty() ? dao.contaTodos() : dao.contaTodosFiltrado(filtros).intValue();
        setRowCount(totalSize);

        em.close();

        return livros;
    }
}
