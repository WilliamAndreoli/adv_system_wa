package model.services;

import java.util.List;

import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.entities.Cliente;

public class ClienteService {

	private ClienteDao dao = DaoFactory.createCienteDao();
	
	public List<Cliente> findAll() {
		return dao.findAll();
	}
	
}
