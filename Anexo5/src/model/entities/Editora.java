package model.entities;

import java.io.Serializable;

public class Editora implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;

	public Editora() {
	}

	public Editora(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
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

	@Override
	public String toString() {
		return "Editora [id=" + id + ", editora=" + nome + " HashCode=" + hashCode() + "]";
	}
}
