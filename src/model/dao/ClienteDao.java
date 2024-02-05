package model.dao;

import model.entities.Cliente;

public interface ClienteDao {
	
	Cliente findById(Integer id);
	
}
