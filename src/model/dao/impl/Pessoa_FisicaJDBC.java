package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public void insert(Pessoa_Fisica obj, Cliente cliente) {
		String sqlCliente = null;
		PreparedStatement stmtCliente = null;
		PreparedStatement stmtPessoaFisica = null;
		try {
		    conn.setAutoCommit(false); // Desativa o modo de auto-commit

		    // Inserir cliente
		    sqlCliente = "INSERT INTO cliente (nome, email, telefone, endereco) VALUES (?, ?, ?, ?)";
		    stmtCliente = conn.prepareStatement(sqlCliente, Statement.RETURN_GENERATED_KEYS);
		    stmtCliente.setString(1, cliente.getNome());
		    stmtCliente.setString(2, cliente.getEmail());
		    stmtCliente.setString(3, cliente.getTelefone());
		    stmtCliente.setString(4, cliente.getEndereco());
		    int affectedRows = stmtCliente.executeUpdate();

		    // Recuperar o ID do cliente inserido
		    int clienteId = -1;
		    if (affectedRows > 0) {
		        ResultSet generatedKeys = stmtCliente.getGeneratedKeys();
		        if (generatedKeys.next()) {
		            clienteId = generatedKeys.getInt(1);
		            
		        }
		    } else {
		        throw new SQLException("Falha ao inserir cliente, nenhum registro afetado.");
		    }

		    // Inserir pessoa física
		    String sqlPessoaFisica = "INSERT INTO pessoa_fisica (cpf, rg, certidao_Casamento, ctps, cnh, cliente_Id) VALUES (?, ?, ?, ?, ?, ?)";
		    stmtPessoaFisica = conn.prepareStatement(sqlPessoaFisica, Statement.RETURN_GENERATED_KEYS);
		    stmtPessoaFisica.setString(1, obj.getCpf());
		    stmtPessoaFisica.setString(2, obj.getRg());
		    stmtPessoaFisica.setString(3, obj.getCertidao_Casamento());
		    stmtPessoaFisica.setString(4, obj.getCtps());
		    stmtPessoaFisica.setString(5, obj.getCnh());
		    stmtPessoaFisica.setInt(6, clienteId); // Utiliza o ID do cliente recuperado anteriormente
		    int affectedRows1 = stmtPessoaFisica.executeUpdate();

		 // Recuperar o ID do cliente inserido
		    int p_FId = -1;
		    if (affectedRows1 > 0) {
		        ResultSet generatedKeys1 = stmtPessoaFisica.getGeneratedKeys();
		        if (generatedKeys1.next()) {
		            p_FId = generatedKeys1.getInt(1);
		            
		        }
		    } else {
		        throw new SQLException("Falha ao inserir pessoa fisica, nenhum registro afetado.");
		    }
		    
		    // Confirma a transação
		    conn.commit();
		    
		    cliente.setId(clienteId);
		    obj.setId(p_FId);
		} catch (SQLException e) {
		    // Em caso de erro, desfaz a transação
		    try {
		        if (conn != null) {
		            conn.rollback();
		        }
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		    e.printStackTrace();
		} finally {
		    try {
		        if (stmtCliente != null) {
		            stmtCliente.close();
		        }
		        if (stmtPessoaFisica != null) {
		            stmtPessoaFisica.close();
		        }
		        conn.setAutoCommit(true); // Restaura o modo de auto-commit
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		
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
			
			List<Pessoa_Fisica> list = new ArrayList<>();
			Map<Integer, Cliente> map = new HashMap<>();
			
			while (rs.next()) {
				
				Cliente cliente = map.get(rs.getInt("cliente_Id"));
				
				if (cliente == null) {
					cliente = instantiateCliente(rs);
					map.put(rs.getInt("cliente_Id"), cliente);
				}
				
				Pessoa_Fisica obj = instantiatePessoa_Fisica(rs, cliente);
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
