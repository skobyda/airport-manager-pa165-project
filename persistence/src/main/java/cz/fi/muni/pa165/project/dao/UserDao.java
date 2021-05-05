package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.entity.User;

import java.util.List;

/**
 * @author Petr Hendrych
 * @created 30.04.2021
 * @project airport-manager
 **/

public interface UserDao {

    /**
     * Method to create new user.
     *
     * @param u - User entity to be persisted.
     */
    void create(User u);

    /**
     * Method to get {@Code User} entity by given id.
     *
     * @param id - id
     * @return - {@Code User} entity
     */
    /**
     * Method to update user.
     *
     * @param u - User entity to be merged.
     */
    void update(User u);

    /**
     * Method to delete user.
     *
     * @param u - User entity to be deleted.
     */
    void delete(User u);

    User findById(Long id);

    /**
     * Method to get {@Code User} entity by given email.
     *
     * @param email - given email string
     * @return {@Code User} entity
     */
    User findUserByEmail(String email);

    /**
     * Method to get all {@Code Users}
     *
     * @return - list of {@Code User} entities
     */
    List<User> findAll();
}
