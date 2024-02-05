package model.dao;

import java.util.List;

import model.entities.Tribunal;

public interface TribunalDao {

	void insert(Tribunal obj);
	void update(Tribunal obj);
	void deletebyId(Integer id);
	Tribunal findById(Integer id);
	List<Tribunal> findAll();
	
}
