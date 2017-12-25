package com.schimidt.jsf.bean;

import com.schimidt.jsf.dao.DAO;
import com.schimidt.jsf.infra.RedirectView;
import com.schimidt.jsf.infra.View;
import com.schimidt.jsf.modelo.Autor;
import com.schimidt.jsf.validator.EmailValidator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AutorBean implements Serializable {
    private static final long serialVersionUID = 2098022258496123269L;

    @Inject
    private Autor autor;

    @Inject
    private DAO<Autor> dao;

    private Integer autorId;

    public Autor getAutor() {
        return autor;
    }

    @Transactional
    public void gravar() {
        System.out.println("Gravando autor " + this.autor.getNome());

        if (autor.getId() == null) {
            this.dao.adiciona(this.autor);

        } else {
            this.dao.atualiza(this.autor);
        }

        autor = new Autor();
    }

    public View cadastrarLivroComAutor() {
        return new RedirectView("livro");
    }

    public List<Autor> listarTodos() {
        return dao.listaTodos();
    }

    @Transactional
    public void remover(Autor autor) {
        dao.remove(autor);
    }

    public void pesquisarAutor() {

        if (autorId != null) {
            this.autor = dao.buscaPorId(autorId);
        }
    }

    public void setAutorId(Integer autorId) {
        this.autorId = autorId;
    }

    public Integer getAutorId() {
        return autorId;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void validateEmail(FacesContext context, UIComponent uiComponent, Object value) {
        EmailValidator.validateEmailUsing(uiComponent, value);
    }
}
