package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProcessoDao;
import model.entities.Processo;

public class ProcessosService {

	private ProcessoDao dao = DaoFactory.createProcessoDao();

	public List<Processo> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(Processo obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}

	public List<Processo> findByNumero(String numero_Processo) {
		return dao.findByNumero(numero_Processo);
	}
	
}
