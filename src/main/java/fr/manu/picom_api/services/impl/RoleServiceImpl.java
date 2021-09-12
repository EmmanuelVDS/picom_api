package fr.manu.picom_api.services.impl;

import fr.manu.picom_api.dao.RoleDao;
import fr.manu.picom_api.models.Role;
import fr.manu.picom_api.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }


    @Override
    public void addRole(String role) {
        roleDao.save(new Role(role));
    }

    @Override
    public Role getRole(String role) {
        return roleDao.findFirstByRoleLike("%" + role + "%");
    }

    @Override
    public List<Role> getRoles() {
        return roleDao.findAll();
    }

    @Override
    public Role getRoleByRole(String role) {
        return roleDao.getRoleByRole(role);
    }
}