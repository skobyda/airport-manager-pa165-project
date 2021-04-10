package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.entity.Flight;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Michal Zelenák
 * @created 09.04.2021
 * @project airport-manager
 **/


/**
 * Interface for Flight Data object
 *
 */
public interface FlightDao {

    /**
     * Method for creating {@Code Flight} entity in database
     * @param flight
     */
    void create(Flight flight);

    /**
     * Method for listing all flights from database
     * @return List of {@Code Flight} entities
     */
    List<Flight> findAll();

    /**
     * Method for finding {@Code Flight} by ID from database
     * @param id
     * @return List of {@Code Flight} entities
     */
    Flight findById(int id);

    /**
     * Method for removing {@Code Flight} entity from database
     * @param id
     */
    void remove(Flight id);

    /**
     * Method for update of {@Code Flight} entity
     * @param flight
     */
    void update(Flight flight);

    /**
     * Method for finding list of {@Code Flight} entities by their departure times
     * @param departure
     * @return List of {@Code Flight} entities
     */
    List<Flight> findByDeparture(LocalDate departure);

    /**
     * Method for finding list of {@Code Flight} entities by their arrival times
     * @param arrival
     * @return List of {@Code Flight} entities
     */
    List<Flight> findByArrival(LocalDate arrival);

    /**
     * Method for finding {@Code Flight} entities by their flightCode
     * @param flightCode
     * @return List of {@Code Flight} entities
     */
    Flight findByFlightCode(String flightCode);

}
