package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.ServiceTestsConfiguration;
import cz.fi.muni.pa165.project.dao.StewardDao;
import cz.fi.muni.pa165.project.entity.Steward;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Jozef Vanický
 * @created 05.05.2021
 * @project airport-manager
 **/

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = ServiceTestsConfiguration.class)
class StewardServiceImplTest {

    Steward steward;
    Steward steward2;

    @MockBean
    StewardDao stewardDao;

    @Autowired
    @InjectMocks
    StewardServiceImpl stewardService;

    @BeforeEach
    void setUp() throws ServiceException {
        steward = new Steward();
        steward.setId(1L);
        steward.setFirstName("Jan");
        steward.setLastName("Mrkvicka");
        steward.setCountryCode("CZ");
        steward.setPassportNumber("EA33855ED");

        steward2 = new Steward();
        steward2.setId(2L);
        steward2.setFirstName("Jana");
        steward2.setLastName("Sladka");
        steward2.setCountryCode("CZ");
        steward2.setPassportNumber("DE45013MK");
    }

    @Test
    void create() {
        stewardDao.create(steward);
        verify(stewardDao, times(1)).create(steward);
    }

    @Test
    void findById() {
        when(stewardDao.findById(1L)).thenReturn(steward);
        assertEquals(steward, stewardService.findById(1L));
        verify(stewardDao, times(1)).findById(1L);
    }

    @Test
    void findAll() {
        when(stewardDao.findAll(null)).thenReturn(List.of(steward, steward2));

        List<Steward> tempList = stewardDao.findAll(null);
        assertEquals(2, tempList.size());
        assertThat(tempList).contains(steward);
        assertThat(tempList).contains(steward2);

        verify(stewardDao, times(1)).findAll(null);
    }

    @Test
    void findByFirstName() {
        when(stewardDao.findByFirstName(steward.getFirstName())).thenReturn(List.of(steward));
        List<Steward> tempList = stewardDao.findByFirstName("Jan");

        assertThat(tempList).containsExactly(steward);
        verify(stewardDao, times(1)).findByFirstName("Jan");
    }

    @Test
    void findByLastName() {
        when(stewardDao.findByLastName(steward.getLastName())).thenReturn(List.of(steward));
        List<Steward> tempList = stewardDao.findByLastName("Mrkvicka");

        assertThat(tempList).containsExactly(steward);
        verify(stewardDao, times(1)).findByLastName("Mrkvicka");
    }

    @Test
    void findByCountryCode() {
        when(stewardDao.findByCountryCode("CZ")).thenReturn(List.of(steward, steward2));
        List<Steward> tempList = stewardDao.findByCountryCode("CZ");

        assertThat(tempList).contains(steward);
        assertThat(tempList).contains(steward2);
        verify(stewardDao, times(1)).findByCountryCode("CZ");
    }

    @Test
    void findByPassportNumber() {
        when(stewardDao.findByPassportNumber(steward.getPassportNumber())).thenReturn(List.of(steward));
        List<Steward> tempList = stewardDao.findByPassportNumber("EA33855ED");

        assertThat(tempList).containsExactly(steward);
        verify(stewardDao, times(1)).findByPassportNumber("EA33855ED");
    }

    @Test
    void update() {
        when(stewardDao.findById(1L)).thenReturn(steward);
        steward.setLastName("Sladky");
        stewardService.update(steward);

        when(stewardDao.findByLastName("Sladky")).thenReturn(List.of(steward));

        List<Steward> stewardList = stewardService.findByLastName("Sladky");

        assertEquals(stewardList.size(), 1);
        assertEquals(stewardList.get(0).getLastName(), "Sladky");

        verify(stewardDao, times(1)).update(steward);
    }

    @Test
    void delete() {
        when(stewardDao.findById(1L)).thenReturn(steward);

        stewardService.delete(1L);
        verify(stewardDao, times(1)).delete(steward);
    }
}