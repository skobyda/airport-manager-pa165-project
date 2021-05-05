package cz.fi.muni.pa165.project.facade;

import java.util.*;

import cz.fi.muni.pa165.project.dto.AirportDTO;
import cz.fi.muni.pa165.project.dto.FlightDTO;
import cz.fi.muni.pa165.project.dto.AirportCreateDTO;

/**
 @author Simon Kobyda
 @created 27/04/2021
 @project airport-manager
 **/
public interface AirportFacade {

    /**
     * Method persist given entity.
     *
     * @param airport - AirportCreateDTO entity to be persisted
     */
    void create(AirportCreateDTO airport);

    /**
     * Method to update specific {@code AirportDTO} entity parameters.
     *
     * @param airport - {@code AirportDTO} entity to be updated
     */
    void update(AirportDTO airport);

    /**
     * Removes {@code AirportDTO} entity from database.
     *
     * @param id - {@code AirportDTO} entity to be removed
     */
    void delete(Long id);

    /**
     * Method to find list of all {@code Airport} entities.
     *
     * @return - List of {@code AirportDTO} entity
     */
    List<AirportDTO> findAll();

    /**
     * Method to find list of all {@code AirportDTO} entities by its name.
     *
     * @param name - name of {@code AirportDTO} entity
     * @return - A list of {@code AirportDTO} entities
     */
    List<AirportDTO> findByName(String name);

    /**
     * Method to find list of all {@code AirportDTO} entities by its id.
     *
     * @param id - id of {@code AirportDTO} entity
     * @return - specific {@code AirportDTO} entity
     */
    AirportDTO findById(Long id);

    /**
     * Method to find list of all {@code AirportDTO} entities by its country.
     *
     * @param country - country of {@code AirportDTO} entity
     * @return - A list of {@code AirportDTO} entities
     */
    List<AirportDTO> findByCountry(String country);

    /**
     * Method to find list of all {@code AirportDTO} entities by its city.
     *
     * @param city - name of {@code AirportDTO} entity
     * @return - A list of {@code AirportDTO} entities
     */
    List<AirportDTO> findByCity(String city);

    /**
     * Method to get a list of all {@code FlightDTO} entities which are a departure flights for given airport.
     *
     * @param airportId - {@code AirportDTO} id of entity to be searched for departure flights
     * @return - list of {@code FlightDTO} entities
     */
    List<FlightDTO> getDepartureFlights(Long airportId);

    /**
     * Method to get a list of all {@code FlightDTO} entities which are a arrival flights for given airport.
     *
     * @param airportId - {@code AirportDTO} id of entity to be searched for arrival flights
     * @return - list of {@code FlightDTO} entities
     */
    List<FlightDTO> getArrivalFlights(Long airportId);
}
