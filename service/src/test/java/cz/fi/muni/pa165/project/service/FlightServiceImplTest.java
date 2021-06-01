package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.ServiceTestsConfiguration;
import cz.fi.muni.pa165.project.dao.AirplaneDao;
import cz.fi.muni.pa165.project.dao.AirportDao;
import cz.fi.muni.pa165.project.dao.FlightDao;
import cz.fi.muni.pa165.project.dao.StewardDao;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.entity.Airport;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.entity.Steward;
import cz.fi.muni.pa165.project.enums.AirplaneType;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Michal Zelenák
 * @created 5.05.2021
 * @project airport-manager
 **/

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = ServiceTestsConfiguration.class)
class FlightServiceImplTest {

    Flight flight1;
    Flight flight2;

    Airport airport1;
    Airport airport2;

    Airplane airplane;

    Steward steward1;
    Steward steward2;

    @Autowired
    @InjectMocks
    FlightServiceImpl flightService;

    @MockBean
    FlightDao flightDao;

    @MockBean
    StewardDao stewardDao;

    @MockBean
    AirplaneDao airplaneDao;

    @MockBean
    AirportDao airportDao;

    @BeforeEach
    void setUp() {
        flight1 = createFlight(LocalDate.of(2020, Month.MARCH, 1), LocalDate.of(2020, Month.MARCH, 1), "NVL185", 1L);
        flight2 = createFlight(LocalDate.of(2020, Month.MARCH, 2), LocalDate.of(2020, Month.MARCH, 2), "CVL186", 2L);

        airport1 = createAirport("SAE", "Dubai airport", "Dubai", 1L);
        airport2 = createAirport("SAE", "New York airport", "NewYork", 2L);
        airplane = createAirplane("RandomAirplaneName3", 100, AirplaneType.JET, 1L);
        steward1 = createSteward("SVK", "123", "Anna", "Novakova", 1L);
        steward2 = createSteward("CZK", "124", "Janka", "Jandova", 2L);

        flight1.setAirplane(airplane);
        flight1.addSteward(steward1);
        flight1.addSteward(steward2);
        flight1.setOriginAirport(airport1);
        flight1.setDestinationAirport(airport2);

        flight2.setAirplane(airplane);
        flight2.addSteward(steward1);
        flight2.addSteward(steward2);
        flight2.setOriginAirport(airport2);
        flight2.setDestinationAirport(airport1);
    }

    @Test
    void findById() {
        when(flightDao.findById(1L)).thenReturn(flight1);
        assertEquals(flightService.findById(1L), flight1);
    }

    @Test
    void findAll() {
        when(flightDao.findAll()).thenReturn(List.of(flight1, flight2));
        assertEquals(flightService.findAll(), List.of(flight1, flight2));
    }

    @Test
    void filterFlightsByArrival() {
        Flight flightA = createFlight(LocalDate.of(2020, Month.OCTOBER, 3), LocalDate.of(2020, Month.OCTOBER, 3), "NVL185", 3L);
        Flight flightB = createFlight(LocalDate.of(2020, Month.OCTOBER, 4), LocalDate.of(2020, Month.OCTOBER, 4), "NVL186", 4L);
        Flight flightC = createFlight(LocalDate.of(2020, Month.OCTOBER, 5), LocalDate.of(2020, Month.OCTOBER, 5), "NVL187", 5L);
        flightA.setDestinationAirport(airport1);
        flightA.setOriginAirport(airport2);
        flightB.setDestinationAirport(airport2);
        flightB.setOriginAirport(airport1);
        flightC.setDestinationAirport(airport1);
        flightC.setOriginAirport(airport2);

        when(flightDao.findAll()).thenReturn(List.of(flightA, flightB, flightC));
        when(airportDao.findById(airport1.getId())).thenReturn(airport1);
        when(airportDao.findById(airport2.getId())).thenReturn(airport2);

        List<Flight> filteredFlights = flightService.filterFlights(LocalDate.of(2020, Month.OCTOBER, 4), null, null, null);
        assertEquals(List.of(flightC), filteredFlights);
    }

