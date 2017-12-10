package com.schimidt.jsf.bean;

import com.schimidt.jsf.dao.JPAUtil;
import com.schimidt.jsf.dao.UsuarioDao;
import com.schimidt.jsf.modelo.Usuario;

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
        System.out.println("Fazendo login do usuário " + this.usuario.getEmail());

        return new UsuarioDao(JPAUtil.newEntityManager())
           .obterUsuarioPor(usuario)
           .map(usuario->{
               FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuarioLogado", usuario);
               return "livro?faces-redirect=true";
           })
           .orElse(null);
    }
}
