package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.dao.AirplaneDao;
import cz.fi.muni.pa165.project.dao.FlightDao;
import cz.fi.muni.pa165.project.dao.StewardDao;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.entity.Steward;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Michal Zelenák
 * @created 5.05.2021
 * @project airport-manager
 **/

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightDao flightDao;

    @Autowired
    private AirplaneDao airplaneDao;

    @Autowired
    private StewardDao stewardDao;

    @Override
    public Flight findById(Long id) {
        return flightDao.findById(id);
    }

    @Override
    public List<Flight> findAll() {
        return flightDao.findAll();
    }

    @Override
    public List<Flight> filterFlights(LocalDate dateFrom, LocalDate dateTo, Long departureAirportId, Long arrivalAirportId) {
        List<Flight> allFlights = findAll();
        List<Flight> filteredFlights = new ArrayList<>();
        for (Flight flight : allFlights) {
            if (checkDateFromNullOrBefore(dateFrom, flight)
                    && checkDateToNullOrAfter(dateTo, flight)
                    && checkDestinationAirportNullOrSame(departureAirportId, flight)
                    && checkOriginAirportNullOrSame(arrivalAirportId, flight)) {
                filteredFlights.add(flight);
            }
        }
        return filteredFlights;
    }

    private boolean checkOriginAirportNullOrSame(Long arrivalAirportId, Flight flight) {
        return arrivalAirportId == null || flight.getOriginAirport().getId().equals(arrivalAirportId);
    }

    private boolean checkDestinationAirportNullOrSame(Long departureAirportId, Flight flight) {
        return departureAirportId == null || flight.getDestinationAirport().getId().equals(departureAirportId);
    }

    private boolean checkDateToNullOrAfter(LocalDate dateTo, Flight flight) {
        return dateTo == null || dateTo.isAfter(flight.getArrival());
    }

    private boolean checkDateFromNullOrBefore(LocalDate dateFrom, Flight flight) {
        return dateFrom == null || dateFrom.isBefore(flight.getDeparture());
    }

    @Override
    public Flight create(Flight flight) throws AirportManagerException {
        checkPlaneAvailability(flight);
        checkStewardsAvailability(flight);
        try {
            flightDao.create(flight);
        } catch (EntityExistsException e) {
            throw new AirportManagerException("Flight Entity already exists");
        }
        return flightDao.findById(flight.getId());
    }

    @Override
    public Flight update(Flight mappedFlight) throws AirportManagerException {

        if (mappedFlight.getDestinationAirport().equals(mappedFlight.getOriginAirport())) {
            throw new AirportManagerException("Origin airport and destination Airport cannot be same");
        }
        checkPlaneAvailability(mappedFlight);
        checkStewardsAvailability(mappedFlight);
        try {
            flightDao.update(mappedFlight);
        } catch (IllegalArgumentException e) {
            throw new AirportManagerException("Flight entity does not exists or is detached");
        }
        return flightDao.findById(mappedFlight.getId());
    }

    @Override
    public void delete(Long id) {
        Flight flightToDelete = findById(id);
        try {
            flightDao.delete(flightToDelete);
        } catch (IllegalArgumentException e) {
            throw new AirportManagerException("Flight  entity does not exists or is detached");
        }
    }

    @Override
    public void addSteward(Long stewardId, Long flightId) {
        Flight flight = flightDao.findById(flightId);
        flight.addSteward(stewardDao.findById(stewardId));
    }

    @Override
    public void removeSteward(Long stewardId, Long flightId) {
        Flight flight = flightDao.findById(flightId);
        flight.deleteSteward(stewardDao.findById(stewardId));
    }

    private void checkStewardsAvailability(Flight flight) throws AirportManagerException {
        Set<Steward> stewards = flight.getStewards();
        for (Steward steward : stewards) {
            checkStewardAvailability(steward, flight);
        }
    }

    private void checkStewardAvailability(Steward steward, Flight flightWithSteward) throws AirportManagerException {
        LocalDate dateFrom = flightWithSteward.getDeparture();
        LocalDate dateTo = flightWithSteward.getArrival();
        String flightCode = flightWithSteward.getFlightCode();
        for (Flight flightWhereStewardWorks : stewardDao.findById(steward.getId()).getFlights()) {
            if (flightWhereStewardWorks.getFlightCode().equals(flightCode)) {
                continue;
            }
            if (!(flightWhereStewardWorks.getDeparture().isAfter(dateTo) || flightWhereStewardWorks.getArrival().isBefore(dateFrom))) {
                throw new AirportManagerException("Steward is already on another flight at that time");
            }
        }
    }

    private void checkPlaneAvailability(Flight flightToCheck) throws AirportManagerException {
        Airplane airplane = flightToCheck.getAirplane();
        LocalDate dateFrom = flightToCheck.getDeparture();
        LocalDate dateTo = flightToCheck.getArrival();
        String flightCode = flightToCheck.getFlightCode();
        for (Flight flight : airplaneDao.findById(airplane.getId()).getFlights()) {
            if (flight.getFlightCode().equals(flightCode)) {
                continue;
            }
            if (!(flight.getDeparture().isAfter(dateTo) || flight.getArrival().isBefore(dateFrom))) {
                throw new AirportManagerException("Airplane is already on another flight at that time");
            }
        }
    }

    public List<Flight> getFlightsOrderedByArrival(int limit, Long airportId) {
        return flightDao.getFlightsOrderedByArrival(limit, airportId);
    }

    public List<Flight> getFlightsOrderedByDeparture(int limit, Long airportId) {
        return flightDao.getFlightsOrderedByDeparture(limit, airportId);
    }
}
