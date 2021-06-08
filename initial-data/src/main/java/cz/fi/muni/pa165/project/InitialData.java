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

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.List;

/**
 * @author Petr Hendrych, Simon Kobyda, Jozef Vanicky, Michal Zelenak
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
        Steward steward1 = createSteward("UK", "123456789", "Jane", "Doe");
        Steward steward2 = createSteward("SW", "987654321", "Olga", "Christen");
        Steward steward3 = createSteward("SW", "986545321", "Olga", "Mitchel");
        Steward steward4 = createSteward("AU", "987654354", "Jan", "Christ");
        Steward steward5 = createSteward("SK", "484213521", "Charles", "Dickerson");
        Steward steward6 = createSteward("CZ", "484213536", "Monique", "Lopez");
        Steward steward7 = createSteward("PT", "539817275", "Cleve", "Springford");
        Steward steward8 = createSteward("IR", "427807135", "Nathalie", "Debold");
        Steward steward9 = createSteward("CN", "144473949", "Lurline", "Shury");
        Steward steward10 = createSteward("CN", "101527793", "Zebulen", "Lorens");
        Steward steward11 = createSteward("ID", "784191483", "Dina", "Minero");
        Steward steward12 = createSteward("UY", "683326709", "Myer", "Dury");
        Steward steward13 = createSteward("CZ", "595781411", "Barbra", "Breede");
        Steward steward14 = createSteward("TZ", "453582045", "Avictor", "Hadland");
        Steward steward15 = createSteward("PE", "323229225", "Sholom", "Milbourne");
        Steward steward16 = createSteward("GM", "264527740", "Cornie", "Grason");

        Airport airport1 = createAirport("AT", "Vienna International Airport", "Schwechat");
        Airport airport2 = createAirport("CZ", "Letiske Turany", "Brno");
        Airport airport3 = createAirport("SK", "Letisko Milana Rastislava Stefanika", "Bratislava");
        Airport airport4 = createAirport("US", "Kennedy Airport", "New York");

        Airplane airplane1 = createAirplane("Boeing 747", 200, AirplaneType.JET);
        Airplane airplane2 = createAirplane("Boeing 737", 100, AirplaneType.JET);
        Airplane airplane3 = createAirplane("Boeing 777X", 280, AirplaneType.JET);
        Airplane airplane4 = createAirplane("CESSNA 172 Skyhawk", 4, AirplaneType.PISTON);
        Airplane airplane5 = createAirplane("Antonov An-140", 20, AirplaneType.TURBOPROP);
        Airplane airplane6 = createAirplane("Kawasaki P-2J", 5, AirplaneType.TURBOPROP);
        Airplane airplane7 = createAirplane("Saab 340", 34, AirplaneType.COMMUTER);
        Airplane airplane8 = createAirplane("Boeing 737MAX", 150, AirplaneType.JET);
        Airplane airplane9 = createAirplane("Piper M350", 8, AirplaneType.PISTON);
        createAirplane("Airbus A400M", 5, AirplaneType.TURBOPROP);
        createAirplane("Antonov An-140", 20, AirplaneType.TURBOPROP);
        createAirplane("ATR 72", 80, AirplaneType.TURBOPROP);
        createAirplane("Let L-420", 20, AirplaneType.TURBOPROP);
        createAirplane("Saab 2000", 50, AirplaneType.COMMUTER);
        createAirplane("Jetstream 41", 29, AirplaneType.COMMUTER);
        createAirplane("Fokker 100", 80, AirplaneType.COMMUTER);

        createFlight(LocalDateTime.of(2020, Month.JANUARY, 1, 18, 45), LocalDateTime.of(2020, Month.JANUARY, 1, 20, 30), airport1.getId(), airport2.getId(), airplane1.getId(), "NVL185", List.of(steward1, steward2, steward3));
        createFlight(LocalDateTime.of(2020, Month.JANUARY, 1, 0, 0), LocalDateTime.of(2020, Month.MARCH, 1, 0, 0), airport2.getId(), airport3.getId(), airplane4.getId(), "NVL180", List.of(steward5, steward6, steward7));
        createFlight(LocalDateTime.of(2020, Month.JANUARY, 3, 0, 0), LocalDateTime.of(2020, Month.MARCH, 4, 0, 0), airport3.getId(), airport1.getId(), airplane9.getId(), "AVL185", List.of(steward8, steward9, steward10));
        createFlight(LocalDateTime.of(2020, Month.JANUARY, 2, 10, 30), LocalDateTime.of(2020, Month.JANUARY, 2, 12, 0), airport1.getId(), airport2.getId(), airplane2.getId(), "AVL186", List.of(steward3, steward4, steward5));
        createFlight(LocalDateTime.of(2020, Month.FEBRUARY, 6, 23, 0), LocalDateTime.of(2020, Month.FEBRUARY, 7, 1, 0), airport2.getId(), airport1.getId(), airplane5.getId(), "CKL125", List.of(steward6, steward7));
        createFlight(LocalDateTime.of(2020, Month.AUGUST, 12, 7, 9), LocalDateTime.of(2020, Month.FEBRUARY, 12, 14, 0), airport3.getId(), airport4.getId(), airplane9.getId(), "AVL225", List.of(steward2, steward9, steward4, steward8));
        createFlight(LocalDateTime.of(2020, Month.DECEMBER, 29, 18, 10), LocalDateTime.of(2020, Month.DECEMBER, 29, 12, 12), airport1.getId(), airport2.getId(), airplane6.getId(), "MLL229", List.of(steward3, steward4, steward6));
        createFlight(LocalDateTime.of(2020, Month.JANUARY, 2, 20, 0), LocalDateTime.of(2020, Month.JANUARY, 3, 1, 0), airport1.getId(), airport2.getId(), airplane8.getId(), "AVS921", List.of(steward3, steward4, steward5));
        createFlight(LocalDateTime.of(2020, Month.MARCH, 18, 16, 15), LocalDateTime.of(2020, Month.MARCH, 18, 17, 15), airport4.getId(), airport1.getId(), airplane3.getId(), "AVA001", List.of(steward13, steward14, steward15, steward16));
        createFlight(LocalDateTime.of(2020, Month.DECEMBER, 20, 7, 0), LocalDateTime.of(2020, Month.DECEMBER, 20, 9, 0), airport2.getId(), airport4.getId(), airplane7.getId(), "BLS239", List.of(steward9, steward4, steward1, steward11, steward12));

        createUser("admin@admin.com", "Administrator", "KSC Airport Runway n1", "Testujuci", UserRole.AIRPORT_MANAGER, "admin123");
        createUser("test@test.com", "Tester", "KSC Airport Runway n2", "Testujuci", UserRole.FLIGHT_MANAGER, "test123");
        createUser("pepa@example.com", "Pepa", "My street 21", "Chabro", UserRole.AIRPORT_MANAGER, "pepa");
        createUser("john@example.com", "John", "Brooklyn 1", "Doe", UserRole.AIRPORT_MANAGER, "password1");
        createUser("jane@example.com", "Jane", "Top down 42", "Doe", UserRole.FLIGHT_MANAGER, "password2");
    }

    private Airplane createAirplane(String name, Integer capacity, AirplaneType type) {
        Airplane airplane = new Airplane();
        airplane.setName(name);
        airplane.setCapacity(capacity);
        airplane.setType(type);
        airplaneService.create(airplane);
        return airplane;
    }

    private Steward createSteward(String countryCode, String passportNumber, String firstName, String lastName) {
        Steward steward = new Steward();
        steward.setCountryCode(countryCode);
        steward.setPassportNumber(passportNumber);
        steward.setFirstName(firstName);
        steward.setLastName(lastName);
        stewardService.create(steward);
        return steward;
    }

    private Airport createAirport(String country, String name, String city) {
        Airport airport = new Airport();
        airport.setCountry(country);
        airport.setCity(city);
        airport.setName(name);
        airportService.create(airport);
        return airport;
    }


    private void createFlight(LocalDateTime arrival, LocalDateTime departure, Long originAirportId, Long destinationAirportId, Long airplaneId, String flightCode, List<Steward> stewards) throws Exception {
        Flight flight = new Flight();
        flight.setArrival(arrival);
        flight.setDeparture(departure);
        flight.setOriginAirport(airportService.findById(originAirportId));
        flight.setDestinationAirport(airportService.findById(destinationAirportId));
        flight.setAirplane(airplaneService.findById(airplaneId));
        flight.setFlightCode(flightCode);
        for (Steward steward : stewards) {
            flight.addSteward(steward);
        }
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
