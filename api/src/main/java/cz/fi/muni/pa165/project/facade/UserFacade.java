package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.project.dto.UserDTO;

import java.util.List;

/**
 * @author Petr Hendrych
 * @created 04.05.2021
 * @project airport-manager
 **/
public interface UserFacade {

    /**
     * Method to register user.
     *
     * @param user     - {@Code User} object
     * @param password - unencrypted user's password
     */
    void registerUser(UserDTO user, String password);

    /**
     * Method to get user by given id.
     *
     * @param id - id of {@Code User}
     * @return - User object
     */
    UserDTO findUserById(Long id);

    /**
     * Method to get user by given email
     *
     * @param email - email string of {@Code User}
     * @return - User object
     */
    UserDTO findUserByEmail(String email);

    /**
     * Method to get all registered users.
     *
     * @return - list of all {@Code User} objects
     */
    List<UserDTO> getAllUsers();

    /**
     * Method to try authenticate user trying to log in.
     *
     * @param u - authenticate {@Code User} object
     * @return - boolean depending on validation result
     */
    boolean authenticate(UserAuthenticateDTO u);
}
