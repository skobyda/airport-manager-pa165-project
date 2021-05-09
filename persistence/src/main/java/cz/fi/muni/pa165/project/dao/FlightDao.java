package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.entity.Flight;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Michal Zelenák
 * @created 09.04.2021
 * @project airport-manager
 */
public interface FlightDao {

    /**
     * Method for creating {@Code Flight} entity in database
     * @param flight - entity to create
     */
    void create(Flight flight);

    /**
     * Method for listing all flights from database
     * @return List of {@Code Flight} entities
     */
    List<Flight> findAll();

    /**
     * Method for finding {@Code Flight} by ID from database
     * @param id - Id of the entity to find
     * @return List of {@Code Flight} entities
     */
    Flight findById(Long id);

    /**
     * Method for removing {@Code Flight} entity from database
     * @param id - id of entity to delete
     */
    void delete(Flight flight);

    /**
     * Method for update of {@Code Flight} entity
     * @param flight - entity to update
     */
    void update(Flight flight);

    /**
     * Method for finding list of {@Code Flight} entities by their departure times
     * @param departure - date when flight have scheduled departure
     * @return List of {@Code Flight} entities
     */
    List<Flight> findByDeparture(LocalDate departure);

    /**
     * Method for finding list of {@Code Flight} entities by their arrival times
     * @param arrival - date when flight have scheduled arrival
     * @return List of {@Code Flight} entities
     */
    List<Flight> findByArrival(LocalDate arrival);

    /**
     * Method for finding {@Code Flight} entities by their flightCode
     * @param flightCode - unique code of flight
     * @return List of {@Code Flight} entities
     */
    Flight findByFlightCode(String flightCode);

}
