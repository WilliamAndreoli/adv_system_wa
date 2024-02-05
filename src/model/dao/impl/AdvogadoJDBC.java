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
import model.dao.AdvogadoDao;
import model.entities.Advogado;

public class AdvogadoJDBC implements AdvogadoDao {

	private Connection conn;
	
	public AdvogadoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Advogado findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				    "SELECT advogado.* "
				    + "FROM advogado "
				    + "WHERE advogado.id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Advogado obj = instantiateAdvogado(rs);
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
	
	private Advogado instantiateAdvogado(ResultSet rs) throws SQLException {
		Advogado advogado = new Advogado();
		advogado.setId(rs.getInt("id"));
		advogado.setNome(rs.getString("nome"));
		advogado.setNumero_Da_Ordem(rs.getString("numero_Da_Ordem"));
		return advogado;
	}

	@Override
	public void deletebyId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(	
					"DELETE FROM advogado "
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
	public List<Advogado> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				    "SELECT advogado.*"
				    + "FROM advogado "
				    + "ORDER BY nome");

			rs = st.executeQuery();
			
			List<Advogado> list = new ArrayList<>();
			
			while (rs.next()) {
				Advogado obj = instantiateAdvogado(rs);
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
	public void insert(Advogado obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement(
					"INSERT INTO advogado"
					+ "(nome, numero_Da_Ordem)"
					+ "VALUES"
					+ "(?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getNumero_Da_Ordem());
			
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
	public void update(Advogado obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE advogado "
					+ "SET nome = ?, numero_Da_Ordem = ? "
					+ "WHERE Id = ?");
			
			st.setString(1, obj.getNome());
			st.setString(2, obj.getNumero_Da_Ordem());
			st.setInt(3, obj.getId());
			
			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}


}
