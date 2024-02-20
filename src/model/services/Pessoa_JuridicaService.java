package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.Pessoa_JuridicaDao;
import model.entities.Cliente;
import model.entities.Pessoa_Juridica;

public class Pessoa_JuridicaService {

	private Pessoa_JuridicaDao dao = DaoFactory.createPessoa_JuridicaDao();
	
	public List<Pessoa_Juridica> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Pessoa_Juridica obj, Cliente cliente) {
		if (obj.getId() == null) {
			dao.insert(obj, cliente);
		}
		else {
			dao.update(obj, cliente);
		}
	}
	
}
