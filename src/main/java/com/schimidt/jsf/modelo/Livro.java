package com.schimidt.jsf.modelo;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

@Entity
@Table(name = "livro")
public class Livro implements Serializable{

	@Id
	@GeneratedValue
	private Integer id;

	private String titulo;
	private String isbn;
	private double preco;
    @Temporal(value = TemporalType.TIMESTAMP)
	private Calendar dataLancamento;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "livro_autor", joinColumns = @JoinColumn(name = "livro_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id", referencedColumnName = "id"))
	private List<Autor> autores = new ArrayList<Autor>();

	public List<Autor> getAutores() {
		return autores;
	}

	public void adicionaAutor(Autor autor) {
		this.autores.add(autor);
	}

	public Livro() {
	    this.dataLancamento = Calendar.getInstance();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

    public Calendar getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(final Calendar dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public void remover(Autor autor){
		this.autores.remove(autor);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("titulo", titulo)
				.add("isbn", isbn)
				.add("preco", preco)
				.add("dataLancamento", dataLancamento)
				.add("autores", autores)
				.toString();
	}
}