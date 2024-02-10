package model.services;

import java.util.List;

import model.dao.TribunalDao;
import model.dao.DaoFactory;
import model.entities.Tribunal;

public class TribunalService {

private TribunalDao dao = DaoFactory.createTribunalDao();
	
	public List<Tribunal> findAll() {
		return dao.findAll();
	}
	
}
