package model.dao.interfaces;

import model.entities.Livro;

import java.util.List;

public interface LivroDaoI {

	void insert(Livro livro);
	void update(Livro livro);
	void deleteById(Integer id);
	Livro findById(Integer id);
	List<Livro> findAll();
	List<Livro> listAll(Livro livro);

}
