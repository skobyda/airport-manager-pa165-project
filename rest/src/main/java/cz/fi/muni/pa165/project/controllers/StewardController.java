package cz.fi.muni.pa165.project.controllers;

import cz.fi.muni.pa165.project.dto.StewardCreateDTO;
import cz.fi.muni.pa165.project.dto.StewardDTO;
import cz.fi.muni.pa165.project.dto.StewardFilterDTO;
import cz.fi.muni.pa165.project.enums.UserRole;
import cz.fi.muni.pa165.project.facade.StewardFacade;
import cz.fi.muni.pa165.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Petr Hendrych
 **/

@CrossOrigin
@RestController
@RequestMapping("/rest/stewards")
public class StewardController extends AbstractController {

    private final StewardFacade stewardFacade;

    @Autowired
    public StewardController(StewardFacade stewardFacade, UserService userService) {
        super(userService);
        this.stewardFacade = stewardFacade;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<StewardDTO>> getStewards(@RequestHeader("Authorization") String header, StewardFilterDTO filter) throws AuthenticationException {
        this.authenticate(header, UserRole.FLIGHT_MANAGER);
        return ResponseEntity.ok(stewardFacade.findAll(filter));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createSteward(@RequestHeader("Authorization") String header, @Valid @RequestBody StewardCreateDTO stewardCreateDTO) throws AuthenticationException {
        this.authenticate(header, UserRole.FLIGHT_MANAGER);
        stewardFacade.create(stewardCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<StewardDTO> getStewardById(@RequestHeader("Authorization") String header, @PathVariable Long id) throws AuthenticationException {
        this.authenticate(header, UserRole.FLIGHT_MANAGER);
        return ResponseEntity.ok(stewardFacade.findById(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteSteward(@RequestHeader("Authorization") String header, @PathVariable Long id) throws AuthenticationException {
        this.authenticate(header, UserRole.FLIGHT_MANAGER);
        stewardFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateSteward(@RequestHeader("Authorization") String header, @PathVariable Long id, @Valid @RequestBody StewardDTO stewardDTO) throws AuthenticationException {
        this.authenticate(header, UserRole.FLIGHT_MANAGER);
        stewardFacade.update(stewardDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
