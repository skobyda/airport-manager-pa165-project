package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.dao.AirplaneDao;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.enums.AirplaneType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Petr Hendrych
 * @created 28.04.2021
 * @project airport-manager
 **/

@Service
public class AirplaneServiceImpl implements AirplaneService {

    @Autowired
    private AirplaneDao airplaneDao;

    @Override
    public List<Airplane> findAll() {
        return airplaneDao.findAll();
    }

    @Override
    public Airplane findById(Long id) {
        return airplaneDao.findById(id);
    }

    @Override
    public void create(Airplane airplane) {
        airplaneDao.create(airplane);
    }

    @Override
    public void delete(Long id) {
        airplaneDao.delete(airplaneDao.findById(id));
    }

    @Override
    public void update(Airplane airplane) {
        airplaneDao.update(airplane);
    }

    @Override
    public List<Airplane> findWithBiggerOrEqualCapacity(Integer capacity) {
        List<Airplane> airplanes = airplaneDao.findAll();
        return airplanes.stream().filter(airplane -> airplane.getCapacity() >= capacity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Airplane> findWithLowerOrEqualCapacity(Integer capacity) {
        List<Airplane> airplanes = airplaneDao.findAll();
        return airplanes.stream().filter(airplane -> airplane.getCapacity() <= capacity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Airplane> findByType(AirplaneType type) {
        return airplaneDao.findByType(type);
    }
}
