package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.ServiceTestsConfiguration;
import cz.fi.muni.pa165.project.dao.UserDao;
import cz.fi.muni.pa165.project.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * @author Petr Hendrych
 **/

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = ServiceTestsConfiguration.class)
class UserServiceImplTest {

    @InjectMocks
    private final PasswordEncoder encoder = new Argon2PasswordEncoder();

    @Autowired
    private UserService userService;
    @MockBean
    private UserDao userDao;
    private User user;

    @BeforeEach
    void setUp() {
        user = createUser(
                1L,
                "mail@mail.com",
                "frenchFry8",
                "John",
                "Dean",
                "Street 2"
        );
    }

    @Test
    void registerUserTest() {
        userService.registerUser(user, "frenchFry8");
        verify(userDao).create(user);
    }

    @Test
    void getAllUsersTest() {
        when(userDao.findAll()).thenReturn(List.of(user));

        userService.getAllUsers();

        User u = new User();

        when(userDao.findAll()).thenReturn(List.of(user, u));

        userService.getAllUsers();

        verify(userDao, times(2)).findAll();
    }

    @Test
    void authenticateTest() {
        userService.registerUser(user, "frenchFry8");

        boolean authenticated = userService.authenticate(user, "frenchFry8");

        assertTrue(authenticated);
    }

    @Test
    void findUserByIdTest() {
        when(userDao.findById(1L)).thenReturn(user);

        User u = userService.findUserById(1L);
        assertEquals(u, user);

        verify(userDao).findById(1L);
    }

    @Test
    void findUserByEmailTest() {
        when(userDao.findUserByEmail("mail@mail.com")).thenReturn(user);

        User u = userService.findUserByEmail("mail@mail.com");
        assertEquals(u, user);

        verify(userDao).findUserByEmail("mail@mail.com");
    }

    private User createUser(Long id, String email, String password, String name, String surname, String address) {
        User user = new User();

        user.setId(id);
        user.setEmail(email);
        user.setPasswordHash(encoder.encode(password));
        user.setName(name);
        user.setSurname(surname);
        user.setAddress(address);

        return user;
    }
}