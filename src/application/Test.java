package application;

import model.dao.DaoFactory;
import model.dao.Pessoa_FisicaDao;
import model.entities.Pessoa_Fisica;
import model.entities.Pessoa_Juridica;

public class Test {

	public static void main(String[] args) {
		
		Pessoa_Fisica pessoa_Fisica = new Pessoa_Fisica(1, "Angela", "angela@gmail.com", "47 9456-5677", "Rua Rui Barbosa, 306", "948.847.909-50", "7.456.876", null, null, null);

		Pessoa_Juridica pessoa_Juridicao = new Pessoa_Juridica(1, "Adv Escritorio", "advescritorio@gmai.com", "47 3566-6787", "Rua Eunacio Silva, 193", "ADV", "312.123.123.31");
		
		Pessoa_FisicaDao p_FDao = DaoFactory.createPessoa_FisicaDao();
		
		
	}

}
