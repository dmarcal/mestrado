package model.dao.interfaces;

import model.dao.EditoraDao;
import model.dao.AutorDao;
import model.dao.LivroDao;
import model.dao.LivroAutorDao;
public class DaoFactory {
	public static EditoraDaoI createEditoraDao() {
		return new EditoraDao();
	}
	public static AutorDaoI createAutorDao() {
		return new AutorDao();
	}
	public static LivroDaoI createLivroDao() {
		return new LivroDao();
	}
	public static LivroAutorDaoI createLivroAutorDao() {
		return new LivroAutorDao();
	}
}
