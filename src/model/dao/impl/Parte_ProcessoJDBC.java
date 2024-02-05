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
import model.dao.Parte_ProcessoDao;
import model.entities.Parte_Processo;
import model.entities.Parte_Processo;
import model.entities.Parte_Processo;
import model.entities.Parte_Processo;

public class Parte_ProcessoJDBC implements Parte_ProcessoDao {

	private Connection conn;
	
	public Parte_ProcessoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Parte_Processo findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				    "SELECT parte_processo.* "
				    + "FROM parte_processo "
				    + "WHERE parte_processo.id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Parte_Processo obj = instantiateParte_Processo(rs);
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
	
	private Parte_Processo instantiateParte_Processo(ResultSet rs) throws SQLException {
		Parte_Processo parte_Processo = new Parte_Processo();
		parte_Processo.setId(rs.getInt("id"));
		parte_Processo.setNome(rs.getString("nome"));
		return parte_Processo;
	}

	@Override
	public void deletebyId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(	
					"DELETE FROM parte_processo "
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
	public List<Parte_Processo> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				    "SELECT parte_processo.*"
				    + "FROM parte_processo "
				    + "ORDER BY nome");

			rs = st.executeQuery();
			
			List<Parte_Processo> list = new ArrayList<>();
			
			while (rs.next()) {
				Parte_Processo obj = instantiateParte_Processo(rs);
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
	public void insert(Parte_Processo obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO parte_processo"
					+ "(nome)"
					+ "VALUES"
					+ "(?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			
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
	public void update(Parte_Processo obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE parte_processo "
					+ "SET nome = ? "
					+ "WHERE Id = ?");
			
			st.setString(1, obj.getNome());
			st.setInt(2, obj.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

}
