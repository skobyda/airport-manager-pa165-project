package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.dao.UserDao;
import cz.fi.muni.pa165.project.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Petr Hendrych
 * @created 05.05.2021
 * @project airport-manager
 **/

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserServiceImplTest {

    @MockBean
    private UserDao userDao;

    @InjectMocks
    private final PasswordEncoder encoder = new Argon2PasswordEncoder();

    @InjectMocks
    private final UserService userService = new UserServiceImpl();

    private User user;

    @BeforeEach
    void setUp() {
        user = createUser(1L, "mail@mail.com", "John", "Dean", "Street 2");
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

    @Disabled
    @Test
    void authenticateTest() {
        // TODO implement authentication
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

    private static User createUser(Long id, String email, String name, String surname, String address) {
        User user = new User();

        user.setId(id);
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setAddress(address);

        return user;
    }
}