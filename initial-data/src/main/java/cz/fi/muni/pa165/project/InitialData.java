package cz.fi.muni.pa165.project;

import cz.fi.muni.pa165.project.entity.*;
import cz.fi.muni.pa165.project.enums.AirplaneType;
import cz.fi.muni.pa165.project.enums.UserRole;
import cz.fi.muni.pa165.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;

/**
 * @author Petr Hendrych
 * @created 26.05.2021
 * @project airport-manager
 **/
@Component
public class InitialData implements ApplicationRunner {

    private final StewardService stewardService;
    private final AirportService airportService;
    private final AirplaneService airplaneService;
    private final FlightService flightService;
    private final UserService userService;

    @Autowired
    public InitialData(StewardService stewardService, AirportService airportService, AirplaneService airplaneService, FlightService flightService, UserService userService) {
        this.stewardService = stewardService;
        this.airportService = airportService;
        this.airplaneService = airplaneService;
        this.flightService = flightService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        loadData();
    }

    private void loadData() throws Exception {
        createSteward("UK", "123456789", "Jane", "Doe");
        createSteward("SW", "987654321", "Olga", "Christen");
        createSteward("SW", "986545321", "Olga", "Mitchel");
        createSteward("AU", "987654354", "Jan", "Christ");
        createSteward("SK", "484213521", "Charles", "Dickerson");
        createSteward("CZ", "484213536", "Monique", "Lopez");

        createAirport("AT", "Vienna International Airport", "Schwechat");
        createAirport("CZ", "Letiske Turany", "Brno");
        createAirport("SK", "Letisko Milana Rastislava Stefanika", "Bratislava");
        createAirport("US", "Kennedy Airport", "New York");

        createAirplane("dvojplosnik turbo 3000", 2, AirplaneType.PISTON);
        createAirplane("trojplosnik turbo 3000", 5, AirplaneType.PISTON);
        createAirplane("stvorplosnik turbo 3000", 200, AirplaneType.COMMUTER);

        createFlight(LocalDate.of(2020, Month.JANUARY, 1), LocalDate.of(2020, Month.MARCH, 1), 1L, 2L, 1L, "NVL185", 1L, 2L, 3L);
        createFlight(LocalDate.of(2020, Month.JANUARY, 3), LocalDate.of(2020, Month.MARCH, 4), 1L, 4L, 2L, "AVL185", 3L, 4L, 5L);

        createUser("pepa@example.com", "Pepa", "My street 21", "Chabro", UserRole.AIRPORT_MANAGER, "pepa");
        createUser("john@example.com", "John", "Brooklyn 1", "Doe", UserRole.AIRPORT_MANAGER, "password1");
        createUser("jane@example.com", "Jane", "Top down 42", "Doe", UserRole.AIRPORT_MANAGER, "password2");
    }

    private void createAirplane(String name, Integer capacity, AirplaneType type) {
        Airplane airplane = new Airplane();
        airplane.setName(name);
        airplane.setCapacity(capacity);
        airplane.setType(type);
        airplaneService.create(airplane);
    }

    private void createSteward(String countryCode, String passportNumber, String firstName, String lastName) {
        Steward steward = new Steward();
        steward.setCountryCode(countryCode);
        steward.setPassportNumber(passportNumber);
        steward.setFirstName(firstName);
        steward.setLastName(lastName);
        stewardService.create(steward);
    }

    private void createAirport(String country, String name, String city) {
        Airport airport = new Airport();
        airport.setCountry(country);
        airport.setCity(city);
        airport.setName(name);
        airportService.create(airport);
    }

    private void createFlight(LocalDate arrival, LocalDate departure, Long originAirportId, Long destinationAirportId, Long airplaneId, String flightCode, Long s1Id, Long s2Id, Long s3Id) throws Exception {
        Flight flight = new Flight();
        flight.setArrival(arrival);
        flight.setDeparture(departure);
        flight.setOriginAirport(airportService.findById(originAirportId));
        flight.setDestinationAirport(airportService.findById(destinationAirportId));
        flight.setAirplane(airplaneService.findById(airplaneId));
        flight.setFlightCode(flightCode);
        flight.addSteward(stewardService.findById(s1Id));
        flight.addSteward(stewardService.findById(s2Id));
        flight.addSteward(stewardService.findById(s3Id));
        flightService.create(flight);
    }

    private void createUser(String email, String name, String address, String sureName, UserRole role, String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setAddress(address);
        user.setSurname(sureName);
        user.setJoinedDate(new Date());
        user.setRole(role);
        userService.registerUser(user, password);
    }
}
