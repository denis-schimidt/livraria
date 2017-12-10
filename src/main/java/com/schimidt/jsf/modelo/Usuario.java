package com.schimidt.jsf.modelo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(name ="email_contraint", columnNames = {"email"})})
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String senha;

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario() {}

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
