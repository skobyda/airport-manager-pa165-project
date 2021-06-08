package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.dao.StewardDao;
import cz.fi.muni.pa165.project.dto.StewardFilterDTO;
import cz.fi.muni.pa165.project.entity.Steward;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jozef Vanický
 **/

@Service
public class StewardServiceImpl implements StewardService {

    private final StewardDao stewardDao;

    @Autowired
    public StewardServiceImpl(StewardDao stewardDao) {
        this.stewardDao = stewardDao;
    }

    @Override
    public void create(Steward s) {

        try {
            stewardDao.create(s);
        } catch (IllegalArgumentException e) {
            throw new AirportManagerException("Steward with this passport number already exists");
        }
    }

    @Override
    public Steward findById(Long id) {
        return stewardDao.findById(id);
    }

    @Override
    public List<Steward> findAll(StewardFilterDTO filter) {
        return stewardDao.findAll(filter);
    }

    @Override
    public List<Steward> findByFirstName(String firstName) {
        return stewardDao.findByFirstName(firstName);
    }

    @Override
    public List<Steward> findByLastName(String lastName) {
        return stewardDao.findByLastName(lastName);
    }

    @Override
    public List<Steward> findByCountryCode(String countryCode) {
        return stewardDao.findByCountryCode(countryCode);
    }

    @Override
    public List<Steward> findByPassportNumber(String passportNumber) {
        return stewardDao.findByPassportNumber(passportNumber);
    }

    @Override
    public void update(Steward s) {

        try {
            stewardDao.update(s);
        } catch (IllegalArgumentException e) {
            throw new AirportManagerException("Steward with this ID does not exists");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            stewardDao.delete(stewardDao.findById(id));
        } catch (IllegalArgumentException e) {
            throw new AirportManagerException("Steward entity does not exists in system");
        }
    }

    private List<Steward> findWithFilter(StewardFilterDTO filter) {
        List<Steward> result = new ArrayList<>();

        if (filter.getFirstName() != null) {
            result.addAll(stewardDao.findByFirstName(filter.getFirstName()));
        }

        if (filter.getLastName() != null) {
            result.addAll(stewardDao.findByLastName(filter.getLastName()));
        }

        if (filter.getCountryCode() != null) {
            result.addAll(stewardDao.findByCountryCode(filter.getCountryCode()));
        }

        return result.stream().distinct().collect(Collectors.toList());
    }
}
