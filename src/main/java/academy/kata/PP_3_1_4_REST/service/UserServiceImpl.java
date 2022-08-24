package academy.kata.PP_3_1_4_REST.service;

import academy.kata.PP_3_1_4_REST.dao.UserDao;
import academy.kata.PP_3_1_4_REST.model.Role;
import academy.kata.PP_3_1_4_REST.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    @Override
    public void add(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDao.add(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    public User showById(int id) {
        return userDao.showById(id);
    }

    @Transactional
    @Override
    public void update(User user, int id) {
        user.setId(id);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDao.update(user);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public Optional<User> showByEmail(String email) {
        return userDao.showByEmail(email);
    }

    @Override
    public List<Role> getAllAvailableRoles() {
        System.out.println(userDao.getAllAvailableRoles());
        return userDao.getAllAvailableRoles();
    }

}
