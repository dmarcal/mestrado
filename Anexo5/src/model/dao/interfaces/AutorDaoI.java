package model.dao.interfaces;

import model.entities.Autor;

import java.util.List;

public interface AutorDaoI {

	void insert(Autor autor);
	void update(Autor autor);
	void deleteById(Integer id);
	Autor findById(Integer id);
	List<Autor> findAll();
	List<Autor> select(Autor autor);

}