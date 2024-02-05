package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	}

	@Override
	public void update(Processo obj) {

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
			st = conn.prepareStatement("SELECT processo.*, cliente.nome as Nome_Do_Cliente, advogado.nome as Nome_Do_Advogado, "
				    + "parte_processo.nome as Nome_Da_Parte, tribunal.nome as Tribunal, usuario.login as Usuário "
				    + "FROM processo "
				    + "INNER JOIN cliente ON processo.cliente_Id = cliente.id "
				    + "INNER JOIN advogado ON processo.advogado_Id = advogado.id "
				    + "INNER JOIN parte_processo ON processo.partes = parte_processo.id "
				    + "INNER JOIN tribunal ON processo.tribunal = tribunal.id "
				    + "INNER JOIN usuario ON processo.usuario = usuario.id "
				    + "WHERE processo.id = ?");

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

	private Processo instantiateProcesso(ResultSet rs, Cliente cliente, Advogado advogado, Parte_Processo parte, Tribunal tribunal, Usuario usuario) throws SQLException {
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
		obj.setAdvogados(advogado);
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
		return null;

	}

}
