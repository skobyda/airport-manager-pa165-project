package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.StewardCreateDTO;
import cz.fi.muni.pa165.project.dto.StewardDTO;
import cz.fi.muni.pa165.project.dto.StewardFilterDTO;

import java.util.List;

/**
 * @author Jozef Vanický
 **/
public interface StewardFacade {

    /**
     * Method to find specific {@code Steward} entity by it's id.
     *
     * @param id - id of {@code StewardDTO} entity
     * @return - specific {@code StewardDTO} entity
     */
    StewardDTO findById(Long id);

    /**
     * Method persist given entity.
     *
     * @param s - StewardDTO entity to be persisted
     */
    void create(StewardCreateDTO s);

    /**
     * Removes {@code StewardDTO} entity from database based on its id.
     *
     * @param id - id of {@code StewardDTO} entity to be removed
     */
    void delete(Long id);

    /**
     * Method that returns list of all Stewards.
     *
     * @return - returns list of {@code StewardDTO} entities
     */
    List<StewardDTO> findAll(StewardFilterDTO filter);

    /**
     * Method to find list of all {@code StewardDTO} entities based on firstName.
     *
     * @param firstName - name of {@code StewardDTO} entity
     * @return - returns list of {@code StewardDTO} entities
     */
    List<StewardDTO> findByFirstName(String firstName);

    /**
     * Method to find list of all {@code StewardDTO} entities based on lastName.
     *
     * @param lastName - id of {@code StewardDTO} entity
     * @return - returns list of {@code StewardDTO} entities
     */
    List<StewardDTO> findByLastName(String lastName);

    /**
     * Method to find list of all {@code StewardDTO} entities based on countryCode.
     *
     * @param countryCode - id of {@code StewardDTO} entity
     * @return - returns list of {@code StewardDTO} entities
     */
    List<StewardDTO> findByCountryCode(String countryCode);

    /**
     * Method to find list of all {@code StewardDTO} entities based on passportNumber.
     *
     * @param passportNumber - id of {@code StewardDTO} entity
     * @return - returns list of {@code StewardDTO} entities
     */
    List<StewardDTO> findByPassportNumber(String passportNumber);

    /**
     * Method to update specific {@code StewardDTO} entity parameters.
     *
     * @param s - {@code StewardDTO} entity to be updated
     */
    void update(StewardDTO s);
}
