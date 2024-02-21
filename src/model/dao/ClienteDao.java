package model.dao;

import java.util.List;

import model.entities.Cliente;

public interface ClienteDao {
	
	Cliente findById(Integer id);

	List<Cliente> findAll();
	
	List<Cliente> findByNome(String nome);
}
