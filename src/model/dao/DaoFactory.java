package model.dao;

import db.DB;
import model.dao.impl.Pessoa_FisicaJDBC;
import model.dao.impl.Pessoa_JuridicaJDBC;

public class DaoFactory {

	public static Pessoa_FisicaDao createPessoa_FisicaDao() {
		return new Pessoa_FisicaJDBC(DB.getConnection());
	}
	
	public static Pessoa_JuridicaDao createPessoa_JuridicaDao() {
		return new Pessoa_JuridicaJDBC(DB.getConnection());
	}
	
}
