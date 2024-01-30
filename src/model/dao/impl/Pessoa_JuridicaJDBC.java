package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.Pessoa_JuridicaDao;
import model.entities.Cliente;
import model.entities.Pessoa_Juridica;

public class Pessoa_JuridicaJDBC implements Pessoa_JuridicaDao {

	private Connection conn;
	
	public Pessoa_JuridicaJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Pessoa_Juridica obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Pessoa_Juridica obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletebyId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pessoa_Juridica findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				    "SELECT pessoa_juridica.*, cliente.nome as Nome, "
				    + "cliente.email as Email, cliente.telefone as Telefone, cliente.endereco as Endereco "
				    + "FROM pessoa_juridica INNER JOIN cliente "
				    + "ON pessoa_juridica.cliente_Id = cliente.id "
				    + "WHERE pessoa_juridica.id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Cliente cliente = instantiateCliente(rs);
				
				Pessoa_Juridica obj = instantiatePessoa_Juridica(rs, cliente);
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

	private Pessoa_Juridica instantiatePessoa_Juridica(ResultSet rs, Cliente cliente) throws SQLException {
		Pessoa_Juridica obj = new Pessoa_Juridica();
		obj.setId(rs.getInt("id"));
		obj.setNome_fantasia("nome_Fantasia");
		obj.setCnpj(rs.getString("cnpj"));
		obj.setCliente_Id(cliente);
		return obj;
	}

	private Cliente instantiateCliente(ResultSet rs) throws SQLException {
		Cliente cliente =new Cliente();
		cliente.setId(rs.getInt("id"));
		cliente.setNome(rs.getString("nome"));
		cliente.setEmail(rs.getString("email"));
		cliente.setTelefone(rs.getString("telefone"));
		cliente.setEndereco(rs.getString("endereco"));
		return cliente;
	}

	@Override
	public List<Pessoa_Juridica> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


	
	
}
