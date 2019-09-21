package fantasticfour.controllers.service;

import fantasticfour.controllers.dao.UserDao;
import fantasticfour.entity.Role;
import fantasticfour.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;


    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.findByUsername(s);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(int id) {
        return userDao.findById(id).orElseThrow(NullPointerException::new);
    }

    public void saveUser(int id, String username, Map<String, String> form) {
        var user = userDao.findById(id).orElseThrow(NullPointerException::new);
        user.setUsername(username);
        user.getRoles().clear();
        user.getRoles().add(Role.valueOf(form.get("role")));
        userDao.save(user);
    }

    public void addUser(User user) {
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    public void blockUser(int userId) {
        var user = userDao.findById(userId).orElseThrow(NullPointerException::new);
        user.getRoles().clear();
        user.getRoles().add(Role.BLOCKED);
        userDao.save(user);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

}
