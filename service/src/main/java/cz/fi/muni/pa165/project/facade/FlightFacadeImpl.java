package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.FlightCreateDTO;
import cz.fi.muni.pa165.project.dto.FlightDTO;
import cz.fi.muni.pa165.project.dto.StewardDTO;
import cz.fi.muni.pa165.project.dto.StewardFilterDTO;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.entity.Steward;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import cz.fi.muni.pa165.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

/**
 * @author Michal Zelenák
 * @created 5.05.2021
 * @project airport-manager
 **/

@Service
@Transactional
public class FlightFacadeImpl implements FlightFacade {

    @Autowired
    private BeanMappingService beanMappingService;

    @Autowired
    private FlightService flightService;

    @Autowired
    private AirportService airportService;

    @Autowired
    private AirplaneService airplaneService;

    @Autowired
    private StewardService stewardService;


    @Override
    public FlightDTO findById(Long id) {
        Flight flight = flightService.findById(id);
        return (flight != null) ? beanMappingService.mapTo(flight, FlightDTO.class) : null;
    }

    @Override
    public List<FlightDTO> findAll() {
        return beanMappingService.mapTo(flightService.findAll(), FlightDTO.class);
    }

    @Override
    public Long create(FlightCreateDTO flightCreateDTO) throws AirportManagerException {
        Flight mappedFlight = beanMappingService.mapTo(flightCreateDTO, Flight.class);
        mappedFlight.setAirplane(airplaneService.findById(flightCreateDTO.getAirplaneId()));
        mappedFlight.setOriginAirport(airportService.findById(flightCreateDTO.getOriginAirportId()));
        mappedFlight.setDestinationAirport(airportService.findById(flightCreateDTO.getDestinationAirportId()));
        HashSet<Steward> stewards = new HashSet();
        for(Long stewardID: flightCreateDTO.getStewardIds()){
            stewards.add(stewardService.findById(stewardID));
        }
        Flight flight = flightService.create(mappedFlight);
        return flight.getId();
    }

    @Override
    public Long update(FlightDTO flightDTO) throws AirportManagerException {
        Flight mappedFlight = beanMappingService.mapTo(flightDTO, Flight.class);
        Flight flight = flightService.update(mappedFlight);
        return flight.getId();
    }

    @Override
    public void delete(Long id) {
        flightService.delete(id);
    }

    @Override
    public List<FlightDTO> getFilteredList(LocalDate dateFrom, LocalDate dateTo, Long departureAirportId, Long arrivalAirportId) {
        return beanMappingService.mapTo(flightService.filterFlights(dateFrom, dateTo, departureAirportId, arrivalAirportId), FlightDTO.class);
    }

    @Override
    public void addSteward(Long stewardId, Long flightId) {
        flightService.addSteward(stewardId, flightId);
    }

    @Override
    public void removeSteward(Long stewardId, Long flightId) {
        flightService.removeSteward(stewardId, flightId);
    }

    @Override
    public List<FlightDTO> getFlightsOrderedByArrival(int limit, Long airportId) {
        return beanMappingService.mapTo(flightService.getFlightsOrderedByArrival(limit,airportId), FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getFlightsOrderedByDeparture(int limit, Long airportId) {
        return beanMappingService.mapTo(flightService.getFlightsOrderedByDeparture(limit,airportId), FlightDTO.class);
    }

}
