package com.schimidt.jsf.bean;

import com.schimidt.jsf.dao.DAO;
import com.schimidt.jsf.dao.JPAUtil;
import com.schimidt.jsf.infra.RedirectView;
import com.schimidt.jsf.infra.View;
import com.schimidt.jsf.modelo.Autor;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;

@ManagedBean
public class AutorBean {
    private final EntityManager em = new JPAUtil().newEntityManager();
    private Autor autor = new Autor();

    public Autor getAutor() {
        return autor;
    }

    public View gravar() {
        em.getTransaction().begin();

        System.out.println("Gravando autor " + this.autor.getNome());

        new DAO<Autor>(Autor.class, em).adiciona(this.autor);
        em.getTransaction().commit();
        em.close();

        autor = new Autor();

        return new RedirectView("livro");
    }
}
