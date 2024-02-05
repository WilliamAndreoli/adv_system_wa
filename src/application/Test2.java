package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.Parte_ProcessoDao;
import model.entities.Parte_Processo;

public class Test2 {

	public static void main(String[] args) {

		System.out.println("=== TEST parte_processo ====");
		System.out.println("=== TEST 1: findById");
		
		Parte_ProcessoDao p_PDao = DaoFactory.createParte_ProcessoDao();
		
		Parte_Processo p_P = p_PDao.findById(1);
		System.out.println(p_P);
		
		System.out.println("=== TEST 2: findAll");
		List<Parte_Processo> list = p_PDao.findAll();
		
		for (Parte_Processo p : list) {
			System.out.println(p);
		}
		
		System.out.println("=== TEST 3: insert");
		
		Parte_Processo parte = new Parte_Processo(null, "Josenildo");
		
		p_PDao.insert(parte);
		System.out.println("Inserted! New Id = " + parte.getId());
		
		System.out.println("=== TEST 4: update ====");
		
		parte = p_PDao.findById(3);
		
		parte.setNome("Jubilso");
		
		p_PDao.update(parte);
		System.out.println("Updated!");
		
		System.out.println("=== TEST 4: deletById ====");
	
		p_PDao.deletebyId(4);
		System.out.println("Deleted!");
		
	}

}
