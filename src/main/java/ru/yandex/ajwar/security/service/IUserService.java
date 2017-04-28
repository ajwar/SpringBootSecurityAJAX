package ru.yandex.ajwar.security.service;


import ru.yandex.ajwar.security.model.User;

public interface IUserService {

	User getUserByLogin(String login);
}
