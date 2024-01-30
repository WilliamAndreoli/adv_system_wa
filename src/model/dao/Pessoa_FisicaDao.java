package model.dao;

import java.util.List;

import model.entities.Pessoa_Fisica;

public interface Pessoa_FisicaDao {

	void insert(Pessoa_Fisica obj);
	void update(Pessoa_Fisica obj);
	void deletebyId(Integer id);
	Pessoa_Fisica findById(Integer id);
	List<Pessoa_Fisica> findAll();
	
}
