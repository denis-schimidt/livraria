package com.schimidt.jsf.bean;

import com.schimidt.jsf.dao.UsuarioDao;
import com.schimidt.jsf.modelo.Usuario;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class LoginBean implements Serializable {
    private static final long serialVersionUID = -3043371408565301236L;

    @Inject
    private Usuario usuario;
    @Inject
    private UsuarioDao usuarioDao;

    public Usuario getUsuario() {
        return usuario;
    }

    public String efetuaLogin() {
        final FacesContext context = FacesContext.getCurrentInstance();

        System.out.println("Fazendo login do usuário " + this.usuario.getEmail());

        return usuarioDao.obterUsuarioPor(usuario)
                .map(usuario -> {
                    context.getExternalContext().getSessionMap().put("usuarioLogado", usuario);
                    return "livro?faces-redirect=true";
                })
                .orElseGet(() -> {
                    context.getExternalContext().getFlash().setKeepMessages(true);
                    context.addMessage(null, new FacesMessage("Login / password inválido(s)"));

                    return "login?faces-redirect=true";
                });
    }

    public String deslogar() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("usuarioLogado");

        return "login?faces-redirect=true";
    }
}
