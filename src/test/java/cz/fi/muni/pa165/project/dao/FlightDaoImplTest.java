package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.AirportManagerApplication;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.entity.Airport;
import cz.fi.muni.pa165.project.entity.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Petr Hendrych
 * @created 10.04.2021
 * @project airport-manager
 **/

@SpringBootTest(classes = AirportManagerApplication.class)
@Transactional
class FlightDaoImplTest {

    @Autowired
    private FlightDaoImpl flightDao;

    @Autowired
    private AirportDaoImpl airportDao;

    @Autowired
    private AirplaneDaoImpl airplaneDao;

    private Flight flightDestinationPrague;
    private Flight flightDestinationVienna;
    private Flight flightDestinationDubai;

    private Airport Dubai;
    private Airport Vienna;
    private Airport Prague;

    private Airplane Boeing;
    private Airplane Airbus;

    private final String flightCodeToPrague = "NVL185";
    private final String flightCodeToVienna = "PDL834";
    private final String flightCodeToDubai = "KWL2010";

    @BeforeEach
    void setUp() {
        Dubai = createAirport("Dubai");
        Vienna = createAirport("Vienna");
        Prague = createAirport("Prague");

        airportDao.create(Dubai);
        airportDao.create(Vienna);
        airportDao.create(Prague);

        Airbus = createAirplane("Airbus");
        airplaneDao.create(Airbus);

        Boeing = createAirplane("Boeing");
        airplaneDao.create(Boeing);

        createFlightsToEurope();
        flightDao.create(flightDestinationPrague);
        flightDao.create(flightDestinationVienna);
    }

    @Test
    void create() {
        flightDestinationDubai = createFlight(
                LocalDate.of(2020, Month.JANUARY, 1),
                LocalDate.of(2020, Month.MARCH, 1),
                Dubai,
                Vienna,
                Airbus,
                flightCodeToDubai);
        flightDao.create(flightDestinationDubai);

        assertEquals(flightDestinationDubai, flightDao.findByFlightCode(flightCodeToDubai));
    }

    @Test
    void findAll() {
        List<Flight> allFlights = flightDao.findAll();
        assertEquals(2, allFlights.size());

        flightDestinationDubai = createFlight(
                LocalDate.of(2019, Month.APRIL, 23),
                LocalDate.of(2019, Month.APRIL, 23),
                Vienna,
                Dubai,
                Boeing,
                flightCodeToDubai);
        flightDao.create(flightDestinationDubai);

        allFlights = flightDao.findAll();
        assertEquals(3, allFlights.size());
    }

    @Test
    void findById() {
        Flight found = flightDao.findById(flightDestinationPrague.getId());
        assertEquals(found.getFlightCode(), flightCodeToPrague);
        assertEquals(found.getDestinationAirport(), Prague);
    }

    @Test
    void remove() {
        assertNotNull(flightDao.findById(flightDestinationPrague.getId()));
        flightDao.remove(flightDestinationPrague);
        assertNull(flightDao.findById(flightDestinationPrague.getId()));
    }

    @Test
    void update() {
        Flight found = flightDao.findById(flightDestinationPrague.getId());
        assertEquals(found.getFlightCode(), flightCodeToPrague);

        found.setFlightCode("QWE194");
        flightDao.update(found);

        assertEquals(flightDao.findById(found.getId()).getFlightCode(), "QWE194");
    }

    @Test
    void findByDeparture() {
        List<Flight> flightsDeparture = flightDao.findByDeparture(LocalDate.of(2020, Month.JANUARY, 1));
        assertEquals(2, flightsDeparture.size());

        List<Flight> flights = flightDao.findByDeparture(LocalDate.of(1987, Month.JULY, 23));
        assertEquals(0, flights.size());
    }

    @Test
    void findByArrival() {
        List<Flight> flightsArrival = flightDao.findByArrival(LocalDate.of(2020, Month.MARCH, 4));
        assertEquals(1, flightsArrival.size());
        assertEquals(flightsArrival.get(0).getId(), flightDestinationPrague.getId());

        List<Flight> flights = flightDao.findByArrival(LocalDate.of(1999, Month.NOVEMBER, 16));
        assertEquals(0, flights.size());
    }

    @Test
    void findByFlightCode() {
        assertNotNull(flightDao.findByFlightCode(flightCodeToPrague));
    }

    private void createFlightsToEurope() {
        flightDestinationPrague = createFlight(
                LocalDate.of(2020, Month.JANUARY, 1),
                LocalDate.of(2020, Month.MARCH, 4),
                Prague,
                Vienna,
                Airbus,
                flightCodeToPrague);
        flightDestinationVienna = createFlight(
                LocalDate.of(2020, Month.JANUARY, 1),
                LocalDate.of(2020, Month.FEBRUARY, 4),
                Vienna,
                Prague,
                Boeing,
                flightCodeToVienna);
    }

    private Flight createFlight(
            LocalDate departure,
            LocalDate arrival,
            Airport destination,
            Airport origin,
            Airplane airplane,
            String flightCode) {
        Flight flight = new Flight();
        flight.setDeparture(departure);
        flight.setArrival(arrival);
        flight.setDestinationAirport(destination);
        flight.setOriginAirport(origin);
        flight.setAirplane(airplane);
        flight.setFlightCode(flightCode);

        return flight;
    }

    private Airplane createAirplane(String name) {
        Airplane airplane = new Airplane();
        airplane.setName(name);

        return airplane;
    }

    private Airport createAirport(String city) {
        Airport airport = new Airport();
        airport.setCity(city);

        return airport;
    }
}
