package ru.yandex.ajwar.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.ajwar.security.dao.IUserDao;
import ru.yandex.ajwar.security.model.User;

@Service("userService")
public class UserService implements IUserService {

    @Autowired
    private IUserDao dao;

    @Override
    public User getUserByLogin(String login) {
        return dao.getUserByLogin(login);
    }
}
