package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.*;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;

import java.time.LocalDate;
import java.util.List;

/**
 @author Michal Zelenák
 @created 27/04/2021
 @project airport-manager
 **/
public interface FlightFacade {

    /**
     * Method to find specific {@code Flight} entity by it's id.
     *
     * @param id - id of {@code FlightDTO} entity
     * @return - specific {@code FlightDTO} entity
     */
    FlightDTO findById(Long id);

    /**
     * Method that returns list of all Flights.
     *
     * @return - returns list of {@code FlightDTO} entities
     */
    List<FlightDTO> findAll();

    /**
     * Method persist given entity.
     *
     * @param flightDTO - FlightDTO entity to be persisted
     */
    Long create(FlightCreateDTO flightDTO) throws AirportManagerException;

    /**
     * Method to update specific {@code FlightDTO} entity parameters.
     *
     * @param flightDTO - {@code FlightDTO} entity to be updated
     */
    Long update(FlightDTO flightDTO) throws AirportManagerException;

    /**
     * Removes {@code FlightDTO} entity from database based on its id.
     *
     * @param id - id of {@code FlightDTO} entity to be removed
     */
    void delete(Long id);

    /**
     * Method to find list of all {@code FlightDTO} entities based on arrival date, departure date, arrival airport Id, departure airport id.
     *
     * @param dateFrom - date to which flights will be filtered
     * @param dateTo - date to which flights will be filtered
     * @param departureAirportId - id of the airport from which flights will be filtered
     * @param arrivalAirportId - id of the airport to which flights will be filtered
     * @return - returns list of {@code FlightDTO} entities
     */
    List<FlightDTO> getFilteredList(LocalDate dateFrom, LocalDate dateTo, Long departureAirportId, Long arrivalAirportId);

    /**
     * Add {@code Steward} into {@code Flight} entity.
     *
     * @param flightId - id of {@code Flight} entity
     * @param stewardId - id of {@code Steward} entity
     */
    void addSteward(Long stewardId, Long flightId);

    /**
     * Add {@code Steward} into {@code Flight} entity.
     *
     * @param flightId - id of {@code Flight} entity
     * @param stewardId - id of {@code Steward} entity
     */
    void removeSteward(Long stewardId, Long flightId);

    /**
     * Method for listing n first flights ordered by arrival date
     *
     * @param limit maximal number of flights to return
     * @param airportId id of airport to where flights will land
     * @return list of flights
     */
    public List<FlightDTO> getFlightsOrderedByArrival(int limit, Long airportId);

    /**
     * Method for listing n first flights ordered by departure date
     *
     * @param limit maximal number of flights to return
     * @param airportId id of airport from where flights will start
     * @return list of flights
     */
    public List<FlightDTO> getFlightsOrderedByDeparture(int limit, Long airportId);


}
