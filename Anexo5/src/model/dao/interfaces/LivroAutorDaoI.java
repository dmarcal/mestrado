package model.dao.interfaces;

import model.entities.LivroAutor;

import java.util.List;

public interface LivroAutorDaoI {

	void insert(LivroAutor livroautor);
	void update(LivroAutor livroautor);
	void deleteById (Integer id);
	LivroAutor findById(Integer id);
	List<LivroAutor> findAll();
	List<LivroAutor> listAll(LivroAutor livroautor);

}
