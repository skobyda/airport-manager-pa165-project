package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.dao.AirportDao;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.entity.Airport;
import cz.fi.muni.pa165.project.entity.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 @author Simon Kobyda
 @created 27/04/2021
 @project airport-manager
 **/
@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportDao airportDao;

    @Override
    public void create(Airport airport) {
        airportDao.create(airport);
    }

    @Override
    public void update(Airport airport) {
        airportDao.update(airport);
    }

    @Override
    public void delete(Long id) {
        airportDao.delete(airportDao.findById(id));
    }

    @Override
    public List<Airport> findAll() {
        return airportDao.findAll();
    }

    @Override
    public Airport findById(Long id) {
        return airportDao.findById(id);
    }

    @Override
    public List<Airport> findByName(String name) {
        return airportDao.findByName(name);
    }

    @Override
    public List<Airport> findByCountry(String country) {
        return airportDao.findByCountry(country);
    }

    @Override
    public List<Airport> findByCity(String city) {
        return airportDao.findByCity(city);
    }

    @Override
    public List<Flight> getDepartureFlights(Long airportId) {
        Airport airport = airportDao.findById(airportId);
        return new ArrayList<>(airport.getDepartureFlights());
    }

    @Override
    public List<Flight> getArrivalFlights(Long airportId) {
        Airport airport = airportDao.findById(airportId);
        return new ArrayList<>(airport.getArrivalFlights());
    }
}
