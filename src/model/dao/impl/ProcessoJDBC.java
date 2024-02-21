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

import authentication.Session;
import db.DB;
import db.DbException;
import model.dao.ProcessoDao;
import model.entities.Advogado;
import model.entities.Cliente;
import model.entities.Parte_Processo;
import model.entities.Processo;
import model.entities.Tribunal;
import model.entities.Usuario;
import model.util.Status;

public class ProcessoJDBC implements ProcessoDao {

	private Connection conn;

	public ProcessoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Processo obj) {
		PreparedStatement stmtProcesso = null;
		try {
			conn.setAutoCommit(false); // Desativa o modo de auto-commit

			// Inserir processo
			String sqlProcesso = "INSERT INTO processo (numero_Processo, data_De_Abertura, tipo, status_Processo, juiz, descricao, honorarios, custos, "
					+ "cliente_Id, advogado_Id, partes, tribunal, usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			stmtProcesso = conn.prepareStatement(sqlProcesso, Statement.RETURN_GENERATED_KEYS);
			stmtProcesso.setString(1, obj.getNumero_Processo());
			stmtProcesso.setDate(2, new java.sql.Date(obj.getData_De_Abertura().getTime()));
			stmtProcesso.setString(3, obj.getTipo());
			stmtProcesso.setString(4, obj.getStatus().name());
			stmtProcesso.setString(5, obj.getJuiz());
			stmtProcesso.setString(6, obj.getDescricao());
			stmtProcesso.setDouble(7, obj.getHonorarios());
			stmtProcesso.setDouble(8, obj.getCustos());
			stmtProcesso.setInt(9, obj.getCliente_Id().getId());
			stmtProcesso.setInt(10, obj.getAdvogado_Id().getId());
			stmtProcesso.setInt(11, obj.getPartes().getId());
			stmtProcesso.setInt(12, obj.getTribunal().getId());
			Integer userId = Session.getUserId();
			stmtProcesso.setInt(13, userId);
			int affectedRows = stmtProcesso.executeUpdate();

			// Recuperar o ID do processo inserido
			int processoId = -1;
			if (affectedRows > 0) {
				ResultSet generatedKeys = stmtProcesso.getGeneratedKeys();
				if (generatedKeys.next()) {
					processoId = generatedKeys.getInt(1);
				}
			} else {
				throw new SQLException("Falha ao inserir processo, nenhum registro afetado.");
			}

			// Confirma a transação
			conn.commit();

			obj.setId(processoId);
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
				if (stmtProcesso != null) {
					stmtProcesso.close();
				}
				conn.setAutoCommit(true); // Restaura o modo de auto-commit
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(Processo obj) {
		PreparedStatement stmtProcesso = null;
		try {
		    conn.setAutoCommit(false); // Desativa o modo de auto-commit

		    // Update pessoa física
		    String sqlProcesso = "UPDATE processo SET numero_Processo = ?, data_De_Abertura = ?, "
		    		+ "tipo = ?, status_Processo = ?, juiz = ?, descricao = ?, honorarios = ?, custos = ?, "
		    		+ "cliente_Id = ?, advogado_Id = ?, partes = ?, tribunal = ?, usuario = ? "
		    		+ "WHERE id = ?";
		    stmtProcesso = conn.prepareStatement(sqlProcesso);
		    stmtProcesso.setString(1, obj.getNumero_Processo());
		    stmtProcesso.setDate(2, new java.sql.Date(obj.getData_De_Abertura().getTime()));
		    stmtProcesso.setString(3, obj.getTipo());
		    stmtProcesso.setString(4, obj.getStatus().name());
		    stmtProcesso.setString(5, obj.getJuiz());
		    stmtProcesso.setString(6, obj.getDescricao());
		    stmtProcesso.setDouble(7, obj.getHonorarios());
		    stmtProcesso.setDouble(8, obj.getCustos());
		    stmtProcesso.setInt(9, obj.getCliente_Id().getId());
			stmtProcesso.setInt(10, obj.getAdvogado_Id().getId());
			stmtProcesso.setInt(11, obj.getPartes().getId());
			stmtProcesso.setInt(12, obj.getTribunal().getId());
			stmtProcesso.setInt(13, obj.getUsuario().getId());
			stmtProcesso.setInt(14, obj.getId());
		    int affectedRows1 = stmtProcesso.executeUpdate();

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
		        if (stmtProcesso != null) {
		            stmtProcesso.close();
		        }
		        conn.setAutoCommit(true); // Restaura o modo de auto-commit
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		
	}

	@Override
	public void deletebyId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM processo " + "WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Processo findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT processo.*, cliente.nome as Nome_Do_Cliente, advogado.nome as Nome_Do_Advogado, "
							+ "parte_processo.nome as Nome_Da_Parte, tribunal.nome as Tribunal, usuario.login as Usuário "
							+ "FROM processo " + "INNER JOIN cliente ON processo.cliente_Id = cliente.id "
							+ "INNER JOIN advogado ON processo.advogado_Id = advogado.id "
							+ "INNER JOIN parte_processo ON processo.partes = parte_processo.id "
							+ "INNER JOIN tribunal ON processo.tribunal = tribunal.id "
							+ "INNER JOIN usuario ON processo.usuario = usuario.id " + "WHERE processo.id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Cliente cliente = instantiateCliente(rs);

				Advogado advogado = instantiateAdvogado(rs);

				Parte_Processo parte = instantiatePartes(rs);

				Tribunal tribunal = instantiateTribunal(rs);

				Usuario usuario = instantiateUsuario(rs);

				Processo obj = instantiateProcesso(rs, cliente, advogado, parte, tribunal, usuario);

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

	@Override
	public List<Processo> findByNumero(String numero_Processo) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT processo.*, cliente.nome as Nome_Do_Cliente, advogado.nome as Nome_Do_Advogado, "
							+ "parte_processo.nome as Nome_Da_Parte, tribunal.nome as Tribunal, usuario.login as Usuário "
							+ "FROM processo " + "INNER JOIN cliente ON processo.cliente_Id = cliente.id "
							+ "INNER JOIN advogado ON processo.advogado_Id = advogado.id "
							+ "INNER JOIN parte_processo ON processo.partes = parte_processo.id "
							+ "INNER JOIN tribunal ON processo.tribunal = tribunal.id "
							+ "INNER JOIN usuario ON processo.usuario = usuario.id " + "WHERE processo.numero_Processo = ?");

			st.setString(1, numero_Processo);
			rs = st.executeQuery();

			List<Processo> list = new ArrayList<>();
			Map<Integer, Cliente> mapCli = new HashMap<>();
			Map<Integer, Advogado> mapAdv = new HashMap<>();
			Map<Integer, Parte_Processo> mapPar = new HashMap<>();
			Map<Integer, Tribunal> mapTri = new HashMap<>();
			Map<Integer, Usuario> mapUser = new HashMap<>();

			while (rs.next()) {

				Cliente cliente = mapCli.get(rs.getInt("cliente_Id"));
				Advogado advogado = mapAdv.get(rs.getInt("advogado_Id"));
				Parte_Processo partes = mapPar.get(rs.getInt("partes"));
				Tribunal tribunal = mapTri.get(rs.getInt("tribunal"));
				Usuario usuario = mapUser.get(rs.getInt("usuario"));

				if (cliente == null) {
					cliente = instantiateCliente(rs);
					mapCli.put(rs.getInt("cliente_Id"), cliente);
				}

				if (advogado == null) {
					advogado = instantiateAdvogado(rs);
					mapAdv.put(rs.getInt("advogado_Id"), advogado);
				}

				if (partes == null) {
					partes = instantiatePartes(rs);
					mapPar.put(rs.getInt("partes"), partes);
				}

				if (tribunal == null) {
					tribunal = instantiateTribunal(rs);
					mapTri.put(rs.getInt("tribunal"), tribunal);
				}

				if (usuario == null) {
					usuario = instantiateUsuario(rs);
					mapUser.put(rs.getInt("usuario"), usuario);
				}

				Processo obj = instantiateProcesso(rs, cliente, advogado, partes, tribunal, usuario);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	
	private Processo instantiateProcesso(ResultSet rs, Cliente cliente, Advogado advogado, Parte_Processo parte,
			Tribunal tribunal, Usuario usuario) throws SQLException {
		Processo obj = new Processo();
		obj.setId(rs.getInt("id"));
		obj.setNumero_Processo(rs.getString("numero_Processo"));
		obj.setData_De_Abertura(rs.getDate("data_De_Abertura"));
		obj.setTipo(rs.getString("tipo"));
		obj.setStatus(Status.SUSPENSO);
		obj.setJuiz(rs.getString("juiz"));
		obj.setDescricao(rs.getString("descricao"));
		obj.setHonorarios(rs.getDouble("honorarios"));
		obj.setCustos(rs.getDouble("custos"));
		obj.setCliente_Id(cliente);
		obj.setAdvogado_Id(advogado);
		obj.setPartes(parte);
		obj.setTribunal(tribunal);
		obj.setUsuario(usuario);
		return obj;
	}

	private Cliente instantiateCliente(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setId(rs.getInt("id"));
		cliente.setNome(rs.getString("Nome_Do_Cliente"));
		return cliente;
	}

	private Advogado instantiateAdvogado(ResultSet rs) throws SQLException {
		Advogado advogado = new Advogado();
		advogado.setId(rs.getInt("id"));
		advogado.setNome(rs.getString("Nome_Do_Advogado"));
		return advogado;
	}

	private Parte_Processo instantiatePartes(ResultSet rs) throws SQLException {
		Parte_Processo parte = new Parte_Processo();
		parte.setId(rs.getInt("id"));
		parte.setNome(rs.getString("Nome_Da_Parte"));
		return parte;
	}

	private Tribunal instantiateTribunal(ResultSet rs) throws SQLException {
		Tribunal tribunal = new Tribunal();
		tribunal.setId(rs.getInt("id"));
		tribunal.setNome(rs.getString("Tribunal"));
		return tribunal;
	}

	private Usuario instantiateUsuario(ResultSet rs) throws SQLException {
		Usuario obj = new Usuario();
		obj.setId(rs.getInt("id"));
		obj.setLogin(rs.getString("Usuario"));
		return obj;
	}

	@Override
	public List<Processo> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT processo.*, cliente.nome as Nome_Do_Cliente, advogado.nome as Nome_Do_Advogado, "
							+ "parte_processo.nome as Nome_Da_Parte, tribunal.nome as Tribunal, usuario.login as Usuário "
							+ "FROM processo " + "INNER JOIN cliente ON processo.cliente_Id = cliente.id "
							+ "INNER JOIN advogado ON processo.advogado_Id = advogado.id "
							+ "INNER JOIN parte_processo ON processo.partes = parte_processo.id "
							+ "INNER JOIN tribunal ON processo.tribunal = tribunal.id "
							+ "INNER JOIN usuario ON processo.usuario = usuario.id " + "ORDER BY tipo");

			rs = st.executeQuery();

			List<Processo> list = new ArrayList<>();
			Map<Integer, Cliente> mapCli = new HashMap<>();
			Map<Integer, Advogado> mapAdv = new HashMap<>();
			Map<Integer, Parte_Processo> mapPar = new HashMap<>();
			Map<Integer, Tribunal> mapTri = new HashMap<>();
			Map<Integer, Usuario> mapUser = new HashMap<>();

			while (rs.next()) {

				Cliente cliente = mapCli.get(rs.getInt("cliente_Id"));
				Advogado advogado = mapAdv.get(rs.getInt("advogado_Id"));
				Parte_Processo partes = mapPar.get(rs.getInt("partes"));
				Tribunal tribunal = mapTri.get(rs.getInt("tribunal"));
				Usuario usuario = mapUser.get(rs.getInt("usuario"));

				if (cliente == null) {
					cliente = instantiateCliente(rs);
					mapCli.put(rs.getInt("cliente_Id"), cliente);
				}

				if (advogado == null) {
					advogado = instantiateAdvogado(rs);
					mapAdv.put(rs.getInt("advogado_Id"), advogado);
				}

				if (partes == null) {
					partes = instantiatePartes(rs);
					mapPar.put(rs.getInt("partes"), partes);
				}

				if (tribunal == null) {
					tribunal = instantiateTribunal(rs);
					mapTri.put(rs.getInt("tribunal"), tribunal);
				}

				if (usuario == null) {
					usuario = instantiateUsuario(rs);
					mapUser.put(rs.getInt("usuario"), usuario);
				}

				Processo obj = instantiateProcesso(rs, cliente, advogado, partes, tribunal, usuario);
				list.add(obj);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
