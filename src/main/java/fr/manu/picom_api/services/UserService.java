package fr.manu.picom_api.services;

import fr.manu.picom_api.models.User;

import java.util.List;

public interface UserService {
    User findByUserEmail(String email);

    User addUser(User user);

    List<User> getUsers();
}
