package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.enums.AirplaneType;

import java.util.List;

/**
 * @author Petr Hendrych
 * @created 28.04.2021
 * @project airport-manager
 **/

public interface AirplaneService {

    /**
     * Method to get all {@Code Airplanes}.
     *
     * @return - list of {@Code Airplane} entities
     */
    List<Airplane> findAll();

    /**
     * Method to specific {@Code Airplane} entity by id.
     *
     * @param id - id of {@Code Airplane} entity
     * @return - {@Code Airplane} entity
     */
    Airplane findById(Long id);

    /**
     * Method to created Airplane entity.
     *
     * @param airplane - {@Code Airplane}
     */
    void create(Airplane airplane);

    /**
     * Method to delete {@Code Airplane} entity.
     *
     * @param id - id of {@Code Airplane} entity
     */
    void delete(Long id);

    /**
     * Method to update {@Code Airplane} entity.
     *
     * @param airplane - {@Code Airplane} entity with new values
     */
    void update(Airplane airplane);

    /**
     * Method to get list of {@Code Airplane} entities with bigger or equal then given capacity.
     *
     * @param capacity - given capacity for comparison
     * @return - list of {@Code Airplane} entities
     */
    List<Airplane> findWithBiggerOrEqualCapacity(Integer capacity);

    /**
     * Method to get list of {@Code Airplane} entities with lower or equal then given capacity.
     *
     * @param capacity - given capacity for comparison
     * @return - list of {@Code Airplane} entities
     */
    List<Airplane> findWithLowerOrEqualCapacity(Integer capacity);

    /**
     * Method to get list of {@Code Airplane} entities with given type.
     *
     * @param type - given type of aircraft
     * @return - list of {@Code Airplane} entities
     */
    List<Airplane> findByType(AirplaneType type);
}
