package cz.fi.muni.pa165.project.dao;

import cz.fi.muni.pa165.project.AirportManagerApplication;
import cz.fi.muni.pa165.project.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Petr Hendrych
 * @created 30.04.2021
 * @project airport-manager
 **/

@SpringBootTest(classes = AirportManagerApplication.class)
@Transactional
class UserDaoImplTest {

    @Autowired
    private UserDaoImpl userDao;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = createUser(
                "john.dean@gmail.com",
                "John",
                "Dean",
                "Open street 113"
        );

        user2 = createUser(
                "linda.huge@hotmail.com",
                "Linda",
                "Huge",
                "Far way street 4"
        );

        userDao.create(user1);
        userDao.create(user2);
    }

    @Test
    void create() {
        assertEquals(2, userDao.findAll().size());
    }

    @Test
    void findById() {
        User foundUser = userDao.findById(user1.getId());

        assertEquals("John", foundUser.getName());
        assertEquals("john.dean@gmail.com", foundUser.getEmail());
    }

    @Test
    void findUserByEmail() {
        User foundUser = userDao.findUserByEmail("linda.huge@hotmail.com");

        assertEquals("Linda", foundUser.getName());
        assertEquals("Huge", foundUser.getSurname());
        assertEquals("Far way street 4", foundUser.getAddress());
    }

    @Test
    void findAll() {
        assertEquals(2, userDao.findAll().size());

        User user3 = createUser(
                "mail@mail.com",
                "Bob",
                "Ross",
                "In our hearts.");

        userDao.create(user3);

        assertEquals(3, userDao.findAll().size());
    }

    private User createUser(String email, String name, String surname, String address) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setAddress(address);
        user.setJoinedDate(new Date());

        return user;
    }
}
