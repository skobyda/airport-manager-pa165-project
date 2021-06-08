package cz.fi.muni.pa165.project.controllers;

import cz.fi.muni.pa165.project.enums.UserRole;
import cz.fi.muni.pa165.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.AuthenticationException;

/**
 * @author Jozef Vanický
 **/
public abstract class AbstractController {

    UserService userService;

    @Autowired
    public AbstractController(UserService userService) {
        this.userService = userService;
    }

    protected boolean authenticate(String header, UserRole role) throws AuthenticationException {
        boolean result = userService.verifyRole(header, role);

        if (!result) {
            throw new AuthenticationException("Unauthorized");
        }

        return true;
    }
}
