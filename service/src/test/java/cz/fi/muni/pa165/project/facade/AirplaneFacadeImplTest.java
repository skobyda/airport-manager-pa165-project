package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.AirplaneCreateDTO;
import cz.fi.muni.pa165.project.dto.AirplaneDTO;
import cz.fi.muni.pa165.project.dto.FlightDTO;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.enums.AirplaneType;
import cz.fi.muni.pa165.project.service.AirplaneService;
import cz.fi.muni.pa165.project.service.BeanMappingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Petr Hendrych
 * @created 05.05.2021
 * @project airport-manager
 **/

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AirplaneFacadeImplTest {

    @InjectMocks
    private final AirplaneFacadeImpl airplaneFacade = new AirplaneFacadeImpl();

    @MockBean
    private AirplaneService airplaneService;

    @MockBean
    private BeanMappingService beanMappingService;

    private Airplane airbus;
    private AirplaneCreateDTO airbusCreateDTO;
    private AirplaneDTO airbusDTO;

    private Airplane boeing;
    private AirplaneCreateDTO boeingCreateDTO;
    private AirplaneDTO boeingDTO;

    @BeforeEach
    void setUp() {
        airbus = createAirplane(1L, "Airbus", 320, AirplaneType.COMMUTER);
        airbusCreateDTO = createAirplaneCreateDTO(airbus.getName(), airbus.getCapacity(), airbus.getType());
        airbusDTO = createAirplaneDTO(1L, airbus.getName(), airbus.getCapacity(), airbus.getType(), new HashSet<>());

        boeing = createAirplane(2L, "Boeing", 400, AirplaneType.COMMUTER);
        boeingCreateDTO = createAirplaneCreateDTO(boeing.getName(), boeing.getCapacity(), boeing.getType());
        boeingDTO = createAirplaneDTO(2L, boeing.getName(), boeing.getCapacity(), boeing.getType(), new HashSet<>());
    }

    @Test
    void getAllTest() {
        when(airplaneService.findAll()).thenReturn(List.of(airbus, boeing));
        when(beanMappingService.mapTo(List.of(airbus, boeing), AirplaneDTO.class)).thenReturn(List.of(airbusDTO, boeingDTO));

        List<AirplaneDTO> airplanes = airplaneFacade.findAll();
        assertThat(airplanes.size()).isEqualTo(2);

        verify(airplaneService).findAll();
        verify(beanMappingService).mapTo(List.of(airbus, boeing), AirplaneDTO.class);
    }

    @Test
    void getByIdTest() {
        when(beanMappingService.mapTo(airbusDTO, Airplane.class)).thenReturn(airbus);
        when(beanMappingService.mapTo(airbus, AirplaneDTO.class)).thenReturn(airbusDTO);

        when(airplaneService.findById(1L)).thenReturn(airbus);
        assertThat(airplaneFacade.findById(1L)).isEqualTo(airbusDTO);
    }

    @Test
    void createTest() {
        when(beanMappingService.mapTo(airbusCreateDTO, Airplane.class)).thenReturn(airbus);

        airplaneFacade.create(airbusCreateDTO);
        verify(airplaneService).create(airbus);
    }

    @Test
    void updateTest() {
        when(beanMappingService.mapTo(airbusDTO, Airplane.class)).thenReturn(airbus);

        airbusDTO.setCapacity(34);
        airbusDTO.setName("Airbus2");
        airplaneFacade.update(airbusDTO);

        when(airplaneService.findWithLowerOrEqualCapacity(100)).thenReturn(List.of(airbus));
        when(beanMappingService.mapTo(List.of(airbus), AirplaneDTO.class)).thenReturn(List.of(airbusDTO));

        List<AirplaneDTO> airplanes = airplaneFacade.findWithLowerOrEqualCapacity(100);
        assertThat(airplanes.size()).isEqualTo(1);
        assertThat(airplanes.get(0).getName()).isEqualTo("Airbus2");

        verify(airplaneService).update(airbus);
    }

    @Test
    void deleteTest() {
        airplaneFacade.delete(2L);
        verify(airplaneService).delete(2L);
    }

    @Test
    void findWithBiggerOrEqualCapacityTest() {
        when(airplaneService.findWithBiggerOrEqualCapacity(350)).thenReturn(List.of(boeing));
        when(beanMappingService.mapTo(List.of(boeing), AirplaneDTO.class)).thenReturn(List.of(boeingDTO));

        List<AirplaneDTO> airplanes = airplaneFacade.findWithBiggerOrEqualCapacity(350);

        assertThat(airplanes.size()).isEqualTo(1);
        assertThat(airplanes).contains(boeingDTO);
    }

    @Test
    void findWithLowerOrEqualCapacityTest() {
        when(airplaneService.findWithLowerOrEqualCapacity(500)).thenReturn(List.of(airbus, boeing));
        when(beanMappingService.mapTo(List.of(airbus, boeing), AirplaneDTO.class)).thenReturn(List.of(airbusDTO, boeingDTO));

        List<AirplaneDTO> airplanes = airplaneFacade.findWithLowerOrEqualCapacity(500);

        assertThat(airplanes.size()).isEqualTo(2);
        assertThat(airplanes).containsExactly(airbusDTO, boeingDTO);
    }

    @Test
    void findByTypeTest() {
        when(airplaneService.findByType(AirplaneType.COMMUTER)).thenReturn(List.of(airbus, boeing));
        when(beanMappingService.mapTo(List.of(airbus, boeing), AirplaneDTO.class)).thenReturn(List.of(airbusDTO, boeingDTO));

        List<AirplaneDTO> airplanes = airplaneFacade.findByType(AirplaneType.COMMUTER);
        assertThat(airplanes.size()).isEqualTo(2);
        assertThat(airplanes).containsExactly(airbusDTO, boeingDTO);
    }

    private static AirplaneDTO createAirplaneDTO(Long id, String name, Integer capacity, AirplaneType type, Set<FlightDTO> flights) {
        AirplaneDTO dto = new AirplaneDTO();

        dto.setId(id);
        dto.setName(name);
        dto.setCapacity(capacity);
        dto.setType(type);
        dto.setFlights(flights);

        return dto;
    }

    private static Airplane createAirplane(Long id, String name, Integer capacity, AirplaneType type) {
        Airplane airplane = new Airplane();

        airplane.setId(id);
        airplane.setName(name);
        airplane.setCapacity(capacity);
        airplane.setType(type);

        return airplane;
    }

    private static AirplaneCreateDTO createAirplaneCreateDTO(String name, Integer capacity, AirplaneType type) {
        AirplaneCreateDTO a = new AirplaneCreateDTO();

        a.setName(name);
        a.setCapacity(capacity);
        a.setType(type);

        return a;
    }
}
