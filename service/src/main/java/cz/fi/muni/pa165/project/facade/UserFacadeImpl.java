package cz.fi.muni.pa165.project.facade;

import cz.fi.muni.pa165.project.dto.UserAuthenticateDTO;
import cz.fi.muni.pa165.project.dto.UserDTO;
import cz.fi.muni.pa165.project.entity.User;
import cz.fi.muni.pa165.project.service.BeanMappingService;
import cz.fi.muni.pa165.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Petr Hendrych
 * @created 04.05.2021
 * @project airport-manager
 **/

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;

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
    public boolean authenticate(UserAuthenticateDTO u) {
        User user = userService.findUserById(u.getId());
        return userService.authenticate(user, u.getPassword());
    }
}
