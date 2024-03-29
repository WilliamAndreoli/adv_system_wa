package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.ClienteDao;
import model.dao.DaoFactory;
import model.dao.Pessoa_FisicaDao;
import model.dao.Pessoa_JuridicaDao;
import model.dao.UsuarioDao;
import model.entities.Cliente;
import model.entities.Pessoa_Fisica;
import model.entities.Pessoa_Juridica;
import model.entities.Usuario;

public class Test {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		Pessoa_FisicaDao p_FDao = DaoFactory.createPessoa_FisicaDao();

		Pessoa_JuridicaDao p_JDao = DaoFactory.createPessoa_JuridicaDao();

		UsuarioDao userDao = DaoFactory.createUsuarioDao();

		System.out.println("=== TEST 1: findById =====");
		Pessoa_Fisica p_F = p_FDao.findById(1);

		Pessoa_Juridica p_J = p_JDao.findById(1);

		Usuario user = userDao.findById(1);

		System.out.println(p_F);

		System.out.println(p_J);

		System.out.println(user);

		System.out.println("=== TEST 2: findAll =====");
		List<Pessoa_Fisica> list = p_FDao.findAll();

		for (Pessoa_Fisica obj : list) {
			System.out.println(obj);
		}

		System.out.println("=== TEST 3: findAll de Usuarios =====");

		List<Usuario> listUsers = userDao.findAll();

		for (Usuario u : listUsers) {
			System.out.println(u);
		}

		System.out.println("=== TEST 4: authenticateUser ====");

		System.out.println("Digite o login: ");
		String login = sc.nextLine();
		System.out.println("Digite a senha: ");
		String senha = sc.nextLine();

		Usuario authUser = new Usuario(1000, login, senha);

		userDao.authenticateUser(authUser);

		System.out.println("=== TEST 5: insert ====");

		Cliente clienteInsert = new Cliente(null, "Greg", "greg@gmail.com", "47 99318744", "Rua Rio Carlos, 456", "P_F");

		Pessoa_Fisica p_F2 = new Pessoa_Fisica(null, null, null, null, null, null, "12315554", "15456436", null, null, null, new Date(),
				clienteInsert);

		p_FDao.insert(p_F2, clienteInsert);
		System.out.println("Inserted! New id = " + p_F2.getId());

		Cliente clienteInsert1 = new Cliente(null, "Multinational", "multi@gmail.com", "47 34867898", "Rua Rio Carlos, 456", "P_J");

		Pessoa_Juridica p_J2 = new Pessoa_Juridica(null, null, null, null, null, null, "Multinational", "1324325", clienteInsert1);

		p_JDao.insert(p_J2, clienteInsert);
		System.out.println("Inserted! New id = " + p_J2.getId());

		Usuario userInsert = new Usuario(null, "Jose", "1234");
		
		userDao.insert(userInsert);
		
		System.out.println("Inserted! New id = " + userInsert.getId());
		
		System.out.println("=== TEST 5: update ====");
		ClienteDao clienteDao = DaoFactory.createCienteDao();
		Cliente cliente1 = clienteDao.findById(4);
		Cliente cliente2 = clienteDao.findById(5);
		
		p_F = p_FDao.findById(2);
		p_J = p_JDao.findById(1);
		user = userDao.findById(2);
		
		cliente1.setEmail("will@gmail.com");
		cliente2.setNome("Angela");
		p_J.setCnpj("654321");
		user.setLogin("Damazo");
		
		p_FDao.update(p_F, cliente1);
		p_JDao.update(p_J, cliente2);
		userDao.update(user);
		
		System.out.println("Update complete!");
		
		System.out.println("=== TEST 6: delete ====");
		//p_FDao.deletebyId(3);
		//System.out.println("PF deleted!");
		
		//p_JDao.deletebyId(5);
		//System.out.println("PJ deleted!");
		
		//userDao.deletebyId(3);
	}

}
