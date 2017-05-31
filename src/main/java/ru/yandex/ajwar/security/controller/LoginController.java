package ru.yandex.ajwar.security.controller;

import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.yandex.ajwar.security.utils.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static ru.yandex.ajwar.security.configuration.SpringSecurityRememberMeAnnotation.mapProps;
import static ru.yandex.ajwar.security.utils.Constant.*;
import static ru.yandex.ajwar.security.utils.Util.*;


/**
 * Created by Ajwar on 21.04.2017.
 */
@Controller
public class LoginController {

    @RequestMapping(value = {"/","home","/login"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String loginPage(ModelMap model) {
        propToMap(mapProps,PATH_SERVER_GUI);
        ExecutorService executorService=createAndConfigureExecutorsLoadService();
        AdminController.future=executorService.submit(()->{
            JSONObject obj=new JSONObject();
            obj.put("goal","get_list_servers");
            int port= Integer.parseInt(mapProps.get("port")==null?"5505":mapProps.get("port"));
            AdminController.list=parseResponseToList(sendRequestToServer(SCHEMA,HOST,port,obj));
            //AdminController.managerHost=SCHEMA+"://"+HOST+":"+PORT;
        });
        return "login";
    }

    @RequestMapping(value="/logout", method = {RequestMethod.GET,RequestMethod.POST})
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
    public void propToMap(Map map, String pathProps) {
        Util.PrefixedProperty prop = null;
        try {
            prop = loadProperties(getClass().getResourceAsStream(pathProps));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Map.Entry entry : prop.entrySet()) {
            map.put(entry.getKey(), entry.getValue()) ;
        }
    }
}
