package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.project.dto.UserDTO;
import cz.fi.muni.pa165.project.entity.User;
import cz.fi.muni.pa165.project.service.BeanMappingService;
import cz.fi.muni.pa165.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.List;

/**
 * @author Petr Hendrych
 **/

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final BeanMappingService beanMappingService;

    @Autowired
    public UserFacadeImpl(UserService userService, BeanMappingService beanMappingService) {
        this.userService = userService;
        this.beanMappingService = beanMappingService;
    }

    @Override
    public void registerUser(UserDTO u, String password) {
        User user = beanMappingService.mapTo(u, User.class);
        userService.registerUser(user, password);
        u.setId(user.getId());
    }

    @Override
    public UserDTO findUserById(Long id) {
        User user = userService.findUserById(id);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        User user = userService.findUserByEmail(email);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return beanMappingService.mapTo(users, UserDTO.class);
    }

    @Override
    public UserDTO authenticate(UserAuthenticateDTO u) throws AuthenticationException {
        User user = userService.findUserByEmail(u.getEmail());
        if (user != null) {
            boolean authenticated = userService.authenticate(user, u.getPassword());
            if (authenticated) {
                return beanMappingService.mapTo(user, UserDTO.class);
            }
        }
        throw new AuthenticationException("Unauthorized");
    }
}
