package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.entity.Airport;
import cz.fi.muni.pa165.project.entity.Flight;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Simon Kobyda
 * @created 27/04/2021
 * @project airport-manager
 **/

@Service
public interface AirportService {

    /**
     * Method persist given entity.
     *
     * @param airport - Airport entity to be persisted
     */
    void create(Airport airport);

    /**
     * Method to update specific {@code Airport} entity parameters.
     *
     * @param airport - {@code Airport} entity to be updated
     */
    void update(Airport airport);

    /**
     * Removes {@code Airport} entity from database.
     *
     * @param id - {@code Airport} entity to be removed
     */
    void delete(Long id);

    /**
     * Method to find list of all {@code Airport} entities.
     *
     * @return - List of {@code Airport} entity
     */
    List<Airport> findAll();

    /**
     * Method to find list of all {@code Airport} entities by its id.
     *
     * @param id - id of {@code Airport} entity
     * @return - List of {@code Airport} entities
     */
    Airport findById(Long id);

    /**
     * Method to find list of all {@code Airport} entities by its name.
     *
     * @param name - name of {@code Airport} entity
     * @return - specific {@code Airport} entity
     */
    List<Airport> findByName(String name);

    /**
     * Method to find list of all {@code Airport} entities by its country.
     *
     * @param country - country of {@code Airport} entity
     * @return - specific {@code Airport} entity
     */
    List<Airport> findByCountry(String country);

    /**
     * Method to find list of all {@code Airport} entities by its city.
     *
     * @param city - name of {@code Airport} entity
     * @return - specific {@code Airport} entity
     */
    List<Airport> findByCity(String city);

    /**
     * Method to get a list of all {@code FlightDTO} entities which are a departure flights for given airport.
     *
     * @param airportId - {@code Airport} id of entity to be searched for departure flights
     * @return - list of {@code FlightDTO} entities
     */
    List<Flight> getDepartureFlights(Long airportId);

    /**
     * Method to get a list of all {@code FlightDTO} entities which are a arrival flights for given airport.
     *
     * @param airportId - {@code AirportDTO} id of entity to be searched for arrival flights
     * @return - list of {@code FlightDTO} entities
     */
    List<Flight> getArrivalFlights(Long airportId);

}
