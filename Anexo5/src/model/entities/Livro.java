package model.entities;

import java.io.Serializable;
import java.util.List;

public class Livro implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String titulo;
	private Integer ano_pub;
	private Double preco;
	private Editora editora;
	private List<LivroAutor> livroAutores; // Relacionamento com LivroAutor

	public Livro() {
	}

	public Livro(Integer id, String titulo, Integer anoPub, Double preco, Editora editora) {
		this.id = id;
		this.titulo = titulo;
		this.ano_pub = anoPub;
		this.preco = preco;
		this.editora = editora;
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

	public Integer getAnoPub() {
		return ano_pub;
	}

	public void setAnoPub(Integer anoPub) {
		this.ano_pub = anoPub;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public List<LivroAutor> getLivroAutores() {
		return livroAutores;
	}

	public void setLivroAutores(List<LivroAutor> livroAutores) {
		this.livroAutores = livroAutores;
	}

	@Override
	public String toString() {
		return "Livro [id=" + id + ", titulo=" + titulo + ", anoPub=" + ano_pub + ", preco=" + preco + ", editora=" + editora + " HashCode=" + hashCode() +"]";
	}
}
