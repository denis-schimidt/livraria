package com.schimidt.jsf.dao;

import com.schimidt.jsf.modelo.Autor;
import com.schimidt.jsf.modelo.Genero;
import com.schimidt.jsf.modelo.Livro;
import com.schimidt.jsf.modelo.Usuario;
import org.jboss.weld.SimpleCDI;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PopulaBanco {

	public static void main(String[] args) {
		SimpleCDI cdi = new SimpleCDI();

		EntityManager em = cdi.getBeanManager().instance().select(EntityManager.class).get();

		em.getTransaction().begin();

		Autor assis = geraAutor("Machado de Assis", "machado.assis@gmail.com");
		em.persist(assis);

		Autor amado = geraAutor("Jorge Amado", "jorge.machado@gmail.com");
		em.persist(amado);

		Autor coelho = geraAutor("Paulo Coelho", "paulo.coelho@gmail.com");
		em.persist(coelho);

		Livro casmurro = geraLivro("978-8-52-504464-8", "Dom Casmurro",
				"10/01/1899",  Genero.ROMANCE, 24.90, assis);
		Livro memorias = geraLivro("978-8-50-815415-9",
				"Memorias Postumas de Bras Cubas", "01/01/1881",  Genero.ROMANCE,19.90, assis);
		Livro quincas = geraLivro("978-8-50-804084-1", "Quincas Borba",
				"01/01/1891",  Genero.DRAMA, 16.90, assis);

		em.persist(casmurro);
		em.persist(memorias);
		em.persist(quincas);

		Livro alquemista = geraLivro("978-8-57-542758-3", "O Alquimista",
				"01/01/1988",  Genero.ESOTERICO,19.90, coelho);
		Livro brida = geraLivro("978-8-50-567258-7", "Brida", "01/01/1990",
				Genero.ESOTERICO,12.90, coelho);
		Livro valkirias = geraLivro("978-8-52-812458-8", "As Valkirias",
				"01/01/1992", Genero.ESOTERICO,29.90, coelho);
		Livro maao = geraLivro("978-8-51-892238-9", "O Diario de um Mago",
				"01/01/1987", Genero.ESOTERICO,9.90, coelho);

		em.persist(alquemista);
		em.persist(brida);
		em.persist(valkirias);
		em.persist(maao);

		Livro capitaes = geraLivro("978-8-50-831169-1", "Capitaes da Areia",
				"01/01/1937", Genero.ROMANCE, 6.90, amado);
		Livro flor = geraLivro("978-8-53-592569-9",
				"Dona Flor e Seus Dois Maridos", "01/01/1966", Genero.ROMANCE,18.90, amado);

		Usuario usuario = new Usuario("fulano@elo7.com", "123");
		em.persist(usuario);
		em.persist(capitaes);
		em.persist(flor);

		em.getTransaction().commit();
		em.close();
	}

	private static Autor geraAutor(String nome, String email) {
		Autor autor = new Autor();
		autor.setNome(nome);
		autor.setEmail(email);
		return autor;
	}

	private static Livro geraLivro(String isbn, String titulo, String data, Genero genero,
			double preco, Autor autor) {
		Livro livro = new Livro();
		livro.setIsbn(isbn);
		livro.setTitulo(titulo);
		livro.setDataLancamento(parseData(data));
		livro.setPreco(preco);
		livro.setGenero(genero);
		livro.adicionaAutor(autor);
		return livro;
	}

	private static Calendar parseData(String data) {
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(data);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
