package model.dao;

import model.dao.interfaces.AutorDaoI;
import model.entities.Autor;

import java.sql.*;
import java.util.*;

public class AutorDao extends DbConnection implements AutorDaoI {

	private static Connection conn;

	public AutorDao() {
		conn = getConnection();
	}

	@Override
	public Autor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement(
					"SELECT * FROM autor WHERE id = ?");
			st.setInt(1, id);
			rs = DbConnection.executeQuery(st);
			if (rs.next()) {
				Autor obj = new Autor();
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
				obj.setDtNasc(rs.getDate("dt_nasc"));
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
	public List<Autor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement(
					"SELECT * FROM autor ");
			rs = DbConnection.executeQuery(st);

			List<Autor> list = new ArrayList<>();

			while (rs.next()) {
				Autor obj = new Autor();
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
				obj.setDtNasc(rs.getDate("dt_nasc"));
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
	public List<Autor> select(Autor autor) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT * FROM autor " +
							"WHERE (id = ? OR " + Optional.ofNullable(autor.getId()).isEmpty() + ")" +
							"AND (nome = ? OR " + Optional.ofNullable(autor.getNome()).isEmpty() + ")" +
							"AND (dt_nasc = ? OR " + Optional.ofNullable(autor.getDtNasc()).isEmpty() + ")" );

			st.setInt(1, Optional.ofNullable(autor.getId()).orElse(0));
			st.setString(2, Optional.ofNullable(autor.getNome()).orElse(""));
			st.setDate(3, Optional.ofNullable(autor.getDtNasc()).map(d -> new java.sql.Date(d.getTime())).orElse(null));

			rs = DbConnection.executeQuery(st);

			List<Autor> list = new ArrayList<>();

			while (rs.next()) {
				Autor obj = new Autor();
				obj.setId(rs.getInt("id"));
				obj.setNome(rs.getString("nome"));
				obj.setDtNasc(rs.getDate("dt_nasc"));
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
	public void insert(Autor autor) {
		PreparedStatement st = null;

		try {

			st = conn.prepareStatement(
					"INSERT INTO autor " +
							"(nome) " +
							"(dt_nasc) " +
							"VALUES " +
							"(?), (?)", +
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, autor.getNome());
			st.setDate(2, new java.sql.Date(autor.getDtNasc().getTime()));

			int rowsAffected = DbConnection.executeUpdate(st);

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					autor.setId(id);
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
	public void update(Autor autor) {
		PreparedStatement st = null;
		try {

			st = conn.prepareStatement(
					"UPDATE autor " +
							"SET nome = ?, dt_nasc = ? " +
							"WHERE id = ?");

			st.setString(1, autor.getNome());
			st.setDate(2, new java.sql.Date(autor.getDtNasc().getTime()));
			st.setInt(3, autor.getId());

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
					"DELETE FROM autor WHERE id = ?");

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