    @Test
    void filterFlightsByDestinationAirport() {
        Flight flightA = createFlight(LocalDate.of(2020, Month.OCTOBER, 3), LocalDate.of(2020, Month.OCTOBER, 3), "NVL185", 3L);
        Flight flightB = createFlight(LocalDate.of(2020, Month.OCTOBER, 4), LocalDate.of(2020, Month.OCTOBER, 4), "NVL186", 4L);
        Flight flightC = createFlight(LocalDate.of(2020, Month.OCTOBER, 5), LocalDate.of(2020, Month.OCTOBER, 5), "NVL187", 5L);
        flightA.setDestinationAirport(airport1);
        flightA.setOriginAirport(airport2);
        flightB.setDestinationAirport(airport2);
        flightB.setOriginAirport(airport1);
        flightC.setDestinationAirport(airport1);
        flightC.setOriginAirport(airport2);

        when(flightDao.findAll()).thenReturn(List.of(flightA, flightB, flightC));
        when(airportDao.findById(airport1.getId())).thenReturn(airport1);
        when(airportDao.findById(airport2.getId())).thenReturn(airport2);

        List<Flight> filteredFlights = flightService.filterFlights(null, null, airport1.getId(), null);
        assertEquals(List.of(flightA, flightC), filteredFlights);
    }

    @Test
    void filterFlightsByOriginAirport() {
        Flight flightA = createFlight(LocalDate.of(2020, Month.OCTOBER, 3), LocalDate.of(2020, Month.OCTOBER, 3), "NVL185", 3L);
        Flight flightB = createFlight(LocalDate.of(2020, Month.OCTOBER, 4), LocalDate.of(2020, Month.OCTOBER, 4), "NVL186", 4L);
        Flight flightC = createFlight(LocalDate.of(2020, Month.OCTOBER, 5), LocalDate.of(2020, Month.OCTOBER, 5), "NVL187", 5L);
        flightA.setDestinationAirport(airport1);
        flightA.setOriginAirport(airport2);
        flightB.setDestinationAirport(airport2);
        flightB.setOriginAirport(airport1);
        flightC.setDestinationAirport(airport1);
        flightC.setOriginAirport(airport2);

        when(flightDao.findAll()).thenReturn(List.of(flightA, flightB, flightC));
        when(airportDao.findById(airport1.getId())).thenReturn(airport1);
        when(airportDao.findById(airport2.getId())).thenReturn(airport2);

        List<Flight> filteredFlights = flightService.filterFlights(null, null, null, airport1.getId());
        assertEquals(List.of(flightB), filteredFlights);
    }

    @Test
    void filterFlightsByDeparture() {
        Flight flightA = createFlight(LocalDate.of(2020, Month.OCTOBER, 3), LocalDate.of(2020, Month.OCTOBER, 3), "NVL185", 3L);
        Flight flightB = createFlight(LocalDate.of(2020, Month.OCTOBER, 4), LocalDate.of(2020, Month.OCTOBER, 4), "NVL186", 4L);
        Flight flightC = createFlight(LocalDate.of(2020, Month.OCTOBER, 5), LocalDate.of(2020, Month.OCTOBER, 5), "NVL187", 5L);
        flightA.setDestinationAirport(airport1);
        flightA.setOriginAirport(airport2);
        flightB.setDestinationAirport(airport2);
        flightB.setOriginAirport(airport1);
        flightC.setDestinationAirport(airport1);
        flightC.setOriginAirport(airport2);

        when(flightDao.findAll()).thenReturn(List.of(flightA, flightB, flightC));
        when(airportDao.findById(airport1.getId())).thenReturn(airport1);
        when(airportDao.findById(airport2.getId())).thenReturn(airport2);

        List<Flight> filteredFlights = flightService.filterFlights(null, LocalDate.of(2020, Month.OCTOBER, 4), null, null);
        assertEquals(List.of(flightA), filteredFlights);
    }

