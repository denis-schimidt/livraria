package com.schimidt.jsf.bean;

import com.schimidt.jsf.dao.DAO;
import com.schimidt.jsf.dao.JPAUtil;
import com.schimidt.jsf.infra.RedirectView;
import com.schimidt.jsf.infra.View;
import com.schimidt.jsf.modelo.Autor;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@ManagedBean
@ViewScoped
public class AutorBean implements Serializable{
    private Autor autor = new Autor();

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

    public View cadastrarLivroComAutor(){
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

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
