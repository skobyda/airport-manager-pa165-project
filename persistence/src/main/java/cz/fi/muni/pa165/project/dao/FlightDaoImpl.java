package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
        return em.createQuery("select f from Flight f", Flight.class).getResultList();
    }

    @Override
    public Flight findById(Long id) {
        Flight flight = em.find(Flight.class, id);
        if (flight == null)
            throw new AirportManagerException("Flight entity with this id does not exists");
        return flight;
    }

    @Override
    public void delete(Flight flight) {
        em.remove(flight);
    }

    @Override
    public void update(Flight flight) {
        em.merge(flight);
    }

    @Override
    public List<Flight> findByDeparture(LocalDate departure) {
        return em.createQuery("select f from Flight f where f.departure = :departure", Flight.class).setParameter("departure", departure)
                .getResultList();
    }

    @Override
    public List<Flight> findByArrival(LocalDate arrival) {
        return em.createQuery("select f from Flight f where f.arrival = :arrival", Flight.class).setParameter("arrival", arrival)
                .getResultList();
    }

    @Override
    public Flight findByFlightCode(String flightCode) {
        return em.createQuery("select f from Flight f where f.flightCode = :flightCode", Flight.class).setParameter("flightCode", flightCode)
                .getSingleResult();
    }

    @Override
    public List<Flight> getFlightsOrderedByArrival(int limit, Long airportId) {
        TypedQuery<Flight> query = em.createQuery("select f from Flight f where f.originAirport.id = :id order by f.arrival", Flight.class)
                .setParameter("id", airportId);

        return limit == 0 ? query.getResultList() : query.setMaxResults(limit).getResultList();
    }

    @Override
    public List<Flight> getFlightsOrderedByDeparture(int limit, Long airportId) {
        TypedQuery<Flight> query = em.createQuery("select f from Flight f where f.destinationAirport.id = :id order by f.departure", Flight.class)
                .setParameter("id", airportId);

        return limit == 0 ? query.getResultList() : query.setMaxResults(limit).getResultList();
    }
}
