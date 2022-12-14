package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.entity.Flight;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Michal Zelenák
 */
public interface FlightDao {

    /**
     * Method for creating {@Code Flight} entity in database
     *
     * @param flight - entity to create
     */
    void create(Flight flight);

    /**
     * Method for listing all flights from database
     *
     * @return List of {@Code Flight} entities
     */
    List<Flight> findAll();

    /**
     * Method for finding {@Code Flight} by ID from database
     *
     * @param id - Id of the entity to find
     * @return List of {@Code Flight} entities
     */
    Flight findById(Long id);

    /**
     * Method for removing {@Code Flight} entity from database
     *
     * @param flight - flight entity to delete
     */
    void delete(Flight flight);

    /**
     * Method for update of {@Code Flight} entity
     *
     * @param flight - entity to update
     */
    void update(Flight flight);

    /**
     * Method for finding list of {@Code Flight} entities by their departure times
     *
     * @param departure - date when flight have scheduled departure
     * @return List of {@Code Flight} entities
     */
    List<Flight> findByDeparture(LocalDateTime departure);

    /**
     * Method for finding list of {@Code Flight} entities by their arrival times
     *
     * @param arrival - date when flight have scheduled arrival
     * @return List of {@Code Flight} entities
     */
    List<Flight> findByArrival(LocalDateTime arrival);

    /**
     * Method for finding {@Code Flight} entities by their flightCode
     *
     * @param flightCode - unique code of flight
     * @return List of {@Code Flight} entities
     */
    Flight findByFlightCode(String flightCode);

    /**
     * Method for listing n first flights ordered by arrival date
     *
     * @param limit     maximal number of flights to return
     * @param airportId id of airport to where flights will land
     * @return list of flights
     */
    List<Flight> getFlightsOrderedByArrival(int limit, Long airportId);

    /**
     * Method for listing n first flights ordered by departure date
     *
     * @param limit     maximal number of flights to return
     * @param airportId id of airport from where flights will start
     * @return list of flights
     */
    List<Flight> getFlightsOrderedByDeparture(int limit, Long airportId);
}
