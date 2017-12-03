package com.schimidt.jsf.bean;

import com.google.common.base.MoreObjects;
import com.schimidt.jsf.dao.DAO;
import com.schimidt.jsf.dao.JPAUtil;
import com.schimidt.jsf.modelo.Autor;
import com.schimidt.jsf.modelo.Livro;
import com.schimidt.jsf.service.LivroService;
import com.schimidt.jsf.validator.IsbnValidator;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;

@ManagedBean
@ViewScoped
public class LivroBean implements Serializable {
    private Livro livro = new Livro();
    private Integer autorId;

    public Livro getLivro() {
        return livro;
    }

    public void gravar() {
        System.out.println("Gravando livro " + this.livro.getTitulo());

        if (livro.getAutores().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Livro sem autor", "Livro deve ter pelo menos um Autor"));
            return;
        }

        new LivroService().salvarLivroComAutores(livro);
        this.livro = new Livro();
    }

    public void associarAutor(){
        final EntityManager em = JPAUtil.newEntityManager();
        final Autor autor = em.find(Autor.class, autorId);
        livro.adicionaAutor(autor);

        this.autorId = null;
        em.close();
    }

    public void setAutorId(Integer autorId){
        this.autorId = autorId;
    }

    public List<Autor> getAutores(){
        return livro.getAutores();
    }

    public Integer getAutorId(){
        return this.autorId;
    }

    public List<Autor> listarAutoresNaoSelecionados(){
        EntityManager em = JPAUtil.newEntityManager();
        List<Autor> autoresNaoSelecionados = new DAO<Autor>(Autor.class, em).listaTodos();

        autoresNaoSelecionados.removeAll(livro.getAutores());

        em.close();

        return autoresNaoSelecionados;
    }

    public List<Livro> getLivros(){
        final EntityManager em = JPAUtil.newEntityManager();
        final List<Livro> livros = new DAO<Livro>(Livro.class, em).listaTodos();
        em.close();

        return livros;
    }

    public void validarIsbn(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        new IsbnValidator().validate(facesContext,uiComponent,value);
    }

    public String cadastrarNovoAutor(){
        return "autor?faces-redirect=true";
    }

    @Override
    public String toString() {
//        return new org.apache.commons.lang3.builder.ToStringBuilder(this)
//                .append("livro", livro)
//                .append("autorId", autorId)
//                .toString();
        return null;
    }
}
