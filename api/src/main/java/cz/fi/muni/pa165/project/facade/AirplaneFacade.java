package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.AirplaneCreateDTO;
import cz.fi.muni.pa165.project.dto.AirplaneDTO;
import cz.fi.muni.pa165.project.enums.AirplaneType;

import java.util.List;

/**
 * @author Petr Hendrych
 **/
public interface AirplaneFacade {

    /**
     * Method to get all airplanes.
     *
     * @return - list of airplanes
     */
    List<AirplaneDTO> findAll();

    /**
     * Method to get {@Code Airplane} by given id.
     *
     * @param id - id of airplane
     * @return - {@Code Airplane} object
     */
    AirplaneDTO findById(Long id);

    /**
     * Method for creating {@Code Airplane} object.
     *
     * @param airplane - {@Code Airplane} object
     */
    void create(AirplaneCreateDTO airplane);

    /**
     * Method to update {@Code Airplane} entity
     *
     * @param airplane - {@Code Airplane} object
     */
    void update(AirplaneDTO airplane);

    /**
     * Method to delete {@Code Airplane} entity
     *
     * @param id - id of {@Code Airplane} entity
     */
    void delete(Long id);

    /**
     * Method to get list of {@Code Airplanes} with bigger or equal capacity then is given.
     *
     * @param capacity - given capacity for comparison
     * @return - list of {@Code Airplanes} objects
     */
    List<AirplaneDTO> findWithBiggerOrEqualCapacity(Integer capacity);

    /**
     * Method to get list of {@Code Airplanes} with smaller or equal capacity then is given.
     *
     * @param capacity - given capacity for comparison
     * @return - list of {@Code Airplanes} objects
     */
    List<AirplaneDTO> findWithLowerOrEqualCapacity(Integer capacity);

    /**
     * Method to get list of {@Code Airplanes} with given type.
     *
     * @param type - given type of aircraft
     * @return - list of {@Code Airplanes} objects
     */
    List<AirplaneDTO> findByType(AirplaneType type);
}
