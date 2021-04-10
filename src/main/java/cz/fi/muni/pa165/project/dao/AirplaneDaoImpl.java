package cz.fi.muni.pa165.project.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import cz.fi.muni.pa165.project.entity.Airplane;

/**
 * @author Simon Kobyda
 * @created 10.04.2021
 * @project airport-manager
 **/
@Repository
public class AirplaneDaoImpl implements AirplaneDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void create(Airplane plane) {
		em.persist(plane);
	}

	@Override
	public List<Airplane> findAll() {
		return em.createQuery("select p from Airplane p", Airplane.class)
				.getResultList();
	}

	@Override
	public Airplane findById(Integer id) {
		return em.find(Airplane.class, id);
	}

	@Override
	public void remove(Airplane plane) {
		em.remove(plane);
	}

	@Override
	public void update(Airplane plane) {
		em.merge(plane);
	}

	@Override
	public List<Airplane> findByName(String name) {
		return em.createQuery("SELECT p FROM Airplane p WHERE p.name like :name ",
				Airplane.class).setParameter("name", name).getResultList();
	}

	@Override
	public List<Airplane> findByType(String type) {
		return em.createQuery("SELECT p FROM Airplane p WHERE p.type like :type ",
				Airplane.class).setParameter("type", type).getResultList();
	}
	@Override
	public List<Airplane> findByCapacity(Integer capacity) {
		return em.createQuery("SELECT p FROM Airplane p WHERE p.capacity = :capacity ",
				Airplane.class).setParameter("capacity", capacity).getResultList();
	}

}
