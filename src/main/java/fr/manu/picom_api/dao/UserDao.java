package fr.manu.picom_api.dao;

import fr.manu.picom_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {

    User findByEmail(String email);

}