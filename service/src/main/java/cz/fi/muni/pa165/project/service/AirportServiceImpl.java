package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.dao.AirportDao;
import cz.fi.muni.pa165.project.entity.Airport;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon Kobyda
 **/

@Service
public class AirportServiceImpl implements AirportService {

    private final AirportDao airportDao;

    @Autowired
    public AirportServiceImpl(AirportDao airportDao) {
        this.airportDao = airportDao;
    }

    @Override
    public void create(Airport airport) {
        try {
            airportDao.create(airport);
        } catch (IllegalArgumentException e) {
            throw new AirportManagerException("Entity is not in a correct format or already exists");
        }
    }

    @Override
    public void update(Airport airport) {
        try {
            airportDao.update(airport);
        } catch (IllegalArgumentException e) {
            throw new AirportManagerException("Airport with this ID does not exists");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            airportDao.delete(airportDao.findById(id));
        } catch (IllegalArgumentException e) {
            throw new AirportManagerException("Airport with this ID does not exists in system");
        }
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
