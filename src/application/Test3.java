package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProcessoDao;
import model.entities.Processo;

public class Test3 {

	public static void main(String[] args) {

		System.out.println("=== TEST processo ====");
		System.out.println("=== TEST 1: findById ====");
		
		ProcessoDao pDao = DaoFactory.createProcessoDao();
		
		Processo processo = pDao.findById(1);
		System.out.println(processo);
		
		System.out.println("=== TEST 2: findAll ====");
		
		List<Processo> listP = pDao.findAll();
	
		for (Processo p  : listP) {
			System.out.println(p);
		}
		
		System.out.println("=== TEST 3: deleteById ====");
		
		pDao.deletebyId(2);
		System.out.println("Deleted!");
		
	}

}
