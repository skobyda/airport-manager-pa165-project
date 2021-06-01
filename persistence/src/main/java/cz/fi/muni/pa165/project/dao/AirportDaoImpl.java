package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.entity.Airport;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Petr Hendrych
 * @created 09.04.2021
 * @project airport-manager
 **/

@Repository
public class AirportDaoImpl implements AirportDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Airport airport) {
        em.persist(airport);
    }

    @Override
    public List<Airport> findAll() {
        return em.createQuery("select a from Airport a", Airport.class).getResultList();
    }

    @Override
    public Airport findById(Long id) {
        Airport airport = em.find(Airport.class, id);
        if (airport == null)
            throw new AirportManagerException("Airport entity with this id does not exists");
        return airport;
    }

    @Override
    public List<Airport> findByName(String name) {
        return em.createQuery("select a from Airport a where a.name = :name", Airport.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<Airport> findByCity(String city) {
        return em.createQuery("select a from Airport a where a.city = :city", Airport.class)
                .setParameter("city", city)
                .getResultList();
    }

    @Override
    public List<Airport> findByCountry(String country) {
        return em.createQuery("select a from Airport a where a.country = :country", Airport.class)
                .setParameter("country", country)
                .getResultList();
    }

    @Override
    public void update(Airport airport) {
        em.merge(airport);
    }

    @Override
    public void delete(Airport airport) {
        em.remove(airport);
    }

    @Override
    public void deleteById(Long id) {
        Airport airport = findById(id);
        delete(airport);
    }
}
