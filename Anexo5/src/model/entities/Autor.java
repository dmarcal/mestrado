package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Autor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	private Date dt_nasc;
	private List<LivroAutor> livroAutores; // Relacionamento com LivroAutor

	public Autor() {
	}

	public Autor(Integer id, String nome, Date dtNasc) {
		this.id = id;
		this.nome = nome;
		this.dt_nasc = dtNasc;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDtNasc() {
		return dt_nasc;
	}

	public void setDtNasc(Date dtNasc) {
		this.dt_nasc = dtNasc;
	}

	public List<LivroAutor> getLivroAutores() {
		return livroAutores;
	}

	public void setLivroAutores(List<LivroAutor> livroAutores) {
		this.livroAutores = livroAutores;
	}

	@Override
	public String toString() {
		return "Autor [id=" + id + ", nome=" + nome + ", dtNasc=" + dt_nasc + " HashCode=" + hashCode() +"]";
	}
}
