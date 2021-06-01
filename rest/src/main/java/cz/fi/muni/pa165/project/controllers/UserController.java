package cz.fi.muni.pa165.project.controllers;

import cz.fi.muni.pa165.project.dto.UserDTO;
import cz.fi.muni.pa165.project.enums.UserRole;
import cz.fi.muni.pa165.project.facade.UserFacade;
import cz.fi.muni.pa165.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

/**
 * @author Petr Hendrych
 * @created 31.05.2021
 * @project airport-manager
 **/

@CrossOrigin
@RestController
@RequestMapping("/rest/users")
public class UserController extends AbstractController {

    private final UserFacade userFacade;

    @Autowired
    public UserController(UserFacade userFacade, UserService userService) {
        super(userService);
        this.userFacade = userFacade;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userFacade.getAllUsers());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userFacade.findUserById(id));
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<Void> authenticateUser(@RequestHeader("Authorization") String header) throws AuthenticationException {
        this.authenticate(header, UserRole.FLIGHT_MANAGER);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
