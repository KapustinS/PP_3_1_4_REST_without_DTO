package academy.kata.PP_3_1_4_REST.dao;

import academy.kata.PP_3_1_4_REST.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
}
