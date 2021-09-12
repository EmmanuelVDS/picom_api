package fr.manu.picom_api.dao;

import fr.manu.picom_api.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, Long> {

    Role findFirstByRoleLike(String nom);

    Role getRoleByRole(String role);
}

