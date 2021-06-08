package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.entity.User;
import cz.fi.muni.pa165.project.exceptions.AirportManagerException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Petr Hendrych
 **/

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(User u) {
        em.persist(u);
    }

    @Override
    public void delete(User u) {
        em.remove(u);
    }

    @Override
    public User findById(Long id) {
        User user = em.find(User.class, id);
        if (user == null)
            throw new AirportManagerException("User entity with this id does not exists");
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Cannot search for null e-mail");

        try {
            return em
                    .createQuery("select u from User u where u.email = :email",
                            User.class).setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public void update(User u) {
        em.merge(u);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class)
                .getResultList();
    }
}
