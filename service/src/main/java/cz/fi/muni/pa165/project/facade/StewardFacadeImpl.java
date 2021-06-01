package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.StewardCreateDTO;
import cz.fi.muni.pa165.project.dto.StewardDTO;
import cz.fi.muni.pa165.project.dto.StewardFilterDTO;
import cz.fi.muni.pa165.project.entity.Steward;
import cz.fi.muni.pa165.project.service.BeanMappingService;
import cz.fi.muni.pa165.project.service.StewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jozef Vanický
 * @created 27.04.2021
 * @project airport-manager
 **/

@Service
@Transactional
public class StewardFacadeImpl implements StewardFacade {

    @Autowired
    private StewardService stewardService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void create(StewardCreateDTO s) {
        Steward mappedSteward = beanMappingService.mapTo(s, Steward.class);

        Steward steward = new Steward();
        steward.setFirstName(s.getFirstName());
        steward.setLastName(s.getLastName());
        steward.setPassportNumber(s.getPassportNumber());
        steward.setCountryCode(s.getCountryCode());

        stewardService.create(mappedSteward);
    }

    @Override
    public StewardDTO findById(Long id) {
        Steward steward = stewardService.findById(id);
        return (steward == null) ? null : beanMappingService.mapTo(steward, StewardDTO.class);
    }

    @Override
    public List<StewardDTO> findAll(StewardFilterDTO filter) {
        return beanMappingService.mapTo(stewardService.findAll(filter), StewardDTO.class);
    }

    @Override
    public List<StewardDTO> findByFirstName(String firstName) {
        return beanMappingService.mapTo(stewardService.findByFirstName(firstName), StewardDTO.class);
    }

    @Override
    public List<StewardDTO> findByLastName(String lastName) {
        return beanMappingService.mapTo(stewardService.findByLastName(lastName), StewardDTO.class);
    }

    @Override
    public List<StewardDTO> findByCountryCode(String countryCode) {
        return beanMappingService.mapTo(stewardService.findByCountryCode(countryCode), StewardDTO.class);
    }

    @Override
    public List<StewardDTO> findByPassportNumber(String passportNumber) {
        return beanMappingService.mapTo(stewardService.findByPassportNumber(passportNumber), StewardDTO.class);
    }

    @Override
    public void update(StewardDTO s) {
        Steward mappedSteward = beanMappingService.mapTo(s, Steward.class);

        stewardService.update(mappedSteward);
    }

    @Override
    public void delete(Long id) {
        stewardService.delete(id);
    }
}
