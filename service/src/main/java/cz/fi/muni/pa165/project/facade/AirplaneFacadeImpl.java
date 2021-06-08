package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.AirplaneCreateDTO;
import cz.fi.muni.pa165.project.dto.AirplaneDTO;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.enums.AirplaneType;
import cz.fi.muni.pa165.project.service.AirplaneService;
import cz.fi.muni.pa165.project.service.BeanMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Petr Hendrych
 **/

@Service
@Transactional
public class AirplaneFacadeImpl implements AirplaneFacade {

    private final AirplaneService airplaneService;
    private final BeanMappingService beanMappingService;

    @Autowired
    public AirplaneFacadeImpl(AirplaneService airplaneService, BeanMappingService beanMappingService) {
        this.airplaneService = airplaneService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public List<AirplaneDTO> findAll() {
        return beanMappingService.mapTo(airplaneService.findAll(), AirplaneDTO.class);
    }

    @Override
    public AirplaneDTO findById(Long id) {
        Airplane airplane = airplaneService.findById(id);
        return (airplane == null) ? null : beanMappingService.mapTo(airplane, AirplaneDTO.class);
    }

    @Override
    public void create(AirplaneCreateDTO airplane) {
        Airplane mappedAirplane = beanMappingService.mapTo(airplane, Airplane.class);
        airplaneService.create(mappedAirplane);
    }

    @Override
    public void update(AirplaneDTO airplane) {
        Airplane mappedAirplane = beanMappingService.mapTo(airplane, Airplane.class);
        airplaneService.update(mappedAirplane);
    }

    @Override
    public void delete(Long id) {
        airplaneService.delete(id);
    }

    @Override
    public List<AirplaneDTO> findWithBiggerOrEqualCapacity(Integer capacity) {
        List<Airplane> airplanes = airplaneService.findWithBiggerOrEqualCapacity(capacity);
        return beanMappingService.mapTo(airplanes, AirplaneDTO.class);
    }

    @Override
    public List<AirplaneDTO> findWithLowerOrEqualCapacity(Integer capacity) {
        List<Airplane> airplanes = airplaneService.findWithLowerOrEqualCapacity(capacity);
        return beanMappingService.mapTo(airplanes, AirplaneDTO.class);
    }

    @Override
    public List<AirplaneDTO> findByType(AirplaneType type) {
        List<Airplane> airplanes = airplaneService.findByType(type);
        return beanMappingService.mapTo(airplanes, AirplaneDTO.class);
    }
}
