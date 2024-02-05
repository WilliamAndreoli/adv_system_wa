package model.dao;

import java.util.List;

import model.entities.Advogado;

public interface AdvogadoDao {

	void insert(Advogado obj);
	void update(Advogado obj);
	void deletebyId(Integer id);
	Advogado findById(Integer id);
	List<Advogado> findAll();
	
}
