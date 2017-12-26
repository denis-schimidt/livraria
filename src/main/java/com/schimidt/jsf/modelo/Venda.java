package com.schimidt.jsf.modelo;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "venda")
public class Venda implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Livro livro;

    private int quantidade;

    private LocalDate dataVenda;

    // Hibernate
    Venda() {}

    public Venda(Livro livro, int quantidade, LocalDate dataVenda) {
        this.livro = livro;
        this.quantidade = quantidade;
        this.dataVenda = dataVenda;
    }

    public Venda(Integer id){
        this.id = id;
    }

    public Livro getLivro() {
        return livro;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }
}
