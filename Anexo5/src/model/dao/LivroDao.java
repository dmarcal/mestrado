package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Statement;

import model.dao.interfaces.LivroDaoI;
import model.entities.Editora;
import model.entities.Livro;

public class LivroDao extends DbConnection implements LivroDaoI {

	private Connection conn;

	public LivroDao() {
		conn = getConnection();
	}

	@Override
	public void insert(Livro livro) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement(
					"INSERT INTO livro "
							+ "(titulo, ano_pub, preco, editora_id) "
							+ "VALUES "
							+ "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, livro.getTitulo());
			st.setInt(2, livro.getAnoPub());
			st.setDouble(3, livro.getPreco());
			st.setInt(4, livro.getEditora().getId());

			int rowsAffected = DbConnection.executeUpdate(st);

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					livro.setId(id);
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
	public void update(Livro livro) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement(
					"UPDATE livro "
							+ "SET titulo = ?, ano_pub = ?, preco = ?, editora_id = ? "
							+ "WHERE id = ?");

			st.setString(1, livro.getTitulo());
			st.setInt(2, livro.getAnoPub());
			st.setDouble(3, livro.getPreco());
			st.setInt(4, livro.getEditora().getId());
			st.setInt(5, livro.getId());

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

			st = conn.prepareStatement("DELETE FROM livro WHERE id = ?");

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
	public Livro findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(
					"SELECT livro.*,editora.id as editoraId, editora.nome as editoraNome "
							+ "FROM livro INNER JOIN editora "
							+ "ON livro.editora_id = editora.Id "
							+ "WHERE livro.id = ?");

			st.setInt(1, id);
			rs = DbConnection.executeQuery(st);
			if (rs.next()) {
				Editora editora = loadeditora(rs);
				Livro obj = loadlivro(rs, editora);
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

	private Livro loadlivro(ResultSet rs, Editora editora) throws SQLException {
		Livro livro = new Livro();
		livro.setId(rs.getInt("id"));
		livro.setTitulo(rs.getString("titulo"));
		livro.setAnoPub(rs.getInt("ano_pub"));
		livro.setPreco(rs.getDouble("preco"));
		livro.setEditora(editora);
		return livro;
	}

	private Editora loadeditora(ResultSet rs) throws SQLException {
		Editora editora = new Editora();
		editora.setId(rs.getInt("editoraId"));
		editora.setNome(rs.getString("editoraNome"));
		return editora;
	}

	@Override
	public List<Livro> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {

			st = conn.prepareStatement(
					"SELECT livro.*,editora.id as editoraId,editora.nome as editoraNome "
							+ "FROM livro INNER JOIN editora "
							+ "ON livro.editora_id = editora.id" );

			rs = DbConnection.executeQuery(st);

			List<Livro> list = new ArrayList<>();
			Map<Integer, Editora> map = new HashMap<>();

			while (rs.next()) {

				Editora editora = map.get(rs.getInt("editoraId"));

				if (editora == null) {
					editora = loadeditora(rs);
					map.put(rs.getInt("editoraId"), editora);
				}

				Livro obj = loadlivro(rs, editora);
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
	public List<Livro> listAll(Livro livro) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT livro.*,editora.id as editoraId,editora.nome as editoraNome "
							+ "FROM livro, editora "
							+ "WHERE livro.editora_id = editora.id "
							+ "AND (livro.id = ? OR " + Optional.ofNullable(livro.getId()).isEmpty() + ") "
							+ "AND (livro.titulo LIKE ? OR " + Optional.ofNullable(livro.getTitulo()).isEmpty() + ") "
							+ "AND (livro.preco = ? OR " + Optional.ofNullable(livro.getPreco()).isEmpty() + ") "
							+ "AND (editora.id = ? OR " + Optional.ofNullable(livro.getEditora()).map(Editora::getId).isEmpty() + ") "
							+ "AND (editora.nome LIKE ? OR " + Optional.ofNullable(livro.getEditora()).map(Editora::getNome).isEmpty() + ") "
							+ "ORDER BY livro.id");

			st.setInt(1, Optional.ofNullable(livro.getId()).orElse(0));
			st.setString(2, Optional.ofNullable(livro.getTitulo()).map(t -> "%" + t + "%").orElse(""));
			st.setDouble(3, Optional.ofNullable(livro.getPreco()).orElse(0.0));
			st.setInt(4, Optional.ofNullable(livro.getEditora()).map(Editora::getId).orElse(0));
			st.setString(5, Optional.ofNullable(livro.getEditora()).map(Editora::getNome).map(n -> "%" + n + "%").orElse(""));

			rs = DbConnection.executeQuery(st);

			List<Livro> list = new ArrayList<>();
			Map<Integer, Editora> map = new HashMap<>();

			while (rs.next()) {

				Editora editora = map.get(rs.getInt("editora_id"));

				if (editora == null) {
					editora = loadeditora(rs);
					map.put(rs.getInt("editora_id"), editora);
				}

				Livro obj = loadlivro(rs, editora);
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
