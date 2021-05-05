package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.StewardCreateDTO;
import cz.fi.muni.pa165.project.dto.StewardDTO;
import cz.fi.muni.pa165.project.entity.Steward;
import cz.fi.muni.pa165.project.service.BeanMappingService;
import cz.fi.muni.pa165.project.service.StewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Jozef Vanický
 * @created 05.05.2021
 * @project airport-manager
 **/

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class StewardFacadeImplTest {

    @Autowired
    @InjectMocks
    StewardFacadeImpl stewardFacade;

    @MockBean
    StewardService stewardService;

    @MockBean
    BeanMappingService beanMappingService;

    Steward steward1;
    Steward steward2;

    StewardDTO stewardDTO1;
    StewardDTO stewardDTO2;

    StewardCreateDTO stewardCreateDTO;

    @BeforeEach
    void setUp() {
        steward1 = new Steward();
        steward1.setId(1L);
        steward1.setFirstName("Jan");
        steward1.setLastName("Mrkvicka");
        steward1.setCountryCode("CZ");
        steward1.setPassportNumber("EA33855ED");

        stewardDTO1 = new StewardDTO();
        stewardDTO1.setId(1L);
        stewardDTO1.setFirstName(steward1.getFirstName());
        stewardDTO1.setLastName(steward1.getLastName());
        stewardDTO1.setCountryCode(steward1.getCountryCode());
        stewardDTO1.setPassportNumber(steward1.getPassportNumber());

        when(beanMappingService.mapTo(steward1, StewardDTO.class)).thenReturn(stewardDTO1);
        when(beanMappingService.mapTo(stewardDTO1, Steward.class)).thenReturn(steward1);

        steward2 = new Steward();
        steward2.setId(2L);
        steward2.setFirstName("Jana");
        steward2.setLastName("Sladka");
        steward2.setCountryCode("CZ");
        steward2.setPassportNumber("DE45013MK");

        stewardDTO2 = new StewardDTO();
        stewardDTO2.setId(2L);
        stewardDTO2.setFirstName(steward1.getFirstName());
        stewardDTO2.setLastName(steward1.getLastName());
        stewardDTO2.setCountryCode(steward1.getCountryCode());
        stewardDTO2.setPassportNumber(steward1.getPassportNumber());

        when(beanMappingService.mapTo(steward2, StewardDTO.class)).thenReturn(stewardDTO2);
        when(beanMappingService.mapTo(stewardDTO2, Steward.class)).thenReturn(steward2);

        when(beanMappingService.mapTo(List.of(steward1, steward2), StewardDTO.class)).thenReturn(List.of(stewardDTO1, stewardDTO2));
    }

    @Test
    void testCreate() {
        stewardCreateDTO = new StewardCreateDTO();
        stewardCreateDTO.setFirstName(steward1.getFirstName());
        stewardCreateDTO.setLastName(steward1.getLastName());
        stewardCreateDTO.setCountryCode(steward1.getCountryCode());
        stewardCreateDTO.setPassportNumber(steward1.getPassportNumber());

        when(beanMappingService.mapTo(steward1, StewardCreateDTO.class)).thenReturn(stewardCreateDTO);
        when(beanMappingService.mapTo(stewardCreateDTO, Steward.class)).thenReturn(steward1);

        stewardFacade.create(stewardCreateDTO);
        verify(stewardService, times(1)).create(steward1);
    }

    @Test
    void testFindById() {
        when(stewardService.findById(1L)).thenReturn(steward1);

        assertEquals(stewardFacade.findById(1L), stewardDTO1);
        verify(stewardService, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        when(stewardService.findAll()).thenReturn(List.of(steward1, steward2));
        when(beanMappingService.mapTo(List.of(steward1, steward2), StewardDTO.class)).thenReturn(List.of(stewardDTO1, stewardDTO2));

        assertEquals(2, stewardFacade.findAll().size());

        verify(stewardService, times(1)).findAll();
        verify(beanMappingService, times(1)).mapTo(List.of(steward1, steward2), StewardDTO.class);
    }

    @Test
    void testFindByFirstName() {
        when(stewardService.findByFirstName("Jan")).thenReturn(List.of(steward1, steward2));
        when(beanMappingService.mapTo(List.of(steward1, steward2), StewardDTO.class)).thenReturn(List.of(stewardDTO1, stewardDTO2));

        assertEquals(2, stewardFacade.findByFirstName("Jan").size());

        verify(stewardService, times(1)).findByFirstName("Jan");
        verify(beanMappingService, times(1)).mapTo(List.of(steward1, steward2), StewardDTO.class);
    }

    @Test
    void testFindByLastName() {
        when(stewardService.findByLastName("Mrkvicka")).thenReturn(List.of(steward1));
        when(beanMappingService.mapTo(List.of(steward1), StewardDTO.class)).thenReturn(List.of(stewardDTO1));

        assertEquals(1, stewardFacade.findByLastName("Mrkvicka").size());

        verify(stewardService, times(1)).findByLastName("Mrkvicka");
        verify(beanMappingService, times(1)).mapTo(List.of(steward1), StewardDTO.class);
    }

    @Test
    void testFindByCountryCode() {
        when(stewardService.findByCountryCode("CZ")).thenReturn(List.of(steward1, steward2));
        when(beanMappingService.mapTo(List.of(steward1, steward2), StewardDTO.class)).thenReturn(List.of(stewardDTO1, stewardDTO2));

        assertEquals(2, stewardFacade.findByCountryCode("CZ").size());

        verify(stewardService, times(1)).findByCountryCode("CZ");
        verify(beanMappingService, times(1)).mapTo(List.of(steward1, steward2), StewardDTO.class);
    }

    @Test
    void testFindByPassportNumber() {
        when(stewardService.findByPassportNumber("EA33855ED")).thenReturn(List.of(steward1));
        when(beanMappingService.mapTo(List.of(steward1), StewardDTO.class)).thenReturn(List.of(stewardDTO1));

        stewardFacade.findByPassportNumber("EA33855ED");

        verify(stewardService, times(1)).findByPassportNumber("EA33855ED");
        verify(beanMappingService, times(1)).mapTo(List.of(steward1), StewardDTO.class);
    }

    @Test
    void testUpdate() {
        when(stewardService.findByLastName("Sladky")).thenReturn(List.of(steward1));
        when(beanMappingService.mapTo(stewardDTO1, Steward.class)).thenReturn(steward1);

        stewardDTO1.setLastName("Sladky");
        stewardFacade.update(stewardDTO1);

        when(beanMappingService.mapTo(List.of(steward1), StewardDTO.class)).thenReturn(List.of(stewardDTO1));

        assertEquals(1, stewardFacade.findByLastName("Sladky").size());

        verify(stewardService, times(1)).update(steward1);
    }

    @Test
    void testDelete() {
        stewardFacade.delete(1L);
        verify(stewardService, times(1)).delete(1L);
    }
}
