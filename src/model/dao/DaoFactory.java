package model.dao;

import model.dao.impl.Pessoa_FisicaJDBC;
import model.dao.impl.Pessoa_JuridicaJDBC;

public class DaoFactory {

	public static Pessoa_FisicaDao createPessoa_FisicaDao() {
		return new Pessoa_FisicaJDBC();
	}
	
	public static Pessoa_JuridicaDao createPessoa_JuridicaDao() {
		return new Pessoa_JuridicaJDBC();
	}
	
}
