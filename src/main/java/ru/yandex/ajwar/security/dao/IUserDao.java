package ru.yandex.ajwar.security.dao;

import ru.yandex.ajwar.security.model.User;

/**
 * Created by Ajwar on 26.04.2017.
 */
public interface IUserDao {
    User getUserByLogin(String login);
}
