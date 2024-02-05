package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
	public void insert(Pessoa_Juridica obj, Cliente cliente) {
		String sqlCliente = null;
		String sqlPessoaJuridica = null;
		PreparedStatement stmtCliente = null;
		PreparedStatement stmtPessoaJuridica = null;
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
			sqlPessoaJuridica = "INSERT INTO pessoa_juridica (nome_Fantasia, cnpj, cliente_Id) VALUES (?, ?, ?)";
			stmtPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica, Statement.RETURN_GENERATED_KEYS);
			stmtPessoaJuridica.setString(1, obj.getNome_Fantasia());
			stmtPessoaJuridica.setString(2, obj.getCnpj());
			stmtPessoaJuridica.setInt(3, clienteId); // Utiliza o ID do cliente recuperado anteriormente
			int affectedRows1 = stmtPessoaJuridica.executeUpdate();

			// Recuperar o ID do cliente inserido

			int p_JId = -1;
			if (affectedRows1 > 0) {
				ResultSet generatedKeys1 = stmtPessoaJuridica.getGeneratedKeys();
				if (generatedKeys1.next()) {
					p_JId = generatedKeys1.getInt(1);

				}
			} else {
				throw new SQLException("Falha ao inserir pessoa juridica, nenhum registro afetado.");
			}

			// Confirma a transação
			conn.commit();

			cliente.setId(clienteId);
			obj.setId(p_JId);
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
				if (stmtPessoaJuridica != null) {
					stmtPessoaJuridica.close();
				}
				conn.setAutoCommit(true); // Restaura o modo de auto-commit
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void update(Pessoa_Juridica obj, Cliente cliente) {
		String sqlCliente = null;
		String sqlPessoaJuridica = null;
		PreparedStatement stmtCliente = null;
		PreparedStatement stmtPessoaJuridica = null;
		try {
		    conn.setAutoCommit(false); // Desativa o modo de auto-commit

		    // Update cliente
		    sqlCliente = "UPDATE cliente SET nome = ?, email = ?, telefone = ?, endereco = ? WHERE id = ?";
		    stmtCliente = conn.prepareStatement(sqlCliente);
		    stmtCliente.setString(1, cliente.getNome());
		    stmtCliente.setString(2, cliente.getEmail());
		    stmtCliente.setString(3, cliente.getTelefone());
		    stmtCliente.setString(4, cliente.getEndereco());
		    stmtCliente.setInt(5, cliente.getId()); 
		    stmtCliente.executeUpdate();

		    // Update pessoa física
		    sqlPessoaJuridica = "UPDATE pessoa_juridica SET nome_Fantasia = ?, cnpj = ? WHERE id = ?";
		    stmtPessoaJuridica = conn.prepareStatement(sqlPessoaJuridica);
		    stmtPessoaJuridica.setString(1, obj.getNome_Fantasia());
		    stmtPessoaJuridica.setString(2, obj.getCnpj());
		    stmtPessoaJuridica.setInt(3, obj.getId()); 
		    int affectedRows1 = stmtPessoaJuridica.executeUpdate();

		    // Confirma a transação
		    conn.commit();
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
		        if (stmtPessoaJuridica != null) {
		            stmtPessoaJuridica.close();
		        }
		        conn.setAutoCommit(true); // Restaura o modo de auto-commit
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		
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
			st = conn.prepareStatement("SELECT pessoa_juridica.*, cliente.nome as Nome, "
					+ "cliente.email as Email, cliente.telefone as Telefone, cliente.endereco as Endereco "
					+ "FROM pessoa_juridica INNER JOIN cliente " + "ON pessoa_juridica.cliente_Id = cliente.id "
					+ "WHERE pessoa_juridica.id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Cliente cliente = instantiateCliente(rs);

				Pessoa_Juridica obj = instantiatePessoa_Juridica(rs, cliente);
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Pessoa_Juridica instantiatePessoa_Juridica(ResultSet rs, Cliente cliente) throws SQLException {
		Pessoa_Juridica obj = new Pessoa_Juridica();
		obj.setId(rs.getInt("id"));
		obj.setNome_Fantasia(rs.getString("nome_Fantasia"));
		obj.setCnpj(rs.getString("cnpj"));
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
	public List<Pessoa_Juridica> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
