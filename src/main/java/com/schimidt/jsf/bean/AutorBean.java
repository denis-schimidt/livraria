package com.schimidt.jsf.bean;

import com.schimidt.jsf.dao.DAO;
import com.schimidt.jsf.dao.JPAUtil;
import com.schimidt.jsf.infra.RedirectView;
import com.schimidt.jsf.infra.View;
import com.schimidt.jsf.modelo.Autor;
import com.schimidt.jsf.validator.EmailValidator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AutorBean implements Serializable {
    private Autor autor = new Autor();
    private Integer autorId;

    public Autor getAutor() {
        return autor;
    }

    public void gravar() {
        EntityManager em = new JPAUtil().newEntityManager();
        em.getTransaction().begin();

        System.out.println("Gravando autor " + this.autor.getNome());

        DAO<Autor> autorDAO = new DAO<>(Autor.class, em);

        if (autor.getId() == null) {
            autorDAO.adiciona(this.autor);

        } else {
            autorDAO.atualiza(this.autor);
        }

        em.getTransaction().commit();
        em.close();

        autor = new Autor();
    }

    public View cadastrarLivroComAutor() {
        return new RedirectView("livro");
    }

    public List<Autor> listarTodos() {
        EntityManager em = new JPAUtil().newEntityManager();
        List<Autor> autores = new DAO<Autor>(Autor.class, em).listaTodos();
        em.close();

        return autores;
    }

    public void remover(Autor autor) {
        EntityManager em = new JPAUtil().newEntityManager();

        try {
            em.getTransaction().begin();
            new DAO<Autor>(Autor.class, em).remove(autor);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();

        } finally {
            em.close();
        }
    }

    public void pesquisarAutor() {

        if (autorId != null) {
            EntityManager em = new JPAUtil().newEntityManager();
            this.autor = new DAO<Autor>(Autor.class, em).buscaPorId(autorId);
            em.close();
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
