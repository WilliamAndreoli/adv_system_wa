package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.UsuarioDao;
import model.entities.Usuario;

public class UsuarioService {
	
	private UsuarioDao dao = DaoFactory.createUsuarioDao();

	public List<Usuario> findAll() {
		return null;
	}
	
	public void saveOrUpdate(Usuario obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
	public void authenticateUser(Usuario obj) {
		dao.authenticateUser(obj);
	}
	
}
