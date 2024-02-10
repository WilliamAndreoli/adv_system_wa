package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.Parte_ProcessoDao;
import model.entities.Parte_Processo;

public class Parte_ProcessoService {

private Parte_ProcessoDao dao = DaoFactory.createParte_ProcessoDao();
	
	public List<Parte_Processo> findAll() {
		return dao.findAll();
	}
	
}
