package academy.kata.PP_3_1_4_REST.service;

import academy.kata.PP_3_1_4_REST.dao.RoleDao;
import academy.kata.PP_3_1_4_REST.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getRoles() {
        return roleDao.findAll();
    }
}

