package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import authentication.PasswordUtils;
import db.DB;
import db.DbException;
import model.dao.UsuarioDao;
import model.entities.Usuario;
import model.exceptions.ValidationException;

public class UsuarioJDBC implements UsuarioDao {

	private Connection conn;

	public UsuarioJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Usuario obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO usuario" + "(login, senha)" + "VALUES" + "(?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, obj.getLogin());
			st.setString(2, PasswordUtils.encryptPassword(obj.getSenha())); // Criptografando a senha

			int rowsAffected = st.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);

					obj.setId(id);

					DB.closeResultSet(rs);
				}
			} else {
				throw new DbException("Unexpected error! No rows affected!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Usuario obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE usuario " + "SET login = ?, senha = ? " + "WHERE Id = ?");

			st.setString(1, obj.getLogin());
			st.setString(2, obj.getSenha());
			st.setInt(3, obj.getId());

			st.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void deletebyId(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM usuario " + "WHERE Id = ?");

			st.setInt(1, id);

			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Usuario findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT usuario.* " + "FROM usuario " + "WHERE usuario.id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Usuario obj = instantiateUsuario(rs);
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

	private Usuario instantiateUsuario(ResultSet rs) throws SQLException {
		Usuario obj = new Usuario();
		obj.setId(rs.getInt("id"));
		obj.setLogin(rs.getString("login"));
		obj.setSenha(rs.getString("senha"));
		return obj;
	}

	@Override
	public List<Usuario> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT usuario.*" + "FROM usuario " + "ORDER BY login");

			rs = st.executeQuery();

			List<Usuario> list = new ArrayList<>();

			while (rs.next()) {
				Usuario obj = instantiateUsuario(rs);
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

	@Override
	public void authenticateUser(Usuario obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT usuario.* " + "FROM usuario " + "WHERE usuario.login = ?");

			st.setString(1, obj.getLogin());
			rs = st.executeQuery();
			if (rs.next()) {
				String hashedPassword = rs.getString("senha");
				String inputPasswordHash = PasswordUtils.encryptPassword(obj.getSenha());
				if (hashedPassword.equals(inputPasswordHash)) {
					System.out.println("Bem vindo ao sistema");
				} else {
					throw new DbException("Senha inválida");
				}
			} else {
				System.out.println("Usuário não encontrado");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public Usuario findByLogin(String login) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT usuario.* " + "FROM usuario " + "WHERE usuario.login = ?");

			st.setString(1, login);
			rs = st.executeQuery();
			if (rs.next()) {
				Usuario obj = instantiateUsuario(rs);
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

}
