package ru.yandex.ajwar.security.dao;

import org.springframework.stereotype.Service;
import ru.yandex.ajwar.security.model.Role;
import ru.yandex.ajwar.security.model.User;
import ru.yandex.ajwar.security.utils.Util;

import java.io.IOException;

import static ru.yandex.ajwar.security.utils.Util.loadProperties;

@Service
public class UserDao implements IUserDao{


    @Override
    public User getUserByLogin(String login) {
        //File file=new File("repository.prop");
        Util.PrefixedProperty prop=null;
        User user=new User();
        try {
            prop= loadProperties("repository.prop");
        } catch (IOException e) {
            e.printStackTrace();
        }
        int n = prop.size()/3;
        for (int i = 0; i <n ; i++) {
            if(prop.getProperty("user",i,"login").equals(login)){
                user.setLogin(login);
                user.setPassword(prop.getProperty("user",i,"password"));
                user.setRole(Role.valueOf(prop.getProperty("user",i,"role").toUpperCase()));
            }
        }
        return user;
        //return new User("ajwar",new BCryptPasswordEncoder().encode("ra0843766"), Role.ADMIN);
    }
}

