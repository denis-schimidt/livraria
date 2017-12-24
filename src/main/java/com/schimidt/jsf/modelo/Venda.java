package com.schimidt.jsf.modelo;

public class Venda {
    private final Livro livro;
    private final int quantidade;

    public Venda(Livro livro, int quantidade) {
        this.livro = livro;
        this.quantidade = quantidade;
    }

    public Livro getLivro() {
        return livro;
    }

    public int getQuantidade() {
        return quantidade;
    }
}
