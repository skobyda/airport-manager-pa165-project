package cz.fi.muni.pa165.project.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import cz.fi.muni.pa165.project.ServiceTestsConfiguration;
import cz.fi.muni.pa165.project.dao.AirportDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import java.util.List;

import cz.fi.muni.pa165.project.entity.Airport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Simon Kobyda
 * @created 05.05.2021
 * @project airport-manager
 **/

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = ServiceTestsConfiguration.class)
public class AirportServiceTest {

    @Autowired
    @InjectMocks
    private AirportServiceImpl airportService;

    @MockBean
    private AirportDao airportDao;

    Airport airportVienna;
    Airport airportBudapest;
    Airport airportPrague;

    @BeforeEach
    public void setUp() {
        airportVienna = new Airport();
        airportVienna.setId(666L);
        airportVienna.setName("Vienna International Airport");
        airportVienna.setCity("Schwechat");
        airportVienna.setCountry("AT");

        airportBudapest = new Airport();
        airportBudapest.setId(13L);
        airportBudapest.setName("Budapest International Airport");
        airportBudapest.setCity("Budapest");
        airportBudapest.setCountry("HU");

        airportPrague = new Airport();
        airportPrague.setId(99L);
        airportPrague.setName("Vaclav Havel Airport");
        airportPrague.setCity("Prague");
        airportPrague.setCountry("CZ");
    }

    @Test
    public void testCreate() {
        airportDao.create(airportVienna);
        verify(airportDao, times(1)).create(airportVienna);
    }

    @Test
    public void testFindById() {
        when(airportDao.findById(666L)).thenReturn(airportVienna);

        Airport airport = airportService.findById(666L);

        Assertions.assertEquals(airport.getId(), 666L);

        verify(airportDao).findById(666L);
    }

    @Test
    public void testFindByCity() {
        when(airportDao.findByCity("Schwechat")).thenReturn(List.of(airportVienna));

        List<Airport> airports = airportService.findByCity("Schwechat");

        Assertions.assertEquals(airports.size(), 1);
        Assertions.assertEquals(airports.get(0).getCity(), "Schwechat");

        verify(airportDao).findByCity("Schwechat");
    }

    @Test
    public void testFindByCountry() {
        when(airportDao.findByCountry("AT")).thenReturn(List.of(airportVienna));

        List<Airport> airports = airportService.findByCountry("AT");
        Assertions.assertEquals(airports.size(), 1);
        Assertions.assertEquals(airports.get(0).getCountry(), "AT");

        verify(airportDao).findByCountry("AT");
    }

    @Test
    public void testFindByName() {
        when(airportDao.findByName("Vienna International Airport")).thenReturn(List.of(airportVienna));

        List<Airport> airports = airportService.findByName("Vienna International Airport");

        Assertions.assertEquals(airports.size(), 1);
        Assertions.assertEquals(airports.get(0).getName(), "Vienna International Airport");

        verify(airportDao).findByName("Vienna International Airport");
    }

    @Test
    public void testFindAll() {
        when(airportDao.findAll()).thenReturn(List.of(airportVienna, airportBudapest));

        List<Airport> airports = airportService.findAll();

        Assertions.assertEquals(airports.size(), 2);
        Assertions.assertTrue(airports.contains(airportVienna));
        Assertions.assertTrue(airports.contains(airportBudapest));
        Assertions.assertFalse(airports.contains(airportPrague));

        verify(airportDao).findAll();
    }

    @Test
    public void testUpdate() {
        when(airportDao.findById(airportVienna.getId())).thenReturn(airportVienna);
        airportVienna.setName("Brand new name of Vienna airport");

        airportService.update(airportVienna);

        when(airportDao.findByName("Brand new name of Vienna airport")).thenReturn(List.of(airportVienna));

        List<Airport> airplanes = airportService.findByName("Brand new name of Vienna airport");
        Assertions.assertEquals(airplanes.size(), 1);
        Assertions.assertEquals(airplanes.get(0).getName(), "Brand new name of Vienna airport");

        verify(airportDao).update(airportVienna);
    }

    @Test
    public void testDelete() {
        when(airportDao.findById(666L)).thenReturn(airportVienna);

        airportService.delete(airportVienna.getId());

        verify(airportDao).delete(airportVienna);
    }
}
