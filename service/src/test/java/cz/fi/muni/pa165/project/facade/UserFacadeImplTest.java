package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.project.dto.UserDTO;
import cz.fi.muni.pa165.project.entity.User;
import cz.fi.muni.pa165.project.service.BeanMappingService;
import cz.fi.muni.pa165.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Petr Hendrych
 * @created 05.05.2021
 * @project airport-manager
 **/

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserFacadeImplTest {

    @InjectMocks
    private final UserFacadeImpl userFacade = new UserFacadeImpl();

    @MockBean
    private UserService userService;

    @MockBean
    private BeanMappingService beanMappingService;

    private User user;
    private UserDTO userDTO;
    private UserAuthenticateDTO userAuthenticateDTO;

    @BeforeEach
    void setUp() {
        user = createUser(1L, "xXJohnXx", "mail@mail.com", "John", "Dean", "Street 2");
        userDTO = createUserDTO(1L, user.getEmail(), user.getName(), user.getSurname(), user.getAddress());
        userAuthenticateDTO = createUserAuthenticateDTO(1L, "hranolky8");
    }

    @Test
    void registerUser() {
        when(beanMappingService.mapTo(userDTO, User.class)).thenReturn(user);

        userFacade.registerUser(userDTO, "frenchFry8");

        verify(userService).registerUser(user, "frenchFry8");
    }

    @Test
    void findUserByIdTest() {
        when(beanMappingService.mapTo(user, UserDTO.class)).thenReturn(userDTO);
        when(beanMappingService.mapTo(userDTO, User.class)).thenReturn(user);
        when(userService.findUserById(1L)).thenReturn(user);

        UserDTO user = userFacade.findUserById(1L);

        assertThat(user).isEqualTo(userDTO);
        assertThat(user.getName()).isEqualTo("John");

        verify(userService).findUserById(1L);
    }

    @Test
    void findUserByEmail() {
        when(beanMappingService.mapTo(user, UserDTO.class)).thenReturn(userDTO);
        when(beanMappingService.mapTo(userDTO, User.class)).thenReturn(user);
        when(userService.findUserByEmail("mail@mail.com")).thenReturn(user);

        UserDTO user = userFacade.findUserByEmail("mail@mail.com");

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getSurname()).isEqualTo("Dean");

        verify(userService).findUserByEmail("mail@mail.com");
    }

    @Test
    void getAllUsersTest() {
        when(userService.getAllUsers()).thenReturn(List.of(user));
        when(beanMappingService.mapTo(List.of(user), UserDTO.class)).thenReturn(List.of(userDTO));

        List<UserDTO> users = userFacade.getAllUsers();
        assertThat(users.size()).isEqualTo(1);
        assertThat(users).containsExactly(userDTO);

        verify(userService).getAllUsers();
        verify(beanMappingService).mapTo(List.of(user), UserDTO.class);
    }

    @Test
    void authenticateTest() {
        when(userService.findUserById(1L)).thenReturn(user);
        when(userService.authenticate(user, "hranolky8")).thenReturn(true);

        boolean authenticated = userFacade.authenticate(userAuthenticateDTO);

        assertTrue(authenticated);
    }

    private static User createUser(Long id, String login, String email, String name, String surname, String address) {
        User user = new User();

        user.setId(id);
        user.setLogin(login);
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setAddress(address);

        return user;
    }

    private static UserDTO createUserDTO(Long id, String email, String name, String surname, String address) {
        UserDTO user = new UserDTO();

        user.setId(id);
        user.setEmail(email);
        user.setName(name);
        user.setSurname(surname);
        user.setAddress(address);

        return user;
    }

    private static UserAuthenticateDTO createUserAuthenticateDTO(Long id, String password) {
        UserAuthenticateDTO user = new UserAuthenticateDTO();

        user.setId(id);
        user.setPassword(password);

        return user;
    }
}