    @Test
    void filterFlights() {
        Flight flightA = createFlight(LocalDate.of(2020, Month.OCTOBER, 3), LocalDate.of(2020, Month.OCTOBER, 3), "NVL185", 3L);
        Flight flightB = createFlight(LocalDate.of(2020, Month.OCTOBER, 4), LocalDate.of(2020, Month.OCTOBER, 4), "NVL186", 4L);
        Flight flightC = createFlight(LocalDate.of(2020, Month.OCTOBER, 4), LocalDate.of(2020, Month.OCTOBER, 4), "NVL187", 5L);
        Flight flightD = createFlight(LocalDate.of(2020, Month.OCTOBER, 4), LocalDate.of(2020, Month.OCTOBER, 5), "NVL188", 6L);
        Flight flightE = createFlight(LocalDate.of(2020, Month.OCTOBER, 5), LocalDate.of(2020, Month.OCTOBER, 5), "NVL189", 7L);

        flightA.setDestinationAirport(airport1);

        flightA.setOriginAirport(airport2);
        flightB.setDestinationAirport(airport2);
        flightB.setOriginAirport(airport1);
        flightC.setDestinationAirport(airport1);
        flightC.setOriginAirport(airport2);
        flightD.setOriginAirport(airport1);
        flightD.setDestinationAirport(airport2);
        flightE.setOriginAirport(airport2);
        flightE.setDestinationAirport(airport1);

        when(flightDao.findAll()).thenReturn(List.of(flightA, flightB, flightC));
        when(airportDao.findById(airport1.getId())).thenReturn(airport1);
        when(airportDao.findById(airport2.getId())).thenReturn(airport2);

        List<Flight> filteredFlights = flightService.filterFlights(LocalDate.of(2020, Month.OCTOBER, 3), LocalDate.of(2020, Month.OCTOBER, 5), airport1.getId(), airport2.getId());
        assertEquals(List.of(flightC), filteredFlights);
    }

    @Test
    void filterFlightsByArrivalAndDeparture() {
        Flight flightA = createFlight(LocalDate.of(2020, Month.OCTOBER, 3), LocalDate.of(2020, Month.OCTOBER, 3), "NVL185", 3L);
        Flight flightB = createFlight(LocalDate.of(2020, Month.OCTOBER, 4), LocalDate.of(2020, Month.OCTOBER, 4), "NVL186", 4L);
        Flight flightC = createFlight(LocalDate.of(2020, Month.OCTOBER, 5), LocalDate.of(2020, Month.OCTOBER, 5), "NVL187", 5L);
        flightA.setDestinationAirport(airport1);
        flightA.setOriginAirport(airport2);
        flightB.setDestinationAirport(airport2);
        flightB.setOriginAirport(airport1);
        flightC.setDestinationAirport(airport1);
        flightC.setOriginAirport(airport2);

        when(flightDao.findAll()).thenReturn(List.of(flightA, flightB, flightC));
        when(airportDao.findById(airport1.getId())).thenReturn(airport1);
        when(airportDao.findById(airport2.getId())).thenReturn(airport2);

        List<Flight> filteredFlights = flightService.filterFlights(LocalDate.of(2020, Month.OCTOBER, 3), LocalDate.of(2020, Month.OCTOBER, 5), null, null);
        assertEquals(List.of(flightB), filteredFlights);
    }

