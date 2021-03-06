package ru.yandex.ajwar.security.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.concurrent.Future;

import static ru.yandex.ajwar.security.configuration.SpringSecurityRememberMeAnnotation.mapProps;
import static ru.yandex.ajwar.security.utils.Constant.NAME_INTERVAL_PROP;
import static ru.yandex.ajwar.security.utils.Util.getPrincipal;

@Controller
public class AdminController {

    public static List list = null;
    public static Future future = null;

    @RequestMapping(value = "/admin", method = {RequestMethod.GET, RequestMethod.POST})
    public String adminPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        while (true) {
            if (future.isDone() || future.isCancelled()) break;
        }
        String interval = mapProps.get(NAME_INTERVAL_PROP);
        model.addAttribute(NAME_INTERVAL_PROP, interval);
        model.addAttribute("list", list);
        return "admin";
    }

}