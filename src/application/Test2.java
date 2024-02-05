package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.Parte_ProcessoDao;
import model.dao.TribunalDao;
import model.entities.Parte_Processo;
import model.entities.Tribunal;

public class Test2 {

	public static void main(String[] args) {

		System.out.println("=== TEST parte_processo / tribunal ====");
		System.out.println("=== TEST 1: findById");
		
		Parte_ProcessoDao p_PDao = DaoFactory.createParte_ProcessoDao();
		TribunalDao tribunalDao = DaoFactory.createTribunalDao();
		
		
		Parte_Processo p_P = p_PDao.findById(1);
		Tribunal tribunal = tribunalDao.findById(1);
		System.out.println(p_P);
		System.out.println(tribunal);
		
		System.out.println("=== TEST 2: findAll");
		List<Tribunal> listTribunal = tribunalDao.findAll();
		List<Parte_Processo> listP = p_PDao.findAll();
		
		for (Parte_Processo p : listP) {
			System.out.println(p);
		}
		
		for (Tribunal t : listTribunal) {
			System.out.println(t);
		}
		
		System.out.println("=== TEST 3: insert");
		
		Tribunal tribunal1 = new Tribunal(null, "Tribunal do povo", "Rua 15", "SC");
		
		Parte_Processo parte = new Parte_Processo(null, "Josenildo");
		
		p_PDao.insert(parte);
		tribunalDao.insert(tribunal1);
		System.out.println("Inserted! New Id = " + parte.getId());
		System.out.println("Inserted! New Id = " + tribunal1.getId());
		
		System.out.println("=== TEST 4: update ====");
		
		tribunal = tribunalDao.findById(1);
		tribunal.setNome("Tribunal Igual");
		
		parte = p_PDao.findById(3);
		parte.setNome("Jubilso");
		
		tribunalDao.update(tribunal);
		p_PDao.update(parte);
		System.out.println("Updated!");
		
		System.out.println("=== TEST 4: deletById ====");
	
		tribunalDao.deletebyId(4);
		p_PDao.deletebyId(4);
		System.out.println("Deleted!");
		
	}

}
