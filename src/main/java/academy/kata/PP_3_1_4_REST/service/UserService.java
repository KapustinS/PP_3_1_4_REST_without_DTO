package academy.kata.PP_3_1_4_REST.service;


import academy.kata.PP_3_1_4_REST.model.Role;
import academy.kata.PP_3_1_4_REST.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(User user);

    List<User> listUsers();

    User showById(int id);

    void update(User user, int id);

    void delete(int id);

    Optional<User> showByEmail(String email);

    List<Role> getAllAvailableRoles();
}
