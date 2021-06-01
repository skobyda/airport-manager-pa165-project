package cz.fi.muni.pa165.project.controllers;

import cz.fi.muni.pa165.project.dto.AirplaneCreateDTO;
import cz.fi.muni.pa165.project.dto.AirplaneDTO;
import cz.fi.muni.pa165.project.enums.AirplaneType;
import cz.fi.muni.pa165.project.enums.UserRole;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import cz.fi.muni.pa165.project.facade.AirplaneFacade;
import cz.fi.muni.pa165.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * @author Michal Zelenák
 * @created 28.05.2021
 * @project airplane-manager
 **/
@CrossOrigin
@RestController
@RequestMapping("/rest/airplanes")
public class AirplaneController extends AbstractController {
    private final AirplaneFacade airplaneFacade;


    @Autowired
    public AirplaneController(AirplaneFacade airplaneFacade, UserService userService) {
        super(userService);
        this.airplaneFacade = airplaneFacade;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AirplaneDTO>> findAll() {
        List<AirplaneDTO> airplanes = airplaneFacade.findAll();
        return ResponseEntity.status(200).body(airplanes);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<AirplaneDTO> findById(@PathVariable Long id) {
        AirplaneDTO searchedAirplane = airplaneFacade.findById(id);
        return ResponseEntity.status(200).body(searchedAirplane);
    }


    @RequestMapping(value = "/findWithBiggerOrEqualCapacity", method = RequestMethod.GET)
    public ResponseEntity<List<AirplaneDTO>> findWithBiggerOrEqualCapacity(@RequestParam Integer capacity) {
        List<AirplaneDTO> airplanes = airplaneFacade.findWithBiggerOrEqualCapacity(capacity);
        return ResponseEntity.status(200).body(airplanes);
    }

    @RequestMapping(value = "/findWithLowerOrEqualCapacity", method = RequestMethod.GET)
    public ResponseEntity<List<AirplaneDTO>> findWithLowerOrEqualCapacity(@RequestParam Integer capacity) {
        List<AirplaneDTO> airplanes = airplaneFacade.findWithLowerOrEqualCapacity(capacity);
        return ResponseEntity.status(200).body(airplanes);
    }

    @RequestMapping(value = "/findByType", method = RequestMethod.GET)
    public ResponseEntity<List<AirplaneDTO>> findByType(@RequestParam String type) {

        try {
            AirplaneType airplaneType = AirplaneType.valueOf(type.toUpperCase(Locale.ROOT));
            List<AirplaneDTO> airplanes = airplaneFacade.findByType(airplaneType);
            return ResponseEntity.status(200).body(airplanes);
        } catch (IllegalArgumentException e) {
            throw new AirportManagerException("Unknown type of airplane");
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createAirplane(@RequestHeader("Authorization") String header, @Valid @RequestBody AirplaneCreateDTO dto) throws AuthenticationException {
        this.authenticate(header, UserRole.AIRPORT_MANAGER);
        airplaneFacade.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateAirplane(@RequestHeader("Authorization") String header, @PathVariable Long id, @Valid @RequestBody AirplaneDTO dto) throws AuthenticationException {
        this.authenticate(header, UserRole.AIRPORT_MANAGER);
        airplaneFacade.update(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAirplane(@RequestHeader("Authorization") String header, @PathVariable Long id) throws AuthenticationException {
        this.authenticate(header, UserRole.AIRPORT_MANAGER);
        airplaneFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}