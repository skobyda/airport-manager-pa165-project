package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.entity.Flight;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Michal Zelenák
 * @created 09.04.2021
 * @project airport-manager
 **/

@Repository
public class FlightDaoImpl implements FlightDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Flight flight) {
        em.persist(flight);
    }

    @Override
    public List<Flight> findAll() {
        return em.createQuery("select f from Flight f",Flight.class).getResultList();
    }

    @Override
    public Flight findById(int id) {
        return em.find(Flight.class,id);
    }

    @Override
    public void remove(Flight flight) {
        em.remove(flight);
    }

    @Override
    public void update(Flight flight) {
        em.merge(flight);
    }

    @Override
    public List<Flight> findByDeparture(LocalDate departure) {
        return em.createQuery("select f from Flight f where f.departure= :departure", Flight.class).setParameter("departure", departure)
                .getResultList();
    }

    @Override
    public List<Flight> findByArrival(LocalDate arrival) {
        return em.createQuery("select f from Flight f where f.arrival= :arrival", Flight.class).setParameter("arrival", arrival)
                .getResultList();
    }

    @Override
    public Flight findByFlightCode(String flightCode) {
        return em.createQuery("select f from Flight f where f.flightCode= :flightCode", Flight.class).setParameter("flightCode", flightCode)
                .getSingleResult();
    }
}
