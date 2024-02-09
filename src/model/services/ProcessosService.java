package model.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProcessoDao;
import model.entities.Processo;
import model.util.Status;

public class ProcessosService {

	private ProcessoDao dao = DaoFactory.createProcessoDao();

	public List<Processo> findAll() {
		List<Processo> list = new ArrayList<>();
		list.add(new Processo(10, "12412641", new Date(), "Civil", Status.ARQUIVADO, "Cristiano Ronaldo", "Processo", 3000.00, 1500.00, null, null, null, null, null));
		list.add(new Processo(11, "12412641", new Date(), "Civil", Status.ARQUIVADO, "Cristiano Ronaldo", "Processo", 3000.00, 1500.00, null, null, null, null, null));
		list.add(new Processo(12, "12412641", new Date(), "Civil", Status.ARQUIVADO, "Cristiano Ronaldo", "Processo", 3000.00, 1500.00, null, null, null, null, null));
		return list;
	}
	
	public void saveOrUpdate(Processo obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}
		else {
			dao.update(obj);
		}
	}
	
}
