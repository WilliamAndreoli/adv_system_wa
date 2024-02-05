package model.dao;

import java.util.List;

import model.entities.Cliente;
import model.entities.Parte_Processo;
import model.entities.Pessoa_Fisica;

public interface Parte_ProcessoDao {

	void insert(Parte_Processo obj);
	void update(Parte_Processo obj);
	void deletebyId(Integer id);
	Parte_Processo findById(Integer id);
	List<Parte_Processo> findAll();
	
}
