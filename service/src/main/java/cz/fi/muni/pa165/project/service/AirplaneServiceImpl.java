package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.dao.AirplaneDao;
import cz.fi.muni.pa165.project.entity.Airplane;
import cz.fi.muni.pa165.project.enums.AirplaneType;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
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

        try {
            airplaneDao.create(airplane);
        } catch (IllegalArgumentException e) {
            throw new AirportManagerException("Airplane entity already exists in system");
        }
    }

    @Override
    public void delete(Long id) {

        try {
            airplaneDao.delete(airplaneDao.findById(id));
        } catch (IllegalArgumentException e) {
            throw new AirportManagerException("Airplane with this entity does not exists");
        }
    }

    @Override
    public void update(Airplane airplane) {

        try {
            airplaneDao.update(airplane);
        } catch (IllegalArgumentException e) {
            throw new AirportManagerException("Airplane with same ID does not exists in system");
        }
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
