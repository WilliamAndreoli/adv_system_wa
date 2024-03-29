package model.dao;

import java.util.List;

import model.entities.Usuario;

public interface UsuarioDao {

	void insert(Usuario obj);
	void update(Usuario obj);
	void deletebyId(Integer id);
	Usuario findById(Integer id);
	List<Usuario> findAll();
	void authenticateUser(Usuario obj);
	Usuario findByLogin(String login);
	
}
