package cz.fi.muni.pa165.project.controllers;

import cz.fi.muni.pa165.project.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.project.dto.UserDTO;
import cz.fi.muni.pa165.project.facade.UserFacade;
import cz.fi.muni.pa165.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;

/**
 * @author Petr Hendrych
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserDTO> auth(@RequestBody UserAuthenticateDTO userAuthenticateDTO) throws AuthenticationException {
        UserDTO u = userFacade.authenticate(userAuthenticateDTO);
        return ResponseEntity.ok(u);
    }
}
