package academy.kata.PP_3_1_4_REST.dao;

import academy.kata.PP_3_1_4_REST.model.Role;
import academy.kata.PP_3_1_4_REST.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public List<User> listUsers() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User showById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> showByEmail(String email) {

        return entityManager
                .createQuery("SELECT u FROM User u WHERE u.email= :email", User.class)
                .setParameter("email", email)
                .getResultList().stream().findAny();
    }

    @Override
    public List<Role> getAllAvailableRoles() {

        return entityManager
                .createQuery("SELECT r FROM Role r", Role.class)
                .getResultList();
    }
}
