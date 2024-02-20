package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.Pessoa_FisicaDao;
import model.entities.Cliente;
import model.entities.Pessoa_Fisica;

public class Pessoa_FisicaService {

	private Pessoa_FisicaDao dao = DaoFactory.createPessoa_FisicaDao();
	
	public List<Pessoa_Fisica> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Pessoa_Fisica obj, Cliente cliente) {
		if (obj.getId() == null) {
			dao.insert(obj, cliente);
		}
		else {
			dao.update(obj, cliente);
		}
	}
	
}
