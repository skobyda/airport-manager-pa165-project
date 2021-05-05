package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.entity.Airport;

import java.util.List;

/**
 * @author Petr Hendrych
 * @created 09.04.2021
 * @project airport-manager
 **/

public interface AirportDao {

    /**
     * Method persist given entity.
     *
     * @param airport - entity to be persisted
     */
    void create(Airport airport);

    /**
     * Method that returns list of all Airports.
     *
     * @return - list of {@code Airport} entities
     */
    List<Airport> findAll();

    /**
     * Method to find specific {@code Airport} entity by it's id.
     *
     * @param id - id of {@code Airport} entity
     * @return - specific {@code Airport} entity
     */
    Airport findById(Long id);

    /**
     * Method to find all airports with given name.
     *
     * @param name - name of airport
     * @return - list of {@code Airport} entities with selected name
     */
    List<Airport> findByName(String name);

    /**
     * Method to find all airports in given city.
     *
     * @param city - name of city
     * @return - list of {@code Airport} entities in selected city
     */
    List<Airport> findByCity(String city);

    /**
     * Method to find all airport in given country.
     *
     * @param country - name of country
     * @return - list of {@code Airport} entities in selected country
     */
    List<Airport> findByCountry(String country);

    /**
     * Update existing {@code Airport} entity with new given one.
     *
     * @param airport - {@code Airport} entity to be updated
     */
    void update(Airport airport);

    /**
     * Removes {@code Airport} entity from database.
     *
     * @param airport - {@code Airport} entity to be removed
     */
    void delete(Airport airport);

    /**
     * Find {@code Airport} entity by id and removes it from database.
     *
     * @param id - id of {@code Airport} entity
     */
    void deleteById(Long id);
}
