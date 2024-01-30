package application;

import model.dao.DaoFactory;
import model.dao.Pessoa_FisicaDao;
import model.dao.Pessoa_JuridicaDao;
import model.entities.Pessoa_Fisica;
import model.entities.Pessoa_Juridica;

public class Test {

	public static void main(String[] args) {
		
		Pessoa_FisicaDao p_FDao = DaoFactory.createPessoa_FisicaDao();
		
		Pessoa_JuridicaDao p_JDao = DaoFactory.createPessoa_JuridicaDao();
		
		Pessoa_Fisica p_F = p_FDao.findById(1);
		
		Pessoa_Juridica p_J = p_JDao.findById(1);
		
		System.out.println(p_F);
		
		System.out.println(p_J);
		
	}

}
