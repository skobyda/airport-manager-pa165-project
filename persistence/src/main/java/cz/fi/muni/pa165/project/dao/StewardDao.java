package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.dto.StewardFilterDTO;
import cz.fi.muni.pa165.project.entity.Steward;

import java.util.List;

/**
 * @author Jozef Vanický
 **/
public interface StewardDao {

    /**
     * Method to find specific {@code Steward} entity by it's id.
     *
     * @param id - id of {@code Steward} entity
     * @return - specific {@code Steward} entity
     */
    Steward findById(Long id);

    /**
     * Method persist given entity.
     *
     * @param s - Steward entity to be persisted
     */
    void create(Steward s);

    /**
     * Removes {@code Steward} entity from database.
     *
     * @param s - {@code Steward} entity to be removed
     */
    void delete(Steward s);

    /**
     * Method that returns list of all Stewards.
     *
     * @return - list of {@code Steward} entities
     */
    List<Steward> findAll(StewardFilterDTO filter);

    /**
     * Method to find list of all {@code Steward} entities by it's firstName.
     *
     * @param firstName - name of {@code Steward} entity
     * @return - specific {@code Steward} entity
     */
    List<Steward> findByFirstName(String firstName);

    /**
     * Method to find list of all {@code Steward} entities by it's lastName.
     *
     * @param lastName - id of {@code Steward} entity
     * @return - specific {@code Steward} entity
     */
    List<Steward> findByLastName(String lastName);

    /**
     * Method to find list of all {@code Steward} entities by it's countryCode.
     *
     * @param countryCode - id of {@code Steward} entity
     * @return - specific {@code Steward} entity
     */
    List<Steward> findByCountryCode(String countryCode);

    /**
     * Method to find list of all {@code Steward} entities by it's passportNumber.
     *
     * @param passportNumber - id of {@code Steward} entity
     * @return - specific {@code Steward} entity
     */
    List<Steward> findByPassportNumber(String passportNumber);

    /**
     * Method to update specific {@code Steward} entity parameters.
     *
     * @param s - {@code Steward} entity to be updated
     */
    void update(Steward s);
}
