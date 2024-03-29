package model.dao;

import java.util.List;

import model.entities.Cliente;
import model.entities.Pessoa_Juridica;

public interface Pessoa_JuridicaDao {

	void insert(Pessoa_Juridica obj, Cliente cliente);
	void update(Pessoa_Juridica obj, Cliente cliente);
	void deletebyId(Integer id);
	Pessoa_Juridica findById(Integer id);
	List<Pessoa_Juridica> findAll();
	
}
