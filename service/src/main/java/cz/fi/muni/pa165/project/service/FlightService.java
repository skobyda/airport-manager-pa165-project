package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Michal Zelenák
 **/

public interface FlightService {

    /**
     * Method to find specific {@code Flight} entity by it's id.
     *
     * @param id - id of {@code Flight} entity
     * @return - specific {@code Flight} entity
     */
    Flight findById(Long id);

    /**
     * Method that returns list of all Flights.
     *
     * @return - list of {@code Flight} entities
     */
    List<Flight> findAll();

    /**
     * Method to find list of all {@code Flight} entities by departure date, arrival date, departure airport, arrival airport.
     *
     * @param dateFrom           - date from which the filtered flights will be listed
     * @param dateTo             - date to which the filtered flights will be listed
     * @param departureAirportId - Id of the departure airport for filtering the flights
     * @param arrivalAirportId   - Id of the arrival airport for filtering the flights
     * @return - specific {@code Flight} entity
     */
    List<Flight> filterFlights(LocalDateTime dateFrom, LocalDateTime dateTo, Long departureAirportId, Long arrivalAirportId);

    /**
     * Method persist given entity.
     *
     * @param flight - Flight entity to be persisted
     */
    Flight create(Flight flight) throws AirportManagerException;

    /**
     * Method to update specific {@code Flight} entity parameters.
     *
     * @param flight - {@code Flight} entity to be updated
     */
    Flight update(Flight flight) throws AirportManagerException;

    /**
     * Removes {@code Flight} entity from database.
     *
     * @param id - id of {@code Flight} entity
     */
    void delete(Long id);

    /**
     * Add {@code Steward} into {@code Flight} entity.
     *
     * @param flightId  - id of {@code Flight} entity
     * @param stewardId - id of {@code Steward} entity
     */
    void addSteward(Long stewardId, Long flightId);

    /**
     * Remove {@code Steward} from {@code Flight} entity.
     *
     * @param flightId  - id of {@code Flight} entity
     * @param stewardId - id of {@code Steward} entity
     */
    void removeSteward(Long stewardId, Long flightId);

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
