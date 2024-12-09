package model.entities;

import java.io.Serializable;

public class LivroAutor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String papel;
	private Livro livro;
	private Autor autor;

	public LivroAutor() {
	}

	public LivroAutor(Integer id, String papel, Livro livro, Autor autor) {
		this.id = id;
		this.papel = papel;
		this.livro = livro;
		this.autor = autor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	@Override
	public String toString() {
		return "LivroAutor [id=" + id + ", papel=" + papel + ", livro=" + livro + ", autor=" + autor + " HashCode=" + hashCode() +"]";
	}
}
