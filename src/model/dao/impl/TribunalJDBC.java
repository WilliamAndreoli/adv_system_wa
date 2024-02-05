package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.TribunalDao;
import model.entities.Tribunal;

public class TribunalJDBC implements TribunalDao {

	private Connection conn;
	
	public TribunalJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Tribunal findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				    "SELECT tribunal.* "
				    + "FROM tribunal "
				    + "WHERE tribunal.id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Tribunal obj = instantiateTribunal(rs);
				return obj;
			}
			return null;
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Tribunal instantiateTribunal(ResultSet rs) throws SQLException {
		Tribunal tribunal = new Tribunal();
		tribunal.setId(rs.getInt("id"));
		tribunal.setNome(rs.getString("nome"));
		tribunal.setEndereco(rs.getString("endereco"));
		tribunal.setJurisdicao(rs.getString("jurisdicao"));
		return tribunal;
	}

	@Override
	public void deletebyId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(	
					"DELETE FROM tribunal "
					+ "WHERE Id = ?");
			
			st.setInt(1, id);
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public List<Tribunal> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				    "SELECT tribunal.*"
				    + "FROM tribunal "
				    + "ORDER BY nome");

			rs = st.executeQuery();
			
			List<Tribunal> list = new ArrayList<>();
			
			while (rs.next()) {
				Tribunal obj = instantiateTribunal(rs);
				list.add(obj);			
			}
			return list;
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public void insert(Tribunal obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO tribunal"
					+ "(nome, endereco, jurisdicao)"
					+ "VALUES"
					+ "(?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEndereco());
			st.setString(3, obj.getJurisdicao());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					
					obj.setId(id);
					
					DB.closeResultSet(rs);
				}
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Tribunal obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE tribunal "
					+ "SET nome = ?, endereco = ?, jurisdicao = ? "
					+ "WHERE Id = ?");
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getEndereco());
			st.setString(3, obj.getJurisdicao());
			st.setInt(4, obj.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}


}
