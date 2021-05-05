package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.entity.Steward;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Jozef Vanický
 * @created 09.04.2021
 * @project airport-manager
 **/

@Repository
public class StewardDaoImpl implements StewardDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Steward findById(Long id) {
        return entityManager.find(Steward.class, id);
    }

    @Override
    public List<Steward> findAll() {
        return entityManager.createQuery("select s from Steward s", Steward.class)
                .getResultList();
    }

    @Override
    public void create(Steward s) {
        entityManager.persist(s);
    }

    @Override
    public void delete(Steward s) {
        entityManager.remove(s);
    }

    @Override
    public List<Steward> findByFirstName(String firstName) {
            return entityManager
                    .createQuery("select s from Steward s where s.firstName = :firstName",
                            Steward.class).setParameter("firstName", firstName)
                    .getResultList();
    }

    @Override
    public List<Steward> findByLastName(String lastName) {
            return entityManager
                    .createQuery("select s from Steward s where s.lastName = :lastName",
                            Steward.class).setParameter("lastName", lastName)
                    .getResultList();
    }

    @Override
    public List<Steward> findByCountryCode(String countryCode) {
            return entityManager
                    .createQuery("select s from Steward s where s.countryCode = :countryCode",
                            Steward.class).setParameter("countryCode", countryCode)
                    .getResultList();
    }

    @Override
    public List<Steward> findByPassportNumber(String passportNumber) {
            return entityManager
                    .createQuery("select s from Steward s where s.passportNumber = :passportNumber",
                            Steward.class).setParameter("passportNumber", passportNumber)
                    .getResultList();
    }

    @Override
    public void update(Steward s){
        entityManager.merge(s);
    }
}