    @Test
    void create() throws AirportManagerException {
        Flight flight3 = createFlight(LocalDate.of(2020, Month.MARCH, 4), LocalDate.of(2020, Month.MARCH, 4), "CDL186", 3L);
        flight3.setAirplane(airplane);

        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(airplaneDao.findById(1L)).thenReturn(airplane);
        when(stewardDao.findById(1L)).thenReturn(steward1);
        when(stewardDao.findById(2L)).thenReturn(steward2);

        Flight updatedFlight = flightService.create(flight1);
        assertEquals(updatedFlight, flight1);
        verify(flightDao, times(1)).findById(flight1.getId());
        verify(flightDao, times(1)).create(flight1);
        verify(airplaneDao, times(1)).findById(airplane.getId());
        verify(stewardDao, times(1)).findById(steward1.getId());
        verify(stewardDao, times(1)).findById(steward2.getId());
    }

    @Test
    void update() throws AirportManagerException {
        Flight flight3 = createFlight(LocalDate.of(2020, Month.MARCH, 4), LocalDate.of(2020, Month.MARCH, 4), "CDL186", 3L);
        flight3.setAirplane(airplane);

        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(airplaneDao.findById(1L)).thenReturn(airplane);
        when(stewardDao.findById(1L)).thenReturn(steward1);
        when(stewardDao.findById(2L)).thenReturn(steward2);

        Flight updatedFlight = flightService.update(flight1);
        assertEquals(updatedFlight, flight1);

        verify(flightDao, times(1)).findById(flight1.getId());
        verify(flightDao, times(1)).update(flight1);
        verify(airplaneDao, times(1)).findById(airplane.getId());
        verify(stewardDao, times(1)).findById(steward1.getId());
        verify(stewardDao, times(1)).findById(steward2.getId());
    }

    @Test
    void createAirplaneException() {
        Flight flight3 = createFlight(LocalDate.of(2020, Month.MARCH, 5), LocalDate.of(2020, Month.MARCH, 5), "Ns185", 3L);
        Flight flight4 = createFlight(LocalDate.of(2020, Month.MARCH, 5), LocalDate.of(2020, Month.MARCH, 5), "Ns186", 4L);

        //flight without stewards, so stewards will not be the thrown exception
        flight3.setAirplane(airplane);
        flight4.setAirplane(airplane);

        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(airplaneDao.findById(1L)).thenReturn(airplane);
        when(stewardDao.findById(1L)).thenReturn(steward1);
        when(stewardDao.findById(2L)).thenReturn(steward2);
        Exception exception = assertThrows(AirportManagerException.class, () -> flightService.create(flight3));
        assertEquals("Airplane is already on another flight at that time", exception.getMessage());
    }

    @Test
    void createStewardException() {
        flight1.setDeparture(LocalDate.of(2020, Month.MARCH, 1));
        flight1.setDeparture(LocalDate.of(2020, Month.MARCH, 1));
        Airplane airplane2 = createAirplane("RandomAirpldsfds3", 100, AirplaneType.JET, 2L);
        Flight flight3 = createFlight(LocalDate.of(2020, Month.MARCH, 1), LocalDate.of(2020, Month.MARCH, 1), "Ns185", 3L);
        flight3.setAirplane(airplane2);
        flight3.addSteward(steward1);

        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(airplaneDao.findById(1L)).thenReturn(airplane);
        when(airplaneDao.findById(2L)).thenReturn(airplane2);
        when(stewardDao.findById(1L)).thenReturn(steward1);
        when(stewardDao.findById(2L)).thenReturn(steward2);

        Exception exception = assertThrows(AirportManagerException.class, () -> flightService.update(flight1));
        assertEquals("Steward is already on another flight at that time", exception.getMessage());

    }

    @Test
    void updateAirplaneException() {
        Flight flight3 = createFlight(LocalDate.of(2020, Month.MARCH, 1), LocalDate.of(2020, Month.MARCH, 1), "Ns185", 3L);
        flight3.setAirplane(airplane);
        flight1.setDestinationAirport(airport1);
        flight1.setOriginAirport(airport2);

        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(airplaneDao.findById(1L)).thenReturn(airplane);
        when(stewardDao.findById(1L)).thenReturn(steward1);
        when(stewardDao.findById(2L)).thenReturn(steward2);

        Exception exception = assertThrows(AirportManagerException.class, () -> flightService.update(flight1));
        assertEquals("Airplane is already on another flight at that time", exception.getMessage());
    }

