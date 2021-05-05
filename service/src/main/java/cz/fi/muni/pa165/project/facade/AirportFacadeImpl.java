package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.AirportCreateDTO;
import cz.fi.muni.pa165.project.dto.AirportDTO;
import cz.fi.muni.pa165.project.dto.FlightDTO;
import cz.fi.muni.pa165.project.entity.Airport;
import cz.fi.muni.pa165.project.service.AirportService;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.service.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 @author Simon Kobyda
 @created 27/04/2021
 @project airport-manager
 **/
@Service
@Transactional
public class AirportFacadeImpl implements AirportFacade {

    @Autowired
    private AirportService airportService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void create(AirportCreateDTO airport) {
        Airport mappedAirport = beanMappingService.mapTo(airport, Airport.class);
        airportService.create(mappedAirport);
    }

    @Override
    public void update(AirportDTO airport) {
        Airport mappedAirport = beanMappingService.mapTo(airport, Airport.class);
        airportService.update(mappedAirport);
    }

    @Override
    public void delete(Long id) {
        airportService.delete(id);
    }

    @Override
    public List<AirportDTO> findAll() {
        return beanMappingService.mapTo(airportService.findAll(), AirportDTO.class);
    }

    @Override
    public List<AirportDTO> findByName(String name) {
        return beanMappingService.mapTo(airportService.findByName(name), AirportDTO.class);
    }

    @Override
    public AirportDTO findById(Long id) {
        return beanMappingService.mapTo(airportService.findById(id), AirportDTO.class);
    }

    @Override
    public List<AirportDTO> findByCountry(String country) {
        return beanMappingService.mapTo(airportService.findByCountry(country), AirportDTO.class);
    }

    @Override
    public List<AirportDTO> findByCity(String city) {
        return beanMappingService.mapTo(airportService.findByCity(city), AirportDTO.class);
    }

    @Override
    public List<FlightDTO> getDepartureFlights(Long airportId) {
        List<Flight> flights = airportService.getDepartureFlights(airportId);
        return beanMappingService.mapTo(flights, FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getArrivalFlights(Long airportId) {
        List<Flight> flights = airportService.getArrivalFlights(airportId);
        return beanMappingService.mapTo(flights, FlightDTO.class);
    }
}
