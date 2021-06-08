package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.FlightCreateDTO;
import cz.fi.muni.pa165.project.dto.FlightDTO;
import cz.fi.muni.pa165.project.dto.FlightSimpleDTO;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.entity.Steward;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import cz.fi.muni.pa165.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

/**
 * @author Michal Zelenák
 **/

@Service
@Transactional
public class FlightFacadeImpl implements FlightFacade {

    private final BeanMappingService beanMappingService;
    private final FlightService flightService;
    private final AirportService airportService;
    private final AirplaneService airplaneService;
    private final StewardService stewardService;

    @Autowired
    public FlightFacadeImpl(FlightService flightService,
                            AirportService airportService,
                            AirplaneService airplaneService,
                            StewardService stewardService,
                            BeanMappingService beanMappingService
    ) {
        this.beanMappingService = beanMappingService;
        this.flightService = flightService;
        this.airportService = airportService;
        this.airplaneService = airplaneService;
        this.stewardService = stewardService;
    }


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
        for (Long stewardID : flightCreateDTO.getStewardIds()) {
            stewards.add(stewardService.findById(stewardID));
        }
        Flight flight = flightService.create(mappedFlight);
        return flight.getId();
    }

    @Override
    public Long update(FlightSimpleDTO flightSimpleDTO) throws AirportManagerException {
        Flight mappedFlight = new Flight();
        mappedFlight.setFlightCode(flightSimpleDTO.getFlightCode());
        mappedFlight.setDeparture(flightSimpleDTO.getDeparture());
        mappedFlight.setArrival(flightSimpleDTO.getArrival());
        mappedFlight.setId(flightSimpleDTO.getId());
        mappedFlight.setAirplane(airplaneService.findById(flightSimpleDTO.getAirplaneId()));
        mappedFlight.setOriginAirport(airportService.findById(flightSimpleDTO.getOriginAirportId()));
        mappedFlight.setDestinationAirport(airportService.findById(flightSimpleDTO.getDestinationAirportId()));
        HashSet<Steward> stewards = new HashSet<>();
        for (Long stewardID : flightSimpleDTO.getStewardIds()) {
            stewards.add(stewardService.findById(stewardID));
        }
        mappedFlight.setStewards(stewards);
        Flight flight = flightService.update(mappedFlight);
        return flight.getId();
        }

    @Override
    public void delete(Long id) {
        flightService.delete(id);
    }

    @Override
    public List<FlightDTO> getFilteredList(LocalDateTime dateFrom, LocalDateTime dateTo, Long departureAirportId, Long arrivalAirportId) {
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
        return beanMappingService.mapTo(flightService.getFlightsOrderedByArrival(limit, airportId), FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getFlightsOrderedByDeparture(int limit, Long airportId) {
        return beanMappingService.mapTo(flightService.getFlightsOrderedByDeparture(limit, airportId), FlightDTO.class);
    }

}
