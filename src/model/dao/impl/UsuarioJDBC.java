package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.UsuarioDao;
import model.entities.Cliente;
import model.entities.Usuario;

public class UsuarioJDBC implements UsuarioDao {

	private Connection conn;
	
	public UsuarioJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Usuario obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Usuario obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletebyId(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario findById(Integer id) {
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
				
				Usuario obj = instantiateUsuario(rs, cliente);
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

	private Usuario instantiateUsuario(ResultSet rs, Cliente cliente) throws SQLException {
		Usuario obj = new Usuario();
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
	public List<Usuario> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				    "SELECT pessoa_fisica.*, cliente.nome as Nome, cliente.email as Email, "
				    + "cliente.telefone as Telefone, cliente.endereco as Endereco "
				    + "FROM pessoa_fisica INNER JOIN cliente "
				    + "ON pessoa_fisica.cliente_Id = cliente.id "
				    + "ORDER BY Nome");

		
			rs = st.executeQuery();
			
			List<Usuario> list = new ArrayList<>();
			Map<Integer, Cliente> map = new HashMap<>();
			
			while (rs.next()) {
				
				Cliente cliente = map.get(rs.getInt("cliente_Id"));
				
				if (cliente == null) {
					cliente = instantiateCliente(rs);
					map.put(rs.getInt("cliente_Id"), cliente);
				}
				
				Usuario obj = instantiateUsuario(rs, cliente);
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

	
	
}
