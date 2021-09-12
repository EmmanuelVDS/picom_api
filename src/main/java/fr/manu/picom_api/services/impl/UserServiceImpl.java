package fr.manu.picom_api.services.impl;

import fr.manu.picom_api.dao.UserDao;
import fr.manu.picom_api.models.User;
import fr.manu.picom_api.services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findByUserEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User addUser(User user) {

        userDao.save(user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }
}
