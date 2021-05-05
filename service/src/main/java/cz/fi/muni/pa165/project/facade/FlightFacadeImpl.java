package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.service.AirportService;
import cz.fi.muni.pa165.project.dto.FlightCreateDTO;
import cz.fi.muni.pa165.project.dto.FlightDTO;
import cz.fi.muni.pa165.project.dto.StewardDTO;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.service.AirplaneService;
import cz.fi.muni.pa165.project.service.BeanMappingService;
import cz.fi.muni.pa165.project.service.FlightService;
import cz.fi.muni.pa165.project.service.StewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
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
    private FlightService flightService;

    @Autowired
    private AirportService airportService;

    @Autowired
    private AirplaneService airplaneService;

    @Autowired
    private StewardService stewardService;

    @Autowired
    private BeanMappingService beanMappingService;

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
    public Long create(FlightCreateDTO flightCreateDTO) throws Exception {
         Flight mappedFlight = mapFlightCreateDTOToFlight(flightCreateDTO);
        //Flight mappedFlight = beanMappingService.mapTo(flightCreateDTO, Flight.class);
       Flight flight = flightService.create(mappedFlight);
       return flight.getId();
    }

    @Override
    public Long update(FlightDTO flightDTO) throws Exception {
        Flight mappedFlight = mapFlightDTOtoFlight(flightDTO);
        //Flight mappedFlight = beanMappingService.mapTo(flightDTO, Flight.class);
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
        flightService.addSteward(stewardId,flightId);
    }

    @Override
    public void removeSteward(Long stewardId, Long flightId) {
        flightService.removeSteward(stewardId,flightId);
    }

    private Flight mapFlightDTOtoFlight(FlightDTO flightDTO) {
        Flight mappedFlight = new Flight();
        mappedFlight.setId(flightDTO.getId());
        mappedFlight.setFlightCode(flightDTO.getFlightCode());
        mappedFlight.setDeparture(flightDTO.getDeparture());
        mappedFlight.setArrival(flightDTO.getArrival());
        mappedFlight.setAirplane(airplaneService.findById(flightDTO.getAirplaneId()));
        mappedFlight.setOriginAirport(airportService.findById(flightDTO.getOriginAirportId()));
        mappedFlight.setDestinationAirport(airportService.findById(flightDTO.getDestinationAirportId()));
        for (StewardDTO stewardDTO : flightDTO.getStewards()) {
            mappedFlight.addSteward(stewardService.findById(stewardDTO.getId()));
        }
        return mappedFlight;
    }

    private Flight mapFlightCreateDTOToFlight(FlightCreateDTO flightCreateDTO) {
        Flight mappedFlight = new Flight();
        mappedFlight.setFlightCode(flightCreateDTO.getFlightCode());
        //System.out.println(flightCreateDTO.getStewardIds());
        mappedFlight.setDeparture(flightCreateDTO.getDeparture());
        mappedFlight.setArrival(flightCreateDTO.getArrival());
        mappedFlight.setAirplane(airplaneService.findById(flightCreateDTO.getAirplaneId()));
        mappedFlight.setOriginAirport(airportService.findById(flightCreateDTO.getOriginAirportId()));
        mappedFlight.setDestinationAirport(airportService.findById(flightCreateDTO.getDestinationAirportId()));
        for (Long stewardId : flightCreateDTO.getStewardIds()) {
            mappedFlight.addSteward(stewardService.findById(stewardId));
        }
        return mappedFlight;
    }
}
