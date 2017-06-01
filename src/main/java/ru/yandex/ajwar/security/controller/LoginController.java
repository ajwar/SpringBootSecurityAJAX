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
import java.net.InetAddress;
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

    @RequestMapping(value = {"/", "home", "/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String loginPage(ModelMap model) {
        propToMap(mapProps, PATH_SERVER_GUI);
        ExecutorService executorService = createAndConfigureExecutorsLoadService();
        AdminController.future = executorService.submit(() -> {
            JSONObject obj = new JSONObject();
            obj.put("goal", "get_list_servers");
            String protocol = mapProps.get(NAME_PROTOCOL_PROP);
            String host = mapProps.get(NAME_HOST_PROP);
            int port = Integer.parseInt(mapProps.get(NAME_PORT_PROP));
            AdminController.list = parseResponseToList(sendRequestToServer(protocol, host, port, obj));
        });
        return "login";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
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
            Object str = null;
            switch (entry.getKey().toString()) {
                case NAME_PORT_PROP:
                    str = entry.getValue() == null ? "5505" : entry.getValue();
                    break;
                case NAME_HOST_PROP:
                    str = entry.getValue() == null ? InetAddress.getLoopbackAddress().getHostAddress() : entry.getValue();
                    break;
                case NAME_PROTOCOL_PROP:
                    str = entry.getValue() == null ? "HTTP" : entry.getValue();
                    break;
                case NAME_INTERVAL_PROP:
                    str = entry.getValue() == null ? "20000" : entry.getValue();
                    break;
                default:
                    str = "wrong key";
            }
            map.put(entry.getKey(), str);
        }
    }
}
