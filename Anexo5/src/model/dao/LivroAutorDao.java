package model.dao;

import model.dao.interfaces.LivroAutorDaoI;
import model.entities.LivroAutor;
import model.entities.Livro;
import model.entities.Editora;
import model.entities.Autor;

import java.sql.*;
import java.util.*;

public class LivroAutorDao extends DbConnection implements LivroAutorDaoI {

	private Connection conn;

	public LivroAutorDao() {
		conn = getConnection();
	}

	@Override
	public void insert(LivroAutor livroautor) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement(
					"INSERT INTO livro_autor "
							+ "(livro_id, autor_id, papel) "
							+ "VALUES "
							+ "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, livroautor.getLivro().getId());
			st.setInt(2, livroautor.getAutor().getId());
			st.setString(3, livroautor.getPapel());

			int rowsAffected = DbConnection.executeUpdate(st);

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					livroautor.setId(id);
				}
				st.close();
			}
			else {
				DbException("Unexpected error! No rows affected!");
			}
		}
		catch (SQLException e) {
			DbException(e.getMessage());
		}
		finally {
			st=null;

		}
	}

	@Override
	public void update(LivroAutor livroautor) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement(
					"UPDATE livro_autor "
							+ "SET livro_id = ?, autor_id = ?, papel = ? "
							+ "WHERE id = ?");

			st.setInt(1, livroautor.getLivro().getId());
			st.setInt(2, livroautor.getAutor().getId());
			st.setString(3, livroautor.getPapel());
			st.setInt(5, livroautor.getId());

			DbConnection.executeUpdate(st);
		}
		catch (SQLException e) {
			DbException(e.getMessage());
		}
		finally {
			st=null;

		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement("DELETE FROM livro_autor WHERE id = ?");

			st.setInt(1, id);

			DbConnection.executeUpdate(st);
		}
		catch (SQLException e) {
			DbException(e.getMessage());
		}
		finally {
			st=null;

		}
	}

	@Override
	public LivroAutor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(
					"SELECT livro_autor.id, livro_autor.papel, livro.id as livroId, livro.titulo as livroTitulo, "
							+ "livro.ano_pub as livroAnoPub, livro.preco as livroPreco, editora.id as editoraId, "
							+ "editora.nome as editoraNome, autor.id as autorId, autor.nome as autorNome, "
							+ "autor.dt_nasc as autorDtNasc "
							+ "FROM livro_autor, livro, autor, editora "
							+ "WHERE livro_autor.livro_id = livro.id "
							+ "AND livro.editora_id = editora.id "
							+ "AND livro_autor.autor_id = autor.id "
							+ "WHERE livro_autor.id = ?");

			st.setInt(1, id);
			rs = DbConnection.executeQuery(st);
			if (rs.next()) {
				Editora editora = loadEditora(rs);
				Livro livro = loadLivro(rs, editora);
				Autor autor = loadAutor(rs);
				LivroAutor obj  = loadLivroAutor(rs, livro, autor);
				return obj;
			}
		}
		catch (SQLException e) {
			DbException(e.getMessage());
		}
		finally {
			st=null;
			rs=null;
		}
		return null;

	}

	private Livro loadLivro(ResultSet rs, Editora editora) throws SQLException {
		Livro livro = new Livro();
		livro.setId(rs.getInt("livroId"));
		livro.setTitulo(rs.getString("livroTitulo"));
		livro.setAnoPub(rs.getInt("livroAnoPub"));
		livro.setPreco(rs.getDouble("livroPreco"));
		livro.setEditora(editora);
		return livro;
	}

	private Editora loadEditora(ResultSet rs) throws SQLException {
		Editora editora = new Editora();
		editora.setId(rs.getInt("editoraId"));
		editora.setNome(rs.getString("editoraNome"));
		return editora;
	}
	private Autor loadAutor(ResultSet rs) throws SQLException {
		Autor autor = new Autor();
		autor.setId(rs.getInt("autorId"));
		autor.setNome(rs.getString("autorNome"));
		autor.setDtNasc(rs.getDate("autorDtNasc"));
		return autor;
	}

	private LivroAutor loadLivroAutor(ResultSet rs, Livro livro, Autor autor) throws SQLException {
		LivroAutor livroautor = new LivroAutor();
		livroautor.setId(rs.getInt("id"));
		livroautor.setPapel(rs.getString("papel"));
		livroautor.setLivro(livro);
		livroautor.setAutor(autor);
		return livroautor;
	}

	@Override
	public List<LivroAutor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement("SELECT livro_autor.id, livro_autor.papel, livro.id as livroId, livro.titulo as livroTitulo, "
					+ "livro.ano_pub as livroAnoPub, livro.preco as livroPreco, editora.id as editoraId, "
					+ "editora.nome as editoraNome, autor.id as autorId, autor.nome as autorNome, "
					+ "autor.dt_nasc as autorDtNasc "
					+ "FROM livro_autor, livro, autor, editora "
					+ "WHERE livro_autor.livro_id = livro.id "
					+ "AND livro.editora_id = editora.id "
					+ "AND livro_autor.autor_id = autor.id "
					+ "ORDER BY livro_autor.id");

			rs = DbConnection.executeQuery(st);

			List<LivroAutor> list = new ArrayList<>();
			Map<Integer, Editora> mapEditora = new HashMap<>();
			Map<Integer, Autor> mapAutor = new HashMap<>();
			Map<Integer, Livro> mapLivro = new HashMap<>();

			while (rs.next()) {

				Editora editora = mapEditora.get(rs.getInt("editoraId"));

				if (editora == null) {
					editora = loadEditora(rs);
					mapEditora.put(rs.getInt("editoraId"), editora);
				}

				Livro livro = mapLivro.get(rs.getInt("editoraId"));

				if (livro == null) {
					livro = loadLivro(rs, editora);
					mapLivro.put(rs.getInt("editoraId"), livro);
				}
				Autor autor = mapAutor.get(rs.getInt("autorId"));

				if (autor == null) {
					autor = loadAutor(rs);
					mapAutor.put(rs.getInt("autorId"), autor);
				}

				LivroAutor obj = loadLivroAutor(rs, livro, autor);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			DbException(e.getMessage());
		}
		finally {
			st=null;
			rs=null;

		}
		return null;
	}

	@Override
	public List<LivroAutor> listAll(LivroAutor livroautor) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT livro_autor.id, livro_autor.papel, livro.id as livroId, livro.titulo as livroTitulo, "
							+ "livro.ano_pub as livroAnoPub, livro.preco as livroPreco, editora.id as editoraId, "
							+ "editora.nome as editoraNome, autor.id as autorId, autor.nome as autorNome, "
							+ "autor.dt_nasc as autorDtNasc "
							+ "FROM livro_autor, livro, autor, editora "
							+ "WHERE livro_autor.livro_id = livro.Id "
							+ "AND livro.editora_id = editora.id "
							+ "AND livro_autor.autor_id = autor.id "
							+ "AND (livro_autor.id = ? OR " + Optional.ofNullable(livroautor.getId()).isEmpty() + ") "
							+ "AND (livro_autor.papel LIKE ? OR " + Optional.ofNullable(livroautor.getPapel()).isEmpty() + ") "
							+ "ORDER BY livro.titulo");

			st.setInt(1, Optional.ofNullable(livroautor.getId()).orElse(0));
			st.setString(2, Optional.ofNullable(livroautor.getPapel()).map(p -> "%" + p + "%").orElse(""));
			rs = DbConnection.executeQuery(st);

			List<LivroAutor> list = new ArrayList<>();
			Map<Integer, Editora> mapEditora = new HashMap<>();
			Map<Integer, Autor> mapAutor = new HashMap<>();
			Map<Integer, Livro> mapLivro = new HashMap<>();

			while (rs.next()) {

				Editora editora = mapEditora.get(rs.getInt("editoraId"));

				if (editora == null) {
					editora = loadEditora(rs);
					mapEditora.put(rs.getInt("editoraId"), editora);
				}

				Livro livro = mapLivro.get(rs.getInt("editoraId"));

				if (livro == null) {
					livro = loadLivro(rs, editora);
					mapLivro.put(rs.getInt("editoraId"), livro);
				}
				Autor autor = mapAutor.get(rs.getInt("autorId"));

				if (autor == null) {
					autor = loadAutor(rs);
					mapAutor.put(rs.getInt("autorId"), autor);
				}

				LivroAutor obj = loadLivroAutor(rs, livro, autor);
				list.add(obj);
			}
			return list;
		}
		catch (SQLException e) {
			DbException(e.getMessage());
		}
		finally {
			st=null;
			rs=null;
		}
		return null;
	}
}
