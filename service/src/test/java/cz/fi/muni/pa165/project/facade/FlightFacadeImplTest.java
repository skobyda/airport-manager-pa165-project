package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.ServiceTestsConfiguration;
import cz.fi.muni.pa165.project.dto.*;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.entity.Airport;
import cz.fi.muni.pa165.project.entity.Flight;
import cz.fi.muni.pa165.project.entity.Steward;
import cz.fi.muni.pa165.project.enums.AirplaneType;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import cz.fi.muni.pa165.project.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Michal Zelenák
 **/

@SpringBootTest
@ContextConfiguration(classes = ServiceTestsConfiguration.class)
class FlightFacadeImplTest {

    @Autowired
    FlightFacade flightFacade;

    @MockBean
    BeanMappingService beanMappingService;

    @MockBean
    FlightService flightService;

    @MockBean
    AirplaneService airplaneService;

    @MockBean
    AirportService airportService;

    @MockBean
    StewardService stewardService;

    Airplane airplane1;
    Flight flight1;

    Airport airport1;
    Airport airport2;
    Steward steward1;
    Steward steward2;

    AirplaneSimpleDTO airplane1DTO;
    FlightDTO flight1DTO;
    AirportSimpleDTO airport1DTO;
    AirportSimpleDTO airport2DTO;
    StewardSimpleDTO steward1DTO;
    StewardSimpleDTO steward2DTO;
    FlightSimpleDTO flightSimpleDTO;


