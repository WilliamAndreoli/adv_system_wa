package model.dao;

import db.DB;
import model.dao.impl.AdvogadoJDBC;
import model.dao.impl.ClienteJDBC;
import model.dao.impl.Parte_ProcessoJDBC;
import model.dao.impl.Pessoa_FisicaJDBC;
import model.dao.impl.Pessoa_JuridicaJDBC;
import model.dao.impl.ProcessoJDBC;
import model.dao.impl.TribunalJDBC;
import model.dao.impl.UsuarioJDBC;

public class DaoFactory {

	public static Pessoa_FisicaDao createPessoa_FisicaDao() {
		return new Pessoa_FisicaJDBC(DB.getConnection());
	}
	
	public static Pessoa_JuridicaDao createPessoa_JuridicaDao() {
		return new Pessoa_JuridicaJDBC(DB.getConnection());
	}
	
	public static UsuarioDao createUsuarioDao() {
		return new UsuarioJDBC(DB.getConnection());
	}
	
	public static ClienteDao createCienteDao() {
		return new ClienteJDBC(DB.getConnection());
	}
	
	public static Parte_ProcessoDao createParte_ProcessoDao() {
		return new Parte_ProcessoJDBC(DB.getConnection());
	}
	
	public static TribunalDao createTribunalDao() {
		return new TribunalJDBC(DB.getConnection());
	}
	
	public static AdvogadoDao createAdvogadoDao() {
		return new AdvogadoJDBC(DB.getConnection());
	}
	
	public static ProcessoDao createProcessoDao() {
		return new ProcessoJDBC(DB.getConnection());
	}
	
}
