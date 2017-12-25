package com.schimidt.jsf.modelo;

import com.schimidt.jsf.dao.LivroDao;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class LivroDataModel extends LazyDataModel<Livro> {

    @Inject
    private LivroDao dao;

    @Override
    public List<Livro> load(int first, int pageSize, String campoOrdenacao, SortOrder sentidoOrdenacao, Map<String, Object> filtros) {
        List<Livro> livros = dao.listaTodosPaginada(first, pageSize, campoOrdenacao, sentidoOrdenacao, filtros);

        int totalSize = filtros.isEmpty() ? dao.contaTodos() : dao.contaTodosFiltrado(filtros).intValue();
        setRowCount(totalSize);

        return livros;
    }
}
