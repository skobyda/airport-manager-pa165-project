package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.enums.AirplaneType;

import java.util.List;

/**
 @author Simon Kobyda
 @created 07/04/2021
 @project airport-manager
 *
 * Interface for Airplane data object
 **/
public interface AirplaneDao {
    /**
     * Adds a flight entity to the database
     * @param plane added to the database
     */
    void create(Airplane plane);

    /**
     * Return all airplanes recorded in database
     */
    List<Airplane> findAll();

    /**
     * Finds a plane by its id
     * @param id a key used to find the plane
     */
    Airplane findById(Long id);

    /**
     * Removes a plane from database
     * @param plane a plane to be removed from database
     */
    void delete(Airplane plane);

    /**
     * Updates a plane in database
     * @param plane a plane to be updated in database
     */
    void update(Airplane plane);

    /**
     * Returns a list of all planes which has the provided name
     * @param name a provided search key
     */
    List<Airplane> findByName(String name);

    /**
     * Returns a list of all planes which has the provided capacity
     * @param capacity a provided search key
     */
    List<Airplane> findByCapacity(Integer capacity);

    /**
     * Returns a list of all planes which has the provided type
     * @param type a provided search key
     */
    List<Airplane> findByType(AirplaneType type);
}
