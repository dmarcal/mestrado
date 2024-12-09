package model.dao.interfaces;

import model.entities.Editora;

import java.util.List;

public interface EditoraDaoI {

	void insert(Editora editora);
	void update(Editora editora);
	void deleteById(Integer id);
	Editora findById(Integer id);
	List<Editora> findAll();
	List<Editora> select(Editora editora);

}