    @Test
    void updateAirplaneExceptionSameAirports() {
        flight1.setOriginAirport(flight1.getDestinationAirport());
        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(airplaneDao.findById(1L)).thenReturn(airplane);

        Exception exception = assertThrows(AirportManagerException.class, () -> flightService.update(flight1));
        assertEquals("Origin airport and destination Airport cannot be same", exception.getMessage());
    }

    @Test
    void updateStewardException() {
        flight1.setDeparture(LocalDate.of(2020, Month.MARCH, 1));
        flight1.setArrival(LocalDate.of(2020, Month.MARCH, 1));
        Flight flight3 = createFlight(LocalDate.of(2020, Month.MARCH, 1), LocalDate.of(2020, Month.MARCH, 1), "Ns185", 3L);
        Airplane airplane2 = createAirplane("RandomAirpldsfds3", 100, AirplaneType.JET, 2L);
        flight3.setAirplane(airplane2);
        flight3.addSteward(steward1);

        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(airplaneDao.findById(1L)).thenReturn(airplane);
        when(airplaneDao.findById(2L)).thenReturn(airplane2);
        when(stewardDao.findById(1L)).thenReturn(steward1);
        when(stewardDao.findById(2L)).thenReturn(steward2);

        Exception exception = assertThrows(AirportManagerException.class, () -> flightService.create(flight3));
        assertEquals("Steward is already on another flight at that time", exception.getMessage());
    }

    @Test
    void stewardExceptionDatesTest() {
        Airplane airplane2 = createAirplane("RandomAirpldsfds3", 100, AirplaneType.JET, 2L);
        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(airplaneDao.findById(1L)).thenReturn(airplane);
        when(airplaneDao.findById(2L)).thenReturn(airplane2);
        when(stewardDao.findById(1L)).thenReturn(steward1);
        when(stewardDao.findById(2L)).thenReturn(steward2);

        Flight flight3 = createFlight(LocalDate.of(2020, Month.SEPTEMBER, 2), LocalDate.of(2020, Month.SEPTEMBER, 4), "Ns185", 3L);
        flight3.setAirplane(airplane2);
        flight3.addSteward(steward1);

        // start before end in middle
        flight1.setDeparture(LocalDate.of(2020, Month.SEPTEMBER, 1));
        flight1.setArrival(LocalDate.of(2020, Month.SEPTEMBER, 3));
        Exception exception = assertThrows(AirportManagerException.class, () -> flightService.create(flight3));
        assertEquals("Steward is already on another flight at that time", exception.getMessage());

        // start before end after
        flight1.setDeparture(LocalDate.of(2020, Month.SEPTEMBER, 1));
        flight1.setArrival(LocalDate.of(2020, Month.SEPTEMBER, 5));
        exception = assertThrows(AirportManagerException.class, () -> flightService.create(flight3));
        assertEquals("Steward is already on another flight at that time", exception.getMessage());

        // start in the middle end in the middle
        flight1.setDeparture(LocalDate.of(2020, Month.SEPTEMBER, 3));
        flight1.setArrival(LocalDate.of(2020, Month.SEPTEMBER, 3));
        exception = assertThrows(AirportManagerException.class, () -> flightService.create(flight3));
        assertEquals("Steward is already on another flight at that time", exception.getMessage());

        // start in the middle end after
        flight1.setDeparture(LocalDate.of(2020, Month.SEPTEMBER, 3));
        flight1.setArrival(LocalDate.of(2020, Month.SEPTEMBER, 5));
        exception = assertThrows(AirportManagerException.class, () -> flightService.create(flight3));
        assertEquals("Steward is already on another flight at that time", exception.getMessage());
    }

