package fr.manu.picom_api.services;

import fr.manu.picom_api.models.Role;

import java.util.List;

public interface RoleService {
    void addRole(String role);

    Role getRole(String role);

    List<Role> getRoles();

    Role getRoleByRole(String role);
}
