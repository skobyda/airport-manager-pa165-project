package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.dao.AirplaneDao;
import cz.fi.muni.pa165.project.dao.FlightDao;
import cz.fi.muni.pa165.project.dao.StewardDao;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.entity.Steward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            if ((dateFrom == null || dateFrom.isBefore(flight.getDeparture()))
                    && (dateTo == null || dateTo.isAfter(flight.getArrival()))
                    && (departureAirportId == null || flight.getDestinationAirport().getId().equals(departureAirportId))
                    && (arrivalAirportId == null || flight.getOriginAirport().getId().equals(arrivalAirportId))) {
                filteredFlights.add(flight);
            }
        }
        return filteredFlights;
    }

    @Override
    public Flight create(Flight flight) throws Exception {
        checkPlaneAvailability(flight.getAirplane(), flight.getDeparture(), flight.getArrival(), flight.getFlightCode());
        for (Steward steward : flight.getStewards()) {
            checkStewardAvailability(steward, flight.getDeparture(), flight.getArrival(), flight.getFlightCode());
        }
        flightDao.create(flight);
        return flightDao.findById(flight.getId());
    }

    @Override
    public Flight update(Flight mappedFlight) throws Exception {
        Flight flightExisting = flightDao.findById(mappedFlight.getId());
        if ((mappedFlight.getDestinationAirport().equals(mappedFlight.getOriginAirport())
                || flightExisting.getDestinationAirport().equals(mappedFlight.getOriginAirport())
                || flightExisting.getOriginAirport().equals(mappedFlight.getDestinationAirport()))
                || (mappedFlight.getOriginAirport() == flightExisting.getDestinationAirport()
                && mappedFlight.getDestinationAirport() == flightExisting.getOriginAirport())) {

            throw new Exception("Origin airport and destination Airport cannot be same");
        }
        checkPlaneAvailability(mappedFlight.getAirplane(), mappedFlight.getDeparture(), mappedFlight.getArrival(), mappedFlight.getFlightCode());
        for (Steward steward : mappedFlight.getStewards()) {
            checkStewardAvailability(steward, mappedFlight.getDeparture(), mappedFlight.getArrival(), mappedFlight.getFlightCode());
        }
        flightDao.update(mappedFlight);
        return flightDao.findById(mappedFlight.getId());
    }

    @Override
    public void delete(Long id) {
        Flight flightToDelete = findById(id);
        flightDao.delete(flightToDelete);
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

    private void checkStewardAvailability(Steward steward, LocalDate dateFrom, LocalDate dateTo, String flightCode) throws Exception {
        for (Flight flight : stewardDao.findById(steward.getId()).getFlights()) {
            if (flight.getFlightCode().equals(flightCode)) {
                continue;
            }
            if (!(flight.getDeparture().isAfter(dateTo) || flight.getArrival().isBefore(dateFrom))) {
                throw new Exception("Steward is already on another flight at that time");
            }
        }
    }

    private void checkPlaneAvailability(Airplane airplane, LocalDate dateFrom, LocalDate dateTo, String flightCode) throws Exception {
        for (Flight flight : airplaneDao.findById(airplane.getId()).getFlights()) {
            if (flight.getFlightCode().equals(flightCode)) {
                continue;
            }
            if (!(flight.getDeparture().isAfter(dateTo) || flight.getArrival().isBefore(dateFrom))) {
                throw new Exception("Airplane is already on another flight at that time");
            }
        }
    }
}
