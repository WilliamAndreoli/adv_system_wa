package model.dao;

import java.util.List;

import model.entities.Cliente;
import model.entities.Pessoa_Fisica;

public interface Pessoa_FisicaDao {

	void insert(Pessoa_Fisica obj, Cliente cliente);
	void update(Pessoa_Fisica obj, Cliente cliente);
	void deletebyId(Integer id);
	Pessoa_Fisica findById(Integer id);
	List<Pessoa_Fisica> findAll();
	
}
