package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.AirportManagerApplication;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.entity.Airport;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.entity.Steward;
import cz.fi.muni.pa165.project.enums.AirplaneType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michal Zelenák
 * @created 10.04.2021
 * @project airport-manager
 **/

@SpringBootTest(classes = AirportManagerApplication.class)
@Transactional
public class FlightRelationshipTest {
    private Airplane airplane1;
    private Airplane airplane2;
    private Flight flight1;
    private Flight flight2;
    private Airport airport1;
    private Airport airport2;
    private Steward steward1;
    private Steward steward2;

    @Autowired
    private FlightDaoImpl flightDao = new FlightDaoImpl();

    @Autowired
    private AirportDaoImpl airportDao = new AirportDaoImpl();

    @Autowired
    private AirplaneDaoImpl airplaneDao = new AirplaneDaoImpl();

    @Autowired
    private StewardDaoImpl stewardDao = new StewardDaoImpl();

    @BeforeEach
    void setup(){

        airplane1 = createAirplane("RandomAirplaneName3", 100, AirplaneType.JET);
        airplane2 = createAirplane("RandomAirplaneName4", 892, AirplaneType.TURBOPROP);

        airport1 = createAirport("SAE","Dubai airport","Dubai");
        airport2 = createAirport("USA","New Your airport","New York");

        steward1 = createSteward("SVK","123","Anna","Novakova");
        steward2 = createSteward("CZK","124","Janka","Jandova");

        String flight1Code = "NVL185";
        flight1 = createFlight(LocalDate.of(2020, Month.MARCH, 1), LocalDate.of(2020, Month.MARCH, 1), flight1Code);

        String flight2Code = "PDL834";
        flight2 = createFlight(LocalDate.of(2020, Month.JANUARY, 2),LocalDate.of(2020, Month.JANUARY, 2), flight2Code);

    }

    @Test
    void flightAirplaneSimpleRelationship(){
        flight1.setAirplane(airplane1);
        flight1.setAirplane(airplane2);
        flightDao.update(flight1);

        Assertions.assertEquals(flight1.getAirplane(), airplane2);
        Assertions.assertEquals(1,airplane2.getFlights().size());
    }

    @Test
    void airplaneArrayOfFlights(){
        flight1.setAirplane(airplane2);
        flight2.setAirplane(airplane2);
        flightDao.update(flight1);
        flightDao.update(flight2);
        Assertions.assertEquals(2,airplane2.getFlights().size());
    }

    @Test
    void flightAirportSimpleRelationship(){
        flight1.setOriginAirport(airport1);
        flight1.setDestinationAirport(airport2);
        flightDao.update(flight1);
        Assertions.assertEquals(flight1.getOriginAirport(), airport1);
        Assertions.assertEquals(flight1.getDestinationAirport(), airport2);
        Assertions.assertEquals(1,airport1.getDepartureFlights().size());
        Assertions.assertEquals(1,airport2.getArrivalFlights().size());
    }

    @Test
    void flightAirportArraysRelationship(){
        flight1.setOriginAirport(airport1);
        flight1.setDestinationAirport(airport2);
        flight2.setOriginAirport(airport1);
        flight2.setDestinationAirport(airport2);
        flightDao.update(flight1);
        flightDao.update(flight2);
        Assertions.assertEquals(2,airport1.getDepartureFlights().size());
        Assertions.assertEquals(2,airport2.getArrivalFlights().size());
    }

    @Test
    void flightStewardSimpleRelationship(){
        flight1.addSteward(steward1);
        flight1.addSteward(steward2);
        flightDao.update(flight1);
        Assertions.assertTrue(steward1.getFlights().contains(flight1));
        Assertions.assertTrue(flight1.getStewards().contains(steward1));
        Assertions.assertTrue(steward1.getFlights().contains(flight1));
        Assertions.assertTrue(steward2.getFlights().contains(flight1));
    }

    @Test
    void setStewardsTest(){
        Set<Steward> stewards = new HashSet<>();
        stewards.add(steward1);
        stewards.add(steward2);
        flight2.setStewards(stewards);
        flight1.setStewards(stewards);
        flightDao.update(flight1);
        flightDao.update(flight2);
        Assertions.assertTrue(flight1.getStewards().contains(steward1) && flight1.getStewards().contains(steward2));
        Assertions.assertTrue(flight2.getStewards().contains(steward1) && flight2.getStewards().contains(steward2));
        Assertions.assertTrue(steward1.getFlights().contains(flight1) && steward2.getFlights().contains(flight1));
        Assertions.assertTrue(steward1.getFlights().contains(flight2) && steward2.getFlights().contains(flight2));
    }

    private Flight createFlight(
            LocalDate departure,
            LocalDate arrival,
            String flightCode) {
        Flight flight = new Flight();
        flight.setDeparture(departure);
        flight.setArrival(arrival);
        flight.setFlightCode(flightCode);
        flightDao.create(flight);
        return flight;
    }

    private Airplane createAirplane(String airplaneName, Integer capacity, AirplaneType airplaneType) {
        Airplane airplane = new Airplane();
        airplane.setName(airplaneName);
        airplane.setCapacity(capacity);
        airplane.setType(airplaneType);
        airplaneDao.create(airplane);
        return airplane;
    }

    private Airport createAirport(String country, String name, String city){
        Airport newAirport= new Airport();
        newAirport.setCity(city);
        newAirport.setCountry(country);
        newAirport.setName(name);
        airportDao.create(newAirport);
        return newAirport;
    }

    private Steward createSteward(
            String countryCode,
            String passportNumber,
            String firstName,
            String lastName) {
        Steward steward = new Steward();
        steward.setCountryCode(countryCode);
        steward.setPassportNumber(passportNumber);
        steward.setFirstName(firstName);
        steward.setLastName(lastName);
        stewardDao.create(steward);
        return steward;
    }

}
