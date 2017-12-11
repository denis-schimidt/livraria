package com.schimidt.jsf.bean;

import com.schimidt.jsf.dao.JPAUtil;
import com.schimidt.jsf.dao.UsuarioDao;
import com.schimidt.jsf.modelo.Usuario;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable {
    private Usuario usuario = new Usuario();

    public Usuario getUsuario() {
        return usuario;
    }

    public String efetuaLogin() {
        final FacesContext context = FacesContext.getCurrentInstance();

        System.out.println("Fazendo login do usuário " + this.usuario.getEmail());

        return new UsuarioDao(JPAUtil.newEntityManager())
                .obterUsuarioPor(usuario)
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
