package model.dao;

import java.util.List;

import model.entities.Processo;

public interface ProcessoDao {

	void insert(Processo obj);
	void update(Processo obj);
	void deletebyId(Integer id);
	Processo findById(Integer id);
	List<Processo> findAll();
	List<Processo> findByNumero(String numero_Processo);
	
}
