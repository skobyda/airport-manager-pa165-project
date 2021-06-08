package cz.fi.muni.pa165.project.controllers;

import cz.fi.muni.pa165.project.dto.FlightCreateDTO;
import cz.fi.muni.pa165.project.dto.FlightDTO;
import cz.fi.muni.pa165.project.dto.FlightSimpleDTO;
import cz.fi.muni.pa165.project.enums.UserRole;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import cz.fi.muni.pa165.project.facade.FlightFacade;
import cz.fi.muni.pa165.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author Jozef Vanický
 **/

@CrossOrigin
@RestController
@RequestMapping("/rest/flights")
public class FlightController extends AbstractController {

    private final FlightFacade flightFacade;

    @Autowired
    public FlightController(FlightFacade flightFacade, UserService userService) {
        super(userService);
        this.flightFacade = flightFacade;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<FlightDTO>> findAll() {
        List<FlightDTO> flights = flightFacade.findAll();
        return ResponseEntity.status(200).body(flights);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<FlightDTO> findById(@PathVariable Long id) {
        FlightDTO flight = flightFacade.findById(id);
        return ResponseEntity.status(200).body(flight);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> create(@RequestHeader("Authorization") String header, @Valid @RequestBody FlightCreateDTO flightCreateDTO) throws AirportManagerException, AuthenticationException {
        this.authenticate(header, UserRole.FLIGHT_MANAGER);
        flightFacade.create(flightCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@RequestHeader("Authorization") String header, @PathVariable Long id, @Valid @RequestBody FlightSimpleDTO flightSimpleDTO) throws AirportManagerException, AuthenticationException {
        this.authenticate(header, UserRole.FLIGHT_MANAGER);
        flightFacade.update(flightSimpleDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String header, @PathVariable Long id) throws AuthenticationException {
        this.authenticate(header, UserRole.FLIGHT_MANAGER);
        flightFacade.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/{flightId}/add-steward/{stewardId}", method = RequestMethod.POST)
    public ResponseEntity<Void> addSteward(@RequestHeader("Authorization") String header, @PathVariable Long flightId, @PathVariable Long stewardId) throws AuthenticationException {
        this.authenticate(header, UserRole.FLIGHT_MANAGER);
        flightFacade.addSteward(stewardId, flightId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{flightId}/delete-steward/{stewardId}", method = RequestMethod.POST)
    public ResponseEntity<Void> removeSteward(@RequestHeader("Authorization") String header, @PathVariable Long flightId, @PathVariable Long stewardId) throws AuthenticationException {
        this.authenticate(header, UserRole.FLIGHT_MANAGER);
        flightFacade.removeSteward(stewardId, flightId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public ResponseEntity<List<FlightDTO>> filterFlights(
            @RequestParam(required = false, value = "dateFrom") String dateFrom,
            @RequestParam(required = false, value = "dateTo") String dateTo,
            @RequestParam(required = false, value = "originAirportId") String originAirportId,
            @RequestParam(required = false, value = "destinationAirportId") String destinationAirportId) {
        LocalDateTime dateF = !Objects.equals(dateFrom, "") ? LocalDateTime.parse(dateFrom) : null;
        LocalDateTime dateT = !Objects.equals(dateTo, "") ? LocalDateTime.parse(dateTo) : null;
        Long origAirportId = !Objects.equals(originAirportId, "") ? Long.parseLong(originAirportId) : null;
        Long destAirportId = !Objects.equals(destinationAirportId, "") ? Long.parseLong(destinationAirportId) : null;
        List<FlightDTO> filteredFlights = flightFacade.getFilteredList(dateF, dateT, origAirportId, destAirportId);
        return ResponseEntity.status(200).body(filteredFlights);
    }

    @RequestMapping(value = "/{airportId}/arrivals/{limit}", method = RequestMethod.GET)
    public ResponseEntity<List<FlightDTO>> getFlightsOrderedByArrival(@PathVariable Long airportId, @PathVariable int limit) {
        List<FlightDTO> flights = flightFacade.getFlightsOrderedByArrival(limit, airportId);
        return ResponseEntity.status(200).body(flights);
    }

    @RequestMapping(value = "/{airportId}/departures/{limit}", method = RequestMethod.GET)
    public ResponseEntity<List<FlightDTO>> getFlightsOrderedByDeparture(@PathVariable Long airportId, @PathVariable int limit) {
        List<FlightDTO> flights = flightFacade.getFlightsOrderedByDeparture(limit, airportId);
        return ResponseEntity.status(200).body(flights);
    }

}
