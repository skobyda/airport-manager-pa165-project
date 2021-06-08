package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.PersistenceTestsConfiguration;
import cz.fi.muni.pa165.project.entity.Airport;
import cz.fi.muni.pa165.project.entity.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author Michal Zelenák
 **/

@SpringBootTest
@Transactional
@ContextConfiguration(classes = PersistenceTestsConfiguration.class)
class AirportDaoImplTest {

    @PersistenceContext
    EntityManager em;
    private Flight flight123;
    private Flight flight125;
    private Flight flight127;
    private Airport KennedyAirport;
    private Airport WashingtonAirport;
    private Airport toUpdateAirport;
    @Autowired
    private AirportDao airportDao;


    @BeforeEach
    void setUp() {
        flight123 = createFlight(LocalDateTime.of(2021, 11, 9, 0 , 0), LocalDateTime.of(2021, 11, 9, 0 , 0), "JAR-123");
        flight125 = createFlight(LocalDateTime.of(2021, 11, 8, 0 , 0), LocalDateTime.of(2021, 11, 8, 0 , 0), "JAR-125");
        flight127 = createFlight(LocalDateTime.of(2021, 12, 8, 0 , 0), LocalDateTime.of(2021, 12, 8, 0 , 0), "JAR-127");
        em.persist(flight123);
        em.persist(flight125);
        em.persist(flight127);

        KennedyAirport = new Airport();
        WashingtonAirport = new Airport();
        toUpdateAirport = new Airport();

        KennedyAirport.setCountry("USA");
        KennedyAirport.setName("Kennedy airport");
        KennedyAirport.setCity("New York");

        WashingtonAirport.setCountry("KAZ");
        WashingtonAirport.setName("WashingtonAirport");
        WashingtonAirport.setCity("Almaty");

        toUpdateAirport.setCountry("USA");
        toUpdateAirport.setName("Funny airport");
        toUpdateAirport.setCity("New York");

        em.persist(KennedyAirport);
        em.persist(WashingtonAirport);
        em.persist(toUpdateAirport);
    }

    @Test
    void findAll() {
        List<Airport> airports = airportDao.findAll();
        assertEquals(3, airports.size());
    }

    @Test
    void create() {
        Airport testAirport = new Airport();
        testAirport.setName("Sliac");
        airportDao.create(testAirport);
        assertEquals(airportDao.findAll().size(), 4);
    }


    @Test
    void findById() {
        Airport found = airportDao.findById(KennedyAirport.getId());
        assertEquals(found.getName(), "Kennedy airport");
        assertEquals(found.getCity(), "New York");
        assertEquals(found.getCountry(), "USA");
    }

    @Test
    void findByName() {
        List<Airport> airportByName = airportDao.findByName(KennedyAirport.getName());
        assertEquals("Kennedy airport", airportByName.get(0).getName());
        assertEquals("New York", airportByName.get(0).getCity());
        assertEquals("USA", airportByName.get(0).getCountry());
    }

    @Test
    void findByCity() {
        List<Airport> airportByCity = airportDao.findByCity(KennedyAirport.getCity());
        assertEquals("Kennedy airport", airportByCity.get(0).getName());
        assertEquals("New York", airportByCity.get(0).getCity());
        assertEquals("USA", airportByCity.get(0).getCountry());
    }

    @Test
    void findByCountry() {
        List<Airport> airportByCountry = airportDao.findByCountry(KennedyAirport.getCountry());
        assertEquals("Kennedy airport", airportByCountry.get(0).getName());
        assertEquals("New York", airportByCountry.get(0).getCity());
        assertEquals("USA", airportByCountry.get(0).getCountry());
    }

    @Test
    void delete() {
        Airport ToDeleteAirport;
        ToDeleteAirport = new Airport();
        ToDeleteAirport.setCountry("AU");
        ToDeleteAirport.setName("LincolnAirport");
        ToDeleteAirport.setCity("Vienna");
        em.persist(ToDeleteAirport);

        airportDao.delete(ToDeleteAirport);
        assertEquals(3, airportDao.findAll().size());
    }

    @Test
    void deleteById() {
        Airport toDeleteAirport2;
        toDeleteAirport2 = new Airport();
        toDeleteAirport2.setCountry("TUR");
        toDeleteAirport2.setName("LincolnAirport");
        toDeleteAirport2.setCity("Alma");
        em.persist(toDeleteAirport2);
        airportDao.deleteById(toDeleteAirport2.getId());
        assertEquals(3, airportDao.findAll().size());
    }

    private Flight createFlight(LocalDateTime arrival, LocalDateTime departure, String flightCode) {
        flight123 = new Flight();
        flight123.setArrival(arrival);
        flight123.setDeparture(departure);
        flight123.setFlightCode(flightCode);
        return flight123;
    }

    @Test
    void updateName() {
        Airport found = airportDao.findById(toUpdateAirport.getId());
        found.setName("NewAirportName");
        assertNotEquals(found.getName(), "RandomAirportName1");
        assertEquals(found.getName(), "NewAirportName");
    }

    @Test
    void updateCity() {
        Airport found = airportDao.findById(toUpdateAirport.getId());
        found.setCity("NewAirportCity");
        assertNotEquals(found.getCity(), "RandomAirportCity1");
        assertEquals(found.getCity(), "NewAirportCity");
    }

    @Test
    void updateCountry() {
        Airport found = airportDao.findById(toUpdateAirport.getId());
        found.setCountry("NewAirportCountry");
        assertNotEquals(found.getCountry(), "RandomAirportCountry1");
        assertEquals(found.getCountry(), "NewAirportCountry");
    }

}