    @Test
    void airplaneExceptionDatesTest() {
        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(airplaneDao.findById(1L)).thenReturn(airplane);
        when(stewardDao.findById(1L)).thenReturn(steward1);
        when(stewardDao.findById(2L)).thenReturn(steward2);

        Flight flight3 = createFlight(LocalDate.of(2020, Month.SEPTEMBER, 1), LocalDate.of(2020, Month.SEPTEMBER, 3), "Ns185", 3L);
        flight3.setAirplane(airplane);

        //start in middle, end after
        flight1.setDeparture(LocalDate.of(2020, Month.SEPTEMBER, 2));
        flight1.setArrival(LocalDate.of(2020, Month.SEPTEMBER, 4));
        Exception exception = assertThrows(AirportManagerException.class, () -> flightService.update(flight1));
        assertEquals("Airplane is already on another flight at that time", exception.getMessage());

        //start in middle, end in middle
        flight1.setDeparture(LocalDate.of(2020, Month.SEPTEMBER, 2));
        flight1.setArrival(LocalDate.of(2020, Month.SEPTEMBER, 2));
        exception = assertThrows(AirportManagerException.class, () -> flightService.update(flight1));
        assertEquals("Airplane is already on another flight at that time", exception.getMessage());

        //start before, end in middle
        flight1.setDeparture(LocalDate.of(2020, Month.AUGUST, 29));
        flight1.setArrival(LocalDate.of(2020, Month.SEPTEMBER, 2));
        exception = assertThrows(AirportManagerException.class, () -> flightService.update(flight1));
        assertEquals("Airplane is already on another flight at that time", exception.getMessage());

        //start before, end after
        flight1.setDeparture(LocalDate.of(2020, Month.AUGUST, 29));
        flight1.setArrival(LocalDate.of(2020, Month.SEPTEMBER, 6));
        exception = assertThrows(AirportManagerException.class, () -> flightService.update(flight1));
        assertEquals("Airplane is already on another flight at that time", exception.getMessage());
    }


    @Test
    void delete() {
        when(flightDao.findById(1L)).thenReturn(flight1);
        flightService.delete(flight1.getId());
        verify(flightDao, times(1)).delete(flight1);
        verify(flightDao, times(1)).findById(flight1.getId());
    }

    @Test
    void addSteward() {
        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(stewardDao.findById(steward1.getId())).thenReturn(steward1);
        flightService.addSteward(flight1.getId(), steward1.getId());
        verify(flightDao, times(1)).findById(flight1.getId());
        verify(stewardDao, times(1)).findById(steward1.getId());
    }

    @Test
    void removeSteward() {
        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(stewardDao.findById(steward1.getId())).thenReturn(steward1);
        flightService.removeSteward(flight1.getId(), steward1.getId());
        verify(flightDao, times(1)).findById(flight1.getId());
        verify(stewardDao, times(1)).findById(steward1.getId());
    }

    private Flight createFlight(LocalDate departure, LocalDate arrival, String flightCode, Long id) {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setDeparture(departure);
        flight.setArrival(arrival);
        flight.setFlightCode(flightCode);
        return flight;
    }

    private Airplane createAirplane(String airplaneName, Integer capacity, AirplaneType airplaneType, Long id) {
        Airplane airplane = new Airplane();
        airplane.setId(id);
        airplane.setName(airplaneName);
        airplane.setCapacity(capacity);
        airplane.setType(airplaneType);
        return airplane;
    }

    private Airport createAirport(String country, String name, String city, Long id) {
        Airport newAirport = new Airport();
        newAirport.setCity(city);
        newAirport.setCountry(country);
        newAirport.setName(name);
        newAirport.setId(id);
        return newAirport;
    }

    private Steward createSteward(String countryCode, String passportNumber, String firstName, String lastName, Long id) {
        Steward steward = new Steward();
        steward.setId(id);
        steward.setCountryCode(countryCode);
        steward.setPassportNumber(passportNumber);
        steward.setFirstName(firstName);
        steward.setLastName(lastName);
        return steward;
    }
}