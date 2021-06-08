package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.ServiceTestsConfiguration;
import cz.fi.muni.pa165.project.dao.AirplaneDao;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.enums.AirplaneType;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Petr Hendrych
 **/

@SpringBootTest
@ContextConfiguration(classes = ServiceTestsConfiguration.class)
class AirplaneServiceImplTest {

    @Autowired
    private AirplaneService airplaneService;

    @MockBean
    private AirplaneDao airplaneDao;
    private Airplane airbus;
    private Airplane boeing;

    private static Airplane createAirplane(Long id, String name, Integer capacity, AirplaneType type) {
        Airplane airplane = new Airplane();

        airplane.setId(id);
        airplane.setName(name);
        airplane.setCapacity(capacity);
        airplane.setType(type);

        return airplane;
    }

    @BeforeEach
    void setUp() {
        airbus = createAirplane(1L, "Airbus", 342, AirplaneType.COMMUTER);
        boeing = createAirplane(2L, "Boeing", 403, AirplaneType.COMMUTER);
    }

    @Test
    void findAllTest() {
        when(airplaneDao.findAll()).thenReturn(List.of(airbus, boeing));

        List<Airplane> airplanes = airplaneService.findAll();
        assertThat(airplanes.size()).isEqualTo(2);
        assertThat(airplanes).containsExactly(airbus, boeing);

        Airplane a = new Airplane();
        airplaneService.create(a);

        when(airplaneDao.findAll()).thenReturn(List.of(airbus, boeing, a));

        List<Airplane> airplanes1 = airplaneService.findAll();
        assertThat(airplanes1.size()).isEqualTo(3);
        assertThat(airplanes1).containsExactly(airbus, boeing, a);

        verify(airplaneDao, times(2)).findAll();
    }

    @Test
    void findByIdTest() {
        when(airplaneDao.findById(1L)).thenReturn(airbus);

        Airplane airplane = airplaneService.findById(1L);
        assertEquals(airplane, airbus);

        verify(airplaneDao).findById(1L);
    }

    @Test
    void createTest() {
        airplaneService.create(airbus);
        verify(airplaneDao).create(airbus);
    }

    @Test
    void deleteTest() {
        when(airplaneDao.findById(1L)).thenReturn(airbus);

        airplaneService.delete(1L);

        verify(airplaneDao).delete(airbus);
    }

    @Test
    void updateTest() {
        boeing.setCapacity(100);
        boeing.setName("Boeing 2");
        when(airplaneDao.findById(boeing.getId())).thenReturn(boeing);
        airplaneService.update(boeing);

        verify(airplaneDao).update(boeing);
    }

    @Test
    void findWithBiggerOrEqualCapacityTest() {
        when(airplaneDao.findAll()).thenReturn(List.of(boeing, airbus));

        List<Airplane> airplanes1 = airplaneService.findWithBiggerOrEqualCapacity(100);
        List<Airplane> airplanes2 = airplaneService.findWithBiggerOrEqualCapacity(403);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(airplanes1.size()).isEqualTo(2);
            softly.assertThat(airplanes1).containsExactly(boeing, airbus);
            softly.assertThat(airplanes2.size()).isEqualTo(1);
            softly.assertThat(airplanes2).containsExactly(boeing);
        });

        verify(airplaneDao, times(2)).findAll();
    }

    @Test
    void findWithLowerOrEqualCapacityTest() {
        when(airplaneDao.findAll()).thenReturn(List.of(boeing, airbus));

        List<Airplane> airplanes1 = airplaneService.findWithLowerOrEqualCapacity(370);
        List<Airplane> airplanes2 = airplaneService.findWithLowerOrEqualCapacity(403);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(airplanes1.size()).isEqualTo(1);
            softly.assertThat(airplanes1).containsExactly(airbus);
            softly.assertThat(airplanes2.size()).isEqualTo(2);
            softly.assertThat(airplanes2).containsExactly(boeing, airbus);
        });
    }

    @Test
    void findByTypeTest() {
        when(airplaneDao.findByType(AirplaneType.COMMUTER)).thenReturn(List.of(boeing, airbus));
        when(airplaneDao.findByType(AirplaneType.JET)).thenReturn(new ArrayList<>());

        List<Airplane> airplanes1 = airplaneService.findByType(AirplaneType.COMMUTER);
        List<Airplane> airplane2 = airplaneService.findByType(AirplaneType.JET);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(airplanes1.size()).isEqualTo(2);
            softly.assertThat(airplanes1).containsExactly(boeing, airbus);
            softly.assertThat(airplane2.size()).isEqualTo(0);
        });

        verify(airplaneDao).findByType(AirplaneType.COMMUTER);
        verify(airplaneDao).findByType(AirplaneType.JET);
    }
}
