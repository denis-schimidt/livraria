package com.schimidt.jsf.bean;

import com.schimidt.jsf.dao.DAO;
import com.schimidt.jsf.infra.Log;
import com.schimidt.jsf.infra.RedirectView;
import com.schimidt.jsf.infra.View;
import com.schimidt.jsf.modelo.Autor;
import com.schimidt.jsf.modelo.Genero;
import com.schimidt.jsf.modelo.Livro;
import com.schimidt.jsf.modelo.LivroDataModel;
import com.schimidt.jsf.service.LivroService;
import com.schimidt.jsf.validator.IsbnValidator;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Named
@ViewScoped
public class LivroBean implements Serializable {
    private static final long serialVersionUID = 8355971869549160737L;

    @Inject
    private Livro livro;
    @Inject
    private LivroDataModel livroDataModel;
    @Inject
    private DAO<Autor> autorDao;
    @Inject
    private DAO<Livro> livroDao;
    @Inject
    private Instance<LivroService> livroServiceInstance;
    @Inject
    private FacesContext context;

    private Integer autorId;
    private List<Livro> livros;

    public Livro getLivro() {
        return livro;
    }

    @Log
    public void gravar() {
        System.out.println("Gravando livro " + this.livro.getTitulo());

        if (livro.getAutores().isEmpty()) {
            context.addMessage("autor", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Livro sem autor", "Livro deve ter pelo menos um Autor"));
            return;
        }

        livroServiceInstance.get().salvarLivroComAutores(livro);
        atualizaListaLivros();
        this.livro = new Livro();
    }

    public List<Genero> getGeneros(){
        return Arrays.asList(Genero.values());
    }

    public void associarAutor() {
        Autor autor = autorDao.buscaPorId(autorId);
        livro.adicionaAutor(autor);

        this.autorId = null;
    }

    public void setAutorId(Integer autorId) {
        this.autorId = autorId;
    }

    public List<Autor> getAutores() {
        return livro.getAutores();
    }

    public Integer getAutorId() {
        return this.autorId;
    }

    public List<Autor> listarAutoresNaoSelecionados() {
        List<Autor> autoresNaoSelecionados = autorDao.listaTodos();
        autoresNaoSelecionados.removeAll(livro.getAutores());

        return autoresNaoSelecionados;
    }

    public List<Livro> getLivros() {

        if (livros == null) {
            atualizaListaLivros();
        }

        return livros;
    }

    private void atualizaListaLivros() {
        this.livros = livroDao.listaTodos();
    }


    public LivroDataModel getLivroDataModel() {
        return livroDataModel;
    }

    public void validarIsbn(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        new IsbnValidator().validate(facesContext, uiComponent, value);
    }

    public View cadastrarNovoAutor() {
        return new RedirectView("autor");
    }

    @Transactional
    public void remover(Livro livro) {
        livroDao.remove(livro);

        System.out.printf("Apagando livro (id %d) com título %s ", livro.getId(), livro.getTitulo());

    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public void removerAssociacaoCom(Autor autor) {
        this.livro.remover(autor);
    }

    @Deprecated
    public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) {

        if (StringUtils.isBlank((String) filtroDigitado)) {
            return true;
        }

        if (valorColuna == null) {
            return false;
        }

        try {
            Double precoDigitado = Double.valueOf(filtroDigitado.toString().trim());
            Double precoColuna = (Double) valorColuna;

            return precoColuna.compareTo(precoDigitado) < 0;

        } catch (NumberFormatException e) {
            return false;
        }
    }
}
