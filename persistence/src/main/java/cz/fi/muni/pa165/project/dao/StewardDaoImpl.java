package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.dto.StewardFilterDTO;
import cz.fi.muni.pa165.project.entity.Steward;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
        Steward steward = entityManager.find(Steward.class, id);
        if (steward == null)
            throw new AirportManagerException("Steward entity with this id does not exists");
        return steward;
    }

    @Override
    public List<Steward> findAll(StewardFilterDTO filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Steward> cq = cb.createQuery(Steward.class);
        Predicate predicate = cb.and();

        Root<Steward> steward = cq.from(Steward.class);


        if (filter != null) {
            if (filter.getFirstName() != null) {
                predicate = cb.and(predicate, cb.equal(steward.get("firstName"), filter.getFirstName()));
            }

            if (filter.getLastName() != null) {
                predicate = cb.and(predicate, cb.equal(steward.get("lastName"), filter.getLastName()));
            }

            if (filter.getCountryCode() != null) {
                predicate = cb.and(predicate, cb.equal(steward.get("countryCode"), filter.getCountryCode()));
            }
        }

        TypedQuery<Steward> query = entityManager.createQuery(cq.select(steward).where(predicate));
        return query.getResultList();
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
    public void update(Steward s) {
        entityManager.merge(s);
    }
}
