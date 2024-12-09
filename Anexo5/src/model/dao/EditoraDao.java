package model.dao;

import model.dao.interfaces.EditoraDaoI;
import model.entities.Editora;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EditoraDao extends DbConnection implements EditoraDaoI {

	private static Connection conn;

	public EditoraDao() {
		conn = getConnection();
	}

	@Override
	public Editora findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement(
					"SELECT * FROM editora WHERE id = ?");
			st.setInt(1, id);
			rs = DbConnection.executeQuery(st);
			if (rs.next()) {
				Editora obj = new Editora();
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
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

	@Override
	public List<Editora> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement(
					"SELECT * FROM editora ");
			rs = DbConnection.executeQuery(st);

			List<Editora> list = new ArrayList<>();

			while (rs.next()) {
				Editora obj = new Editora();
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
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
	public List<Editora> select(Editora editora) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT * FROM editora " +
							"WHERE (id = ? OR " + Optional.ofNullable(editora.getId()).isEmpty() + ")" +
							"AND (nome = ? OR " + Optional.ofNullable(editora.getNome()).isEmpty() + ")" );

			st.setInt(1, Optional.ofNullable(editora.getId()).orElse(0));
			st.setString(2, Optional.ofNullable(editora.getNome()).orElse(""));

			rs = DbConnection.executeQuery(st);

			List<Editora> list = new ArrayList<>();

			while (rs.next()) {
				Editora obj = new Editora();
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
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
	public void insert(Editora editora) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement(
					"INSERT INTO editora " +
							"(nome) " +
							"VALUES " +
							"(?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, editora.getNome());

			int rowsAffected = DbConnection.executeUpdate(st);

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					editora.setId(id);
				}
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
	public void update(Editora editora) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement(
					"UPDATE editora " +
							"SET nome = ? " +
							"WHERE id = ?");

			st.setString(1, editora.getNome());
			st.setInt(2, editora.getId());

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

			st = conn.prepareStatement(
					"DELETE FROM editora WHERE id = ?");

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
}
