package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.dao.AirplaneDao;
import cz.fi.muni.pa165.project.dao.AirportDao;
import cz.fi.muni.pa165.project.dao.FlightDao;
import cz.fi.muni.pa165.project.dao.StewardDao;
import cz.fi.muni.pa165.project.dto.*;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.entity.Airport;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.entity.Steward;
import cz.fi.muni.pa165.project.enums.AirplaneType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
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

        airport1 = createAirport("SAE", "Dubai airport", "Dubai", 1L);;
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
        when(flightDao.findAll()).thenReturn(List.of(flight1,flight2));
        assertEquals(flightService.findAll(),List.of(flight1,flight2));
    }

    @Disabled
    @Test
    void filterFlights() {
    }

    @Test
    void create() throws Exception {
        Flight flight3 = createFlight(LocalDate.of(2020, Month.MARCH, 4), LocalDate.of(2020, Month.MARCH, 4), "CDL186", 3L);
        flight3.setAirplane(airplane);

        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(airplaneDao.findById(1L)).thenReturn(airplane);
        when(stewardDao.findById(1L)).thenReturn(steward1);
        when(stewardDao.findById(2L)).thenReturn(steward2);

        Flight updatedFlight = flightService.create(flight1);
        assertEquals(updatedFlight,flight1);
        verify(flightDao,times(1)).findById(flight1.getId());
        verify(flightDao,times(1)).create(flight1);
        verify(airplaneDao,times(1)).findById(airplane.getId());
        verify(stewardDao,times(1)).findById(steward1.getId());
        verify(stewardDao,times(1)).findById(steward2.getId());
    }

    @Test
    void update() throws Exception {
        Flight flight3 = createFlight(LocalDate.of(2020, Month.MARCH, 4), LocalDate.of(2020, Month.MARCH, 4), "CDL186", 3L);
        flight3.setAirplane(airplane);

        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(airplaneDao.findById(1L)).thenReturn(airplane);
        when(stewardDao.findById(1L)).thenReturn(steward1);
        when(stewardDao.findById(2L)).thenReturn(steward2);

        Flight updatedFlight = flightService.update(flight1);
        assertEquals(updatedFlight,flight1);

        verify(flightDao,times(1)).findById(flight1.getId());
        verify(flightDao,times(1)).update(flight1);
        verify(airplaneDao,times(1)).findById(airplane.getId());
        verify(stewardDao,times(1)).findById(steward1.getId());
        verify(stewardDao,times(1)).findById(steward2.getId());
    }


    @Test
    void updateAirplaneException() throws Exception {
        Flight flight3 = createFlight(LocalDate.of(2020, Month.MARCH, 1), LocalDate.of(2020, Month.MARCH, 1), "Ns185", 3L);
        //flight without stewards, so stewards will not be the thrown exception
        flight3.setAirplane(airplane);

        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(airplaneDao.findById(1L)).thenReturn(airplane);
        when(stewardDao.findById(1L)).thenReturn(steward1);
        when(stewardDao.findById(2L)).thenReturn(steward2);

        Exception exception = assertThrows(Exception.class, () -> {
          flightService.update(flight1);
        });
        assertEquals("Airplane is already on another flight at that time",exception.getMessage());
    }

    @Test
    void updateStewardException() throws Exception {
        flight1.setDeparture(LocalDate.of(2020, Month.MARCH, 1));
        flight1.setDeparture(LocalDate.of(2020, Month.MARCH, 1));
        Airplane airplane2 = createAirplane("RandomAirpldsfds3", 100, AirplaneType.JET, 2L);
        Flight flight3 = createFlight(LocalDate.of(2020, Month.MARCH, 1), LocalDate.of(2020, Month.MARCH, 1), "Ns185", 3L);
        //flight without stewards, so stewards will not be the thrown exception
        flight3.setAirplane(airplane2);
        flight3.addSteward(steward1);

        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(airplaneDao.findById(1L)).thenReturn(airplane);
        when(airplaneDao.findById(2L)).thenReturn(airplane2);
        when(stewardDao.findById(1L)).thenReturn(steward1);
        when(stewardDao.findById(2L)).thenReturn(steward2);

        Exception exception = assertThrows(Exception.class, () -> {
            flightService.update(flight1);
        });
        assertEquals("Steward is already on another flight at that time",exception.getMessage());
    }


    @Test
    void delete() {
        when(flightDao.findById(1L)).thenReturn(flight1);
        flightService.delete(flight1.getId());
        verify(flightDao, times(1)).delete(flight1);
        verify(flightDao,times(1)).findById(flight1.getId());
    }

    @Test
    void addSteward() {
        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(stewardDao.findById(steward1.getId())).thenReturn(steward1);
        flightService.addSteward(flight1.getId(), steward1.getId());
        verify(flightDao, times(1)).findById(flight1.getId());
        verify(stewardDao,times(1)).findById(steward1.getId());
    }

    @Test
    void removeSteward() {
        when(flightDao.findById(flight1.getId())).thenReturn(flight1);
        when(stewardDao.findById(steward1.getId())).thenReturn(steward1);
        flightService.removeSteward(flight1.getId(), steward1.getId());
        verify(flightDao, times(1)).findById(flight1.getId());
        verify(stewardDao,times(1)).findById(steward1.getId());
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