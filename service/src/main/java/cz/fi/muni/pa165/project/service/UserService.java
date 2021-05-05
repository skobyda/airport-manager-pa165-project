package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.entity.User;

import java.util.List;

/**
 * @author Petr Hendrych
 * @created 04.05.2021
 * @project airport-manager
 **/
public interface UserService {

    /**
     * Method to get all {@Code User} entities.
     *
     * @return - list of {@Code User} entities
     */
    List<User> getAllUsers();

    /**
     * Method to authenticate {@Code User} trying to log in.
     *
     * @param u - given {@Code User} entity
     * @param password - unencrypted password
     * @return - boolean depending on validation result
     */
    boolean authenticate(User u, String password);

    /**
     * Method to get {@Code User} entity by given id.
     *
     * @param id - id of {@Code User}
     * @return - {@Code User} entity
     */
    User findUserById(Long id);

    /**
     * Method to get {@Code User} entity by given email.
     *
     * @param email - string email of {@Code User}
     * @return - {@Code User} entity
     */
    User findUserByEmail(String email);
}
