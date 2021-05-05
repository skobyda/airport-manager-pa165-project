package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.dao.StewardDao;
import cz.fi.muni.pa165.project.entity.Steward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jozef Vanický
 * @created 28.04.2021
 * @project airport-manager
 **/

@Service
public class StewardServiceImpl implements StewardService {

    @Autowired
    private StewardDao stewardDao;

    @Override
    public void create(Steward s) {
        stewardDao.create(s);
    }

    @Override
    public Steward findById(Long id) {
        return stewardDao.findById(id);
    }

    @Override
    public List<Steward> findAll() {
        return stewardDao.findAll();
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
        stewardDao.update(s);
    }

    @Override
    public void delete(Long id) {
        stewardDao.delete(stewardDao.findById(id));
    }
}
