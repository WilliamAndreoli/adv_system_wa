package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.ProcessoDao;
import model.entities.Advogado;
import model.entities.Cliente;
import model.entities.Parte_Processo;
import model.entities.Processo;
import model.entities.Tribunal;
import model.entities.Usuario;
import model.util.Status;

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
		
		//pDao.deletebyId(2);
		//System.out.println("Deleted!");
		
		System.out.println("=== TEST 4: insert ====");
		
		Cliente cliente = new Cliente(5, "Jose Alvaro", "jose@gmail.com", "47 99873162", "Rua Rui Rui", "P_F");
		
		Advogado advogado = new Advogado(1, "Adv", "151314152");
		
		Parte_Processo partes = new Parte_Processo(5, "Jurindo");
		
		Tribunal tribunal = new Tribunal(3, "Tribunal", "Rua 25", null);
		
		Usuario usuario = new Usuario(2, "Jose", "1234");
		
		Processo p = new Processo(null, "145123653", new Date(), "Penal", Status.ARQUIVADO, "Paula Vieria", "Roubo de joias", 10000.00, 3000.00, cliente, advogado, partes, tribunal, usuario); 
		
		pDao.insert(p);
		System.out.println("Inserted! New id =" + p.getId());
		
		System.out.println("=== TEST 5: update ====");
		
		processo = pDao.findById(1);
		
		processo.setDescricao("Alterado!");
		
		pDao.update(processo);
		
		System.out.println("Update complete!");
	}

}
