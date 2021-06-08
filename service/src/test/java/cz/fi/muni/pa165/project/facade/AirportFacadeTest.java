package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.ServiceTestsConfiguration;
import cz.fi.muni.pa165.project.dto.AirportCreateDTO;
import cz.fi.muni.pa165.project.dto.AirportDTO;
import cz.fi.muni.pa165.project.entity.Airport;
import cz.fi.muni.pa165.project.service.AirportService;
import cz.fi.muni.pa165.project.service.BeanMappingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Simon Kobyda
 **/

@SpringBootTest
@ContextConfiguration(classes = ServiceTestsConfiguration.class)
public class AirportFacadeTest {

    Airport airportVienna;
    AirportDTO airportDTOVienna;
    AirportCreateDTO airportCreateDTOVienna;

    @Autowired
    private AirportFacade airportFacade;
    @MockBean
    private AirportService airportService;
    @MockBean
    private BeanMappingService beanMappingService;

    @BeforeEach
    public void setUp() {
        Long id = 666L;
        String name = "Vienna International Airport";
        String city = "Schwechat";
        String country = "AT";
        airportVienna = new Airport();
        airportVienna.setId(id);
        airportVienna.setName(name);
        airportVienna.setCity(city);
        airportVienna.setCountry(country);

        airportDTOVienna = new AirportDTO();
        airportDTOVienna.setId(id);
        airportDTOVienna.setName(name);
        airportDTOVienna.setCity(city);
        airportDTOVienna.setCountry(country);
        when(beanMappingService.mapTo(airportVienna, AirportDTO.class)).thenReturn(airportDTOVienna);
        when(beanMappingService.mapTo(airportDTOVienna, Airport.class)).thenReturn(airportVienna);

        airportCreateDTOVienna = new AirportCreateDTO();
        airportCreateDTOVienna.setName(name);
        airportCreateDTOVienna.setCity(city);
        airportCreateDTOVienna.setCountry(country);
        when(beanMappingService.mapTo(airportVienna, AirportCreateDTO.class)).thenReturn(airportCreateDTOVienna);
        when(beanMappingService.mapTo(airportCreateDTOVienna, Airport.class)).thenReturn(airportVienna);
    }

    @Test
    public void testCreate() {
        airportFacade.create(airportCreateDTOVienna);
        verify(airportService, times(1)).create(airportVienna);
    }

    @Test
    public void testFindAll() {
        when(airportService.findAll()).thenReturn(List.of(airportVienna));
        airportFacade.findAll();
        verify(airportService, times(1)).findAll();
        verify(beanMappingService, times(1)).mapTo(List.of(airportVienna), AirportDTO.class);

    }

    @Test
    public void testFindById() {
        when(airportService.findById(666L)).thenReturn(airportVienna);
        airportFacade.findById(666L);

        verify(airportService, times(1)).findById(666L);
        verify(beanMappingService, times(1)).mapTo(airportVienna, AirportDTO.class);
    }

    @Test
    public void testFindByName() {
        when(airportService.findByName("Vienna International Airport")).thenReturn(List.of(airportVienna));
        airportFacade.findByName("Vienna International Airport");

        verify(airportService, times(1)).findByName("Vienna International Airport");
        verify(beanMappingService, times(1)).mapTo(List.of(airportVienna), AirportDTO.class);
    }

    @Test
    public void testFindByCountry() {
        when(airportService.findByCountry("AT")).thenReturn(List.of(airportVienna));
        airportFacade.findByCountry("AT");

        verify(airportService, times(1)).findByCountry("AT");
        verify(beanMappingService, times(1)).mapTo(List.of(airportVienna), AirportDTO.class);
    }

    @Test
    public void testFindByCity() {
        when(airportService.findByCity("Schwechat")).thenReturn(List.of(airportVienna));
        airportFacade.findByCity("Schwechat");

        verify(airportService, times(1)).findByCity("Schwechat");
        verify(beanMappingService, times(1)).mapTo(List.of(airportVienna), AirportDTO.class);
    }

    @Test
    public void testUpdate() {
        airportDTOVienna.setName("Brand new airport name");
        airportFacade.update(airportDTOVienna);

        when(airportService.findByName("Brand new airport name")).thenReturn(List.of(airportVienna));
        when(beanMappingService.mapTo(List.of(airportVienna), AirportDTO.class)).thenReturn(List.of(airportDTOVienna));

        List<AirportDTO> airplanes = airportFacade.findByName("Brand new airport name");
        Assertions.assertEquals(airplanes.size(), 1);
        Assertions.assertEquals(airplanes.get(0).getName(), "Brand new airport name");

        verify(airportService).update(airportVienna);
    }

    @Test
    public void testDelete() {
        airportFacade.delete(airportDTOVienna.getId());
        verify(airportService, times(1)).delete(airportVienna.getId());
    }
}
