package ru.yandex.ajwar.security.model;

/**
 * Created by Ajwar on 26.04.2017.
 */
public enum Role {
    USER, ADMIN;

    Role() {
    }

    @Override
    public String toString() {
        return "ROLE_" + super.toString();
    }
}
