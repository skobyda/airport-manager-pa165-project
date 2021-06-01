package cz.fi.muni.pa165.project.service;

import cz.fi.muni.pa165.project.dao.UserDao;
import cz.fi.muni.pa165.project.entity.User;
import cz.fi.muni.pa165.project.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
    public void registerUser(User u, String password) {
        u.setPasswordHash(encoder.encode(password));
        userDao.create(u);
    }

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

    @Override
    public boolean verifyRole(String basicAuth, UserRole role) {
        if (basicAuth != null && basicAuth.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = basicAuth.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);

            User u = this.findUserByEmail(values[0]);
            if (u == null) {
                return false;
            }

            if (!this.authenticate(u, values[1])) {
                return false;
            }

            return this.checkRole(u, role);
        }

        return false;
    }

    private boolean checkRole(User u, UserRole role) {
        if (u.getRole() == UserRole.AIRPORT_MANAGER) {
            return true;
        }
        return u.getRole() == role;
    }
}
