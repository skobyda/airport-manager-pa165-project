package cz.fi.muni.pa165.project.controllers;


import cz.fi.muni.pa165.project.dto.AirportCreateDTO;
import cz.fi.muni.pa165.project.dto.AirportDTO;
import cz.fi.muni.pa165.project.dto.FlightDTO;
import cz.fi.muni.pa165.project.enums.UserRole;
import cz.fi.muni.pa165.project.facade.AirportFacade;
import cz.fi.muni.pa165.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Simon Kobyda
 **/
@CrossOrigin
@RestController
@RequestMapping("/rest/airports")
public class AirportController extends AbstractController {

    private final AirportFacade airportFacade;

    @Autowired
    public AirportController(AirportFacade airportFacade, UserService userService) {
        super(userService);
        this.airportFacade = airportFacade;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<AirportDTO>> findAll() {
        List<AirportDTO> airports = airportFacade.findAll();
        return ResponseEntity.status(200).body(airports);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<AirportDTO> findById(@PathVariable Long id) {
        AirportDTO court = airportFacade.findById(id);
        return ResponseEntity.status(200).body(court);
    }

    @RequestMapping(value = "/{id}/getArrivalFlights", method = RequestMethod.GET)
    public ResponseEntity<List<FlightDTO>> getArrivalFlights(@PathVariable Long id) {
        List<FlightDTO> flights = airportFacade.getArrivalFlights(id);
        return ResponseEntity.status(200).body(flights);
    }

    @RequestMapping(value = "/{id}/getDepartureFlights", method = RequestMethod.GET)
    public ResponseEntity<List<FlightDTO>> getDepartureFlights(@PathVariable Long id) {
        List<FlightDTO> flights = airportFacade.getDepartureFlights(id);
        return ResponseEntity.status(200).body(flights);
    }

    @RequestMapping(value = "/findByCity", method = RequestMethod.GET)
    public ResponseEntity<List<AirportDTO>> findByCity(@RequestParam String city) {
        List<AirportDTO> airports = airportFacade.findByCity(city);
        return ResponseEntity.status(200).body(airports);
    }

    @RequestMapping(value = "/findByCountry", method = RequestMethod.GET)
    public ResponseEntity<List<AirportDTO>> findByCountry(@RequestParam String country) {
        List<AirportDTO> airports = airportFacade.findByCountry(country);
        return ResponseEntity.status(200).body(airports);
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.GET)
    public ResponseEntity<List<AirportDTO>> findByName(@RequestParam String name) {
        List<AirportDTO> airports = airportFacade.findByName(name);
        return ResponseEntity.status(200).body(airports);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createAirport(@RequestHeader("Authorization") String header, @Valid @RequestBody AirportCreateDTO dto) throws AuthenticationException {
        this.authenticate(header, UserRole.AIRPORT_MANAGER);
        airportFacade.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateAirport(@RequestHeader("Authorization") String header, @Valid @PathVariable Long id, @Valid @RequestBody AirportDTO dto) throws AuthenticationException {
        this.authenticate(header, UserRole.AIRPORT_MANAGER);
        airportFacade.update(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAirport(@RequestHeader("Authorization") String header, @PathVariable Long id) throws AuthenticationException {
        this.authenticate(header, UserRole.AIRPORT_MANAGER);
        airportFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}