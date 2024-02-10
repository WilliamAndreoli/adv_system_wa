package model.services;

import java.util.List;

import model.dao.AdvogadoDao;
import model.dao.DaoFactory;
import model.entities.Advogado;

public class AdvogadoService {

private AdvogadoDao dao = DaoFactory.createAdvogadoDao();
	
	public List<Advogado> findAll() {
		return dao.findAll();
	}
	
}
