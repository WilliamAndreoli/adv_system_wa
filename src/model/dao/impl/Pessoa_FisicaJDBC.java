package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.Pessoa_FisicaDao;
import model.entities.Cliente;
import model.entities.Pessoa_Fisica;

public class Pessoa_FisicaJDBC implements Pessoa_FisicaDao {

	private Connection conn;
	
	public Pessoa_FisicaJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Pessoa_Fisica obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Pessoa_Fisica obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletebyId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pessoa_Fisica findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				    "SELECT pessoa_fisica.*, cliente.nome as Nome, "
				    + "cliente.email as Email, cliente.telefone as Telefone, cliente.endereco as Endereco "
				    + "FROM pessoa_fisica INNER JOIN cliente "
				    + "ON pessoa_fisica.cliente_Id = cliente.id "
				    + "WHERE pessoa_fisica.id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Cliente cliente = instantiateCliente(rs);
				
				Pessoa_Fisica obj = instantiatePessoa_Fisica(rs, cliente);
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

	private Pessoa_Fisica instantiatePessoa_Fisica(ResultSet rs, Cliente cliente) throws SQLException {
		Pessoa_Fisica obj = new Pessoa_Fisica();
		obj.setId(rs.getInt("id"));
		obj.setCpf(rs.getString("cpf"));
		obj.setRg(rs.getString("rg"));
		obj.setCertidao_Casamento(rs.getString("certidao_Casamento"));
		obj.setCtps(rs.getString("ctps"));
		obj.setCnh(rs.getString("cnh"));
		obj.setCliente_Id(cliente);
		return obj;
	}

	private Cliente instantiateCliente(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setId(rs.getInt("id"));
		cliente.setNome(rs.getString("nome"));
		cliente.setEmail(rs.getString("email"));
		cliente.setTelefone(rs.getString("telefone"));
		cliente.setEndereco(rs.getString("endereco"));
		return cliente;
	}

	@Override
	public List<Pessoa_Fisica> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
