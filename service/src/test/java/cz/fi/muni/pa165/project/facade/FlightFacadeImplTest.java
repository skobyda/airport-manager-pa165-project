package cz.fi.muni.pa165.project.facade;


import cz.fi.muni.pa165.project.dto.*;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.entity.Airport;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.entity.Steward;
import cz.fi.muni.pa165.project.enums.AirplaneType;
import cz.fi.muni.pa165.project.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Michal Zelenák
 * @created 05.05.2021
 * @project airport-manager
 **/

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class FlightFacadeImplTest {

    @Autowired
    @InjectMocks
    FlightFacadeImpl flightFacade;

    @MockBean
    FlightService flightService;

    @MockBean
    AirplaneService airplaneService;

    @MockBean
    AirportService airportService;

    @MockBean
    StewardService stewardService;

    @MockBean
    BeanMappingService beanMappingService;

    Airplane airplane1;
    Flight flight1;

    Airport airport1;
    Airport airport2;
    Steward steward1;
    Steward steward2;

    AirplaneDTO airplane1DTO;
    FlightDTO flight1DTO;
    AirportDTO airport1DTO;
    AirportDTO airport2DTO;
    StewardDTO steward1DTO;
    StewardDTO steward2DTO;

    @BeforeEach
    public void setUp() {
        Object[] temporaryObject;
        temporaryObject = createAirport("SAE", "Dubai airport", "Dubai", 1L);
        airport1 = (Airport) temporaryObject[0];
        airport1DTO = (AirportDTO) temporaryObject[1];

        temporaryObject = createAirport("SAE", "New York airport", "Haha_Land", 2L);
        airport2 = (Airport) temporaryObject[0];
        airport2DTO = (AirportDTO) temporaryObject[1];

        temporaryObject = createAirplane("RandomAirplaneName3", 100, AirplaneType.JET, 1L);
        airplane1 = (Airplane) temporaryObject[0];
        airplane1DTO = (AirplaneDTO) temporaryObject[1];

        temporaryObject = createSteward("SVK", "123", "Anna", "Novakova", 1L);
        steward1 = (Steward) temporaryObject[0];
        steward1DTO = (StewardDTO) temporaryObject[1];

        temporaryObject = createSteward("CZK", "124", "Janka", "Jandova", 2L);
        steward2 = (Steward) temporaryObject[0];
        steward2DTO = (StewardDTO) temporaryObject[1];

        temporaryObject = createFlight(LocalDate.of(2020, Month.MARCH, 1), LocalDate.of(2020, Month.MARCH, 1), "NVL185", 1L);
        flight1 = (Flight) temporaryObject[0];
        flight1DTO = (FlightDTO) temporaryObject[1];

        //create list of stewards
        HashSet<StewardDTO> stewards = new HashSet<>();
        stewards.add(steward1DTO);
        stewards.add(steward2DTO);

        //create set of flights
        HashSet<FlightDTO> flightsDTOset = new HashSet<FlightDTO>();
        flightsDTOset.add(flight1DTO);

        //create relationships for flight with id 1
        flight1.setOriginAirport(airport1);
        flight1.setDestinationAirport(airport2);
        flight1.setAirplane(airplane1);
        flight1.addSteward(steward1);
        flight1.addSteward(steward2);
        flight1DTO.setDestinationAirportId(flight1.getDestinationAirport().getId());
        flight1DTO.setOriginAirportId(flight1.getOriginAirport().getId());
        flight1DTO.setAirplaneId(flight1.getAirplane().getId());

        //add list of flights to used entities
        airplane1DTO.setFlights(flightsDTOset);
        airport1DTO.setDepartureFlights(flightsDTOset);
        airport2DTO.setArrivalFlights(flightsDTOset);

        //finish relationships in flightDTO
        flight1DTO.setOriginAirportId(airport1DTO.getId());
        flight1DTO.setDestinationAirportId(airport2DTO.getId());
        flight1DTO.setAirplaneId(airplane1DTO.getId());
        flight1DTO.setStewards(stewards);

        when(beanMappingService.mapTo(flight1, FlightDTO.class)).thenReturn(flight1DTO);
        when(beanMappingService.mapTo(flight1DTO, Flight.class)).thenReturn(flight1);

    }

    @Test
    void findById() {
        when(flightService.findById(1L)).thenReturn(flight1);
        assertEquals(flightFacade.findById(1L), flight1DTO);
    }

    @Test
    void findAll() {
        when(flightService.findAll()).thenReturn(List.of(flight1));
        flightFacade.findAll();
        verify(flightService, times(1)).findAll();
        verify(beanMappingService, times(1)).mapTo(List.of(flight1), FlightDTO.class);
    }

    @Test
    void create() throws Exception {
        //Initialize Flight and FlightCreateDTO classes
        Object [] temporaryObject = createFlightWithoutStewards(LocalDate.of(2020, Month.MARCH, 2), LocalDate.of(2020, Month.MARCH, 2), "NVL185", 2L);
        Flight flight2WithoutStewards = (Flight) temporaryObject[0];
        FlightCreateDTO flight2CreateDTO = (FlightCreateDTO) temporaryObject[1];
        flight2WithoutStewards.setAirplane(flight1.getAirplane());
        flight2WithoutStewards.setDestinationAirport(flight1.getDestinationAirport());
        flight2WithoutStewards.setOriginAirport(flight1.getOriginAirport());
        flight2CreateDTO.setAirplaneId(flight1DTO.getAirplaneId());
        flight2CreateDTO.setDestinationAirportId(flight1DTO.getDestinationAirportId());
        flight2CreateDTO.setOriginAirportId(flight1DTO.getOriginAirportId());

        //setup mock methods
        when(airplaneService.findById(flight2CreateDTO.getAirplaneId())).thenReturn(flight2WithoutStewards.getAirplane());
        when(airportService.findById(flight2CreateDTO.getDestinationAirportId())).thenReturn(flight2WithoutStewards.getDestinationAirport());
        when(airportService.findById(flight2CreateDTO.getOriginAirportId())).thenReturn(flight2WithoutStewards.getOriginAirport());
        when(stewardService.findById(1L)).thenReturn(steward1);
        when(stewardService.findById(2L)).thenReturn(steward2);
        when(flightService.create(flight2WithoutStewards)).thenReturn(flight2WithoutStewards);

        //test facade method create
        flightFacade.create(flight2CreateDTO);
        verify(flightService, times(1)).create(flight2WithoutStewards);
        verify(airportService, times(1)).findById(1L);
        verify(airportService, times(1)).findById(2L);
        verify(airplaneService,times(1)).findById(1L);
    }

    @Test
    void update() throws Exception {
        //setup mock
        when(airplaneService.findById(flight1DTO.getAirplaneId())).thenReturn(airplane1);
        when(airportService.findById(flight1DTO.getOriginAirportId())).thenReturn(airport1);
        when(airportService.findById(flight1DTO.getDestinationAirportId())).thenReturn(airport2);
        when(stewardService.findById(1L)).thenReturn(steward1);
        when(stewardService.findById(2L)).thenReturn(steward2);
        when(flightService.update(flight1)).thenReturn(flight1);

        //test facade method update
        flightFacade.update(flight1DTO);
        verify(flightService, times(1)).update(flight1);
        verify(airportService, times(1)).findById(1L);
        verify(airportService, times(1)).findById(2L);
        verify(airplaneService,times(1)).findById(1L);
        verify(stewardService,times(1)).findById(1L);
        verify(stewardService,times(1)).findById(2L);

    }

    @Test
    void delete() {
        flightFacade.delete(1L);
        verify(flightService, times(1)).delete(1L);
    }

    @Test
    void getFilteredList() {
        when(flightService.filterFlights(null, null, 0L, 0L)).thenReturn(List.of(flight1));
        flightFacade.getFilteredList(null, null, 0L, 0L);
        verify(flightService, times(1)).filterFlights(null, null, 0L, 0L);
        verify(beanMappingService, times(1)).mapTo(List.of(flight1), FlightDTO.class);
    }

    @Test
    void addSteward() throws Exception {
        flightFacade.addSteward(steward1.getId(), flight1.getId());
        verify(flightService, times(1)).addSteward(steward1.getId(), flight1.getId());
    }

    @Test
    void removeSteward() {
        flightFacade.removeSteward(steward1.getId(), flight1.getId());
        verify(flightService, times(1)).removeSteward(steward1.getId(), flight1.getId());
    }

    private Object[] createFlight(LocalDate departure, LocalDate arrival, String flightCode, Long id) {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setDeparture(departure);
        flight.setArrival(arrival);
        flight.setFlightCode(flightCode);

        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightCode(flight.getFlightCode());
        flightDTO.setId(flight.getId());
        flightDTO.setArrival(flight.getArrival());
        flightDTO.setDeparture(flight.getDeparture());
        return new Object[]{flight, flightDTO};
    }

    private Object[] createFlightWithoutStewards(LocalDate departure, LocalDate arrival, String flightCode, Long id) {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setDeparture(departure);
        flight.setArrival(arrival);
        flight.setFlightCode(flightCode);

        FlightCreateDTO flightDTO = new FlightCreateDTO();
        flightDTO.setFlightCode(flight.getFlightCode());
        flightDTO.setArrival(flight.getArrival());
        flightDTO.setDeparture(flight.getDeparture());
        return new Object[]{flight, flightDTO};
    }

    private Object[] createAirplane(String airplaneName, Integer capacity, AirplaneType airplaneType, Long id) {
        Airplane airplane = new Airplane();
        airplane.setId(id);
        airplane.setName(airplaneName);
        airplane.setCapacity(capacity);
        airplane.setType(airplaneType);

        AirplaneDTO airplaneDTO = new AirplaneDTO();
        airplaneDTO.setCapacity(airplane.getCapacity());
        airplaneDTO.setName(airplane.getName());
        airplaneDTO.setId(airplane.getId());
        airplaneDTO.setType(airplane.getType());
        return new Object[]{airplane, airplaneDTO};
    }

    private Object[] createAirport(String country, String name, String city, Long id) {
        Airport newAirport = new Airport();
        newAirport.setCity(city);
        newAirport.setCountry(country);
        newAirport.setName(name);
        newAirport.setId(id);

        AirportDTO airportDTO = new AirportDTO();
        airportDTO.setCity(newAirport.getCity());
        airportDTO.setName(newAirport.getName());
        airportDTO.setId(newAirport.getId());
        return new Object[]{newAirport, airportDTO};
    }

    private Object[] createSteward(String countryCode, String passportNumber, String firstName, String lastName, Long id) {
        Steward steward = new Steward();
        steward.setId(id);
        steward.setCountryCode(countryCode);
        steward.setPassportNumber(passportNumber);
        steward.setFirstName(firstName);
        steward.setLastName(lastName);

        StewardDTO stewardDTO = new StewardDTO();
        stewardDTO.setCountryCode(steward.getCountryCode());
        stewardDTO.setFirstName(steward.getFirstName());
        stewardDTO.setId(steward.getId());
        stewardDTO.setLastName(steward.getLastName());
        stewardDTO.setPassportNumber(steward.getPassportNumber());
        return new Object[]{steward, stewardDTO};
    }
}
