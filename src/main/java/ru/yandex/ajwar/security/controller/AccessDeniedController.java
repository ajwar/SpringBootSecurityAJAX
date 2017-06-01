package ru.yandex.ajwar.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static ru.yandex.ajwar.security.utils.Util.getPrincipal;

/**
 * Created by Ajwar on 21.04.2017.
 */
@Controller
public class AccessDeniedController {
    @RequestMapping(value = "/Access_Denied", method = {RequestMethod.GET, RequestMethod.POST})
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("user", getPrincipal());
        return "accessDenied";
    }
}
