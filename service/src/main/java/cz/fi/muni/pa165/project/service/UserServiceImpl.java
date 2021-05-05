package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.dao.UserDao;
import cz.fi.muni.pa165.project.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Petr Hendrych
 * @created 04.05.2021
 * @project airport-manager
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    private final PasswordEncoder encoder = new Argon2PasswordEncoder();

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public boolean authenticate(User u, String password) {
        return encoder.matches(password, u.getPasswordHash());
    }

    @Override
    public User findUserById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }
}