    @BeforeEach
    public void setUp() {
        Object[] temporaryObject;
        temporaryObject = createAirport("SAE", "Dubai airport", "Dubai", 1L);
        airport1 = (Airport) temporaryObject[0];
        airport1DTO = (AirportSimpleDTO) temporaryObject[1];

        temporaryObject = createAirport("SAE", "New York airport", "Haha_Land", 2L);
        airport2 = (Airport) temporaryObject[0];
        airport2DTO = (AirportSimpleDTO) temporaryObject[1];

        temporaryObject = createAirplane("RandomAirplaneName3", 100, AirplaneType.JET, 1L);
        airplane1 = (Airplane) temporaryObject[0];
        airplane1DTO = (AirplaneSimpleDTO) temporaryObject[1];

        temporaryObject = createSteward("SVK", "123", "Anna", "Novakova", 1L);
        steward1 = (Steward) temporaryObject[0];
        steward1DTO = (StewardSimpleDTO) temporaryObject[1];

        temporaryObject = createSteward("CZK", "124", "Janka", "Jandova", 2L);
        steward2 = (Steward) temporaryObject[0];
        steward2DTO = (StewardSimpleDTO) temporaryObject[1];

        temporaryObject = createFlight(LocalDateTime.of(2020, Month.MARCH, 1, 10, 30), LocalDateTime.of(2020, Month.MARCH, 1, 11, 30), "NVL185", 1L);
        flight1 = (Flight) temporaryObject[0];
        flight1DTO = (FlightDTO) temporaryObject[1];

        //create list of stewards
        HashSet<StewardSimpleDTO> stewards = new HashSet<>();
        stewards.add(steward1DTO);
        stewards.add(steward2DTO);

        //create set of flights
        HashSet<FlightDTO> flightsDTOset = new HashSet<>();
        flightsDTOset.add(flight1DTO);

        //create relationships for flight with id 1
        flight1.setOriginAirport(airport1);
        flight1.setDestinationAirport(airport2);
        flight1.setAirplane(airplane1);
        flight1.addSteward(steward1);
        flight1.addSteward(steward2);
        flight1DTO.setDestinationAirport(airport1DTO);
        flight1DTO.setOriginAirport(airport2DTO);
        flight1DTO.setAirplane(airplane1DTO);


        //finish relationships in flightDTO
        flight1DTO.setStewards(stewards);

        when(beanMappingService.mapTo(flight1, FlightDTO.class)).thenReturn(flight1DTO);
        when(beanMappingService.mapTo(flight1DTO, Flight.class)).thenReturn(flight1);
        when(beanMappingService.mapTo(List.of(flight1), FlightDTO.class)).thenReturn(List.of(flight1DTO));

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
    void create() throws AirportManagerException {
        //Initialize Flight and FlightCreateDTO classes
        Object[] temporaryObject = createFlightWithoutStewards(LocalDateTime.of(2020, Month.MARCH, 2, 9, 30), LocalDateTime.of(2020, Month.MARCH, 2, 10, 30), "NVL185", 2L);
        Flight flight2WithoutStewards = (Flight) temporaryObject[0];
        FlightCreateDTO flight2CreateDTO = (FlightCreateDTO) temporaryObject[1];
        flight2WithoutStewards.setAirplane(flight1.getAirplane());
        flight2WithoutStewards.setDestinationAirport(flight1.getDestinationAirport());
        flight2WithoutStewards.setOriginAirport(flight1.getOriginAirport());
        flight2CreateDTO.setAirplaneId(flight1DTO.getAirplane().getId());
        flight2CreateDTO.setDestinationAirportId(flight1DTO.getDestinationAirport().getId());
        flight2CreateDTO.setOriginAirportId(flight1DTO.getOriginAirport().getId());

        //setup mock methods
        when(airplaneService.findById(flight2CreateDTO.getAirplaneId())).thenReturn(flight2WithoutStewards.getAirplane());
        when(airportService.findById(flight2CreateDTO.getDestinationAirportId())).thenReturn(flight2WithoutStewards.getDestinationAirport());
        when(airportService.findById(flight2CreateDTO.getOriginAirportId())).thenReturn(flight2WithoutStewards.getOriginAirport());
        when(flightService.create(flight2WithoutStewards)).thenReturn(flight2WithoutStewards);
        when(beanMappingService.mapTo(flight2CreateDTO, Flight.class)).thenReturn(flight2WithoutStewards);

        //test facade method create
        flightFacade.create(flight2CreateDTO);
        verify(flightService, times(1)).create(flight2WithoutStewards);

    }

    @Test
    void update() throws AirportManagerException {
        flightSimpleDTO = new FlightSimpleDTO();
        flightSimpleDTO.setFlightCode(flight1DTO.getFlightCode());
        flightSimpleDTO.setArrival(flight1DTO.getArrival());
        flightSimpleDTO.setDeparture(flight1DTO.getDeparture());
        flightSimpleDTO.setId(flight1DTO.getId());
        flightSimpleDTO.setAirplaneId(flight1DTO.getAirplane().getId());
        flightSimpleDTO.setDestinationAirportId(flight1DTO.getDestinationAirport().getId());
        flightSimpleDTO.setOriginAirportId(flight1DTO.getOriginAirport().getId());
        HashSet<Long> stewardIds = new HashSet<>();
        for (StewardSimpleDTO steward : flight1DTO.getStewards()) {
            stewardIds.add(steward.getId());
        }
        when(flightService.update(flight1)).thenReturn(flight1);
        when(airplaneService.findById(flightSimpleDTO.getAirplaneId())).thenReturn(airplane1);
        when(airportService.findById(flightSimpleDTO.getOriginAirportId())).thenReturn(airport1);
        when(airportService.findById(flightSimpleDTO.getDestinationAirportId())).thenReturn(airport2);
        when(stewardService.findById(steward1.getId())).thenReturn(steward1);
        when(stewardService.findById(steward2.getId())).thenReturn(steward2);

        //test facade method update
        flightFacade.update(flightSimpleDTO);
        verify(flightService, times(1)).update(flight1);
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
    void addSteward() throws AirportManagerException {
        flightFacade.addSteward(steward1.getId(), flight1.getId());
        verify(flightService, times(1)).addSteward(steward1.getId(), flight1.getId());
    }

    @Test
    void removeSteward() {
        flightFacade.removeSteward(steward1.getId(), flight1.getId());
        verify(flightService, times(1)).removeSteward(steward1.getId(), flight1.getId());
    }

    private Object[] createFlight(LocalDateTime departure, LocalDateTime arrival, String flightCode, Long id) {
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

    private Object[] createFlightWithoutStewards(LocalDateTime departure, LocalDateTime arrival, String flightCode, Long id) {
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

        AirplaneSimpleDTO airplaneDTO = new AirplaneSimpleDTO();
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

        AirportSimpleDTO airportDTO = new AirportSimpleDTO();
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

        StewardSimpleDTO stewardDTO = new StewardSimpleDTO();
        stewardDTO.setCountryCode(steward.getCountryCode());
        stewardDTO.setFirstName(steward.getFirstName());
        stewardDTO.setId(steward.getId());
        stewardDTO.setLastName(steward.getLastName());
        stewardDTO.setPassportNumber(steward.getPassportNumber());
        return new Object[]{steward, stewardDTO};
    }
}
