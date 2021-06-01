package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.enums.AirplaneType;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public Airplane findById(Long id) {
        Airplane airplane = em.find(Airplane.class, id);
        if (airplane == null)
            throw new AirportManagerException("Airplane entity with this id does not exists");
        return airplane;
    }

    @Override
    public void delete(Airplane plane) {
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
    public List<Airplane> findByType(AirplaneType type) {
        return em.createQuery("SELECT p FROM Airplane p WHERE p.type = :type ",
                Airplane.class).setParameter("type", type).getResultList();
    }

    @Override
    public List<Airplane> findByCapacity(Integer capacity) {
        return em.createQuery("SELECT p FROM Airplane p WHERE p.capacity = :capacity ",
                Airplane.class).setParameter("capacity", capacity).getResultList();
    }
}
