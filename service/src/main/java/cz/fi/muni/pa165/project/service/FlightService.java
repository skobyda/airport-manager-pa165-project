package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.entity.Flight;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Michal Zelenák
 * @created 27.04.2021
 * @project airport-manager
 **/

@Service
public interface FlightService {

    /**
     * Method to find specific {@code Flight} entity by it's id.
     *
     * @param id - id of {@code Flight} entity
     * @return - specific {@code Flight} entity
     */
    Flight findById(Long id);

    /**
     * Method that returns list of all Flights.
     *
     * @return - list of {@code Flight} entities
     */
    List<Flight> findAll();

    /**
     * Method to find list of all {@code Flight} entities by departure date, arrival date, departure airport, arrival airport.
     *
     * @param dateFrom - date from which the filtered flights will be listed
     * @param dateTo - date to which the filtered flights will be listed
     * @param departureAirportId - Id of the departure airport for filtering the flights
     * @param arrivalAirportId - Id of the arrival airport for filtering the flights
     * @return - specific {@code Flight} entity
     */
    List<Flight> filterFlights(LocalDate dateFrom, LocalDate dateTo, Long departureAirportId, Long arrivalAirportId);

    /**
     * Method persist given entity.
     *
     * @param flight - Flight entity to be persisted
     */
    Flight create(Flight flight) throws Exception;

    /**
     * Method to update specific {@code Flight} entity parameters.
     *
     * @param flight - {@code Flight} entity to be updated
     */
    Flight update(Flight flight) throws Exception;

    /**
     * Removes {@code Flight} entity from database.
     *
     * @param id - id of {@code Flight} entity
     */
    void delete(Long id);

    /**
     * Add {@code Steward} into {@code Flight} entity.
     *
     * @param flightId - id of {@code Flight} entity
     * @param stewardId - id of {@code Steward} entity
     */
    void addSteward(Long stewardId, Long flightId);

    /**
     * Remove {@code Steward} from {@code Flight} entity.
     *
     * @param flightId - id of {@code Flight} entity
     * @param stewardId - id of {@code Steward} entity
     */
    void removeSteward(Long stewardId, Long flightId);
}
