package com.schimidt.jsf.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Autor implements Serializable{

	@Id
	@GeneratedValue
	private Integer id;
	private String nome;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final Autor autor = (Autor) o;

		if (!id.equals(autor.id)) return false;
		return nome.equals(autor.nome);
	}

	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + nome.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return com.google.common.base.MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("nome", nome)
				.toString();
	}
}
