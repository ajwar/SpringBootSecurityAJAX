package ru.yandex.ajwar.security.controller;

import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutorService;

import static ru.yandex.ajwar.security.utils.Util.*;


/**
 * Created by Ajwar on 21.04.2017.
 */
@Controller
public class LoginController {

    @RequestMapping(value = {"/","home","/login"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String loginPage() {
        ExecutorService executorService=createAndConfigureExecutorsLoadService();
        AdminController.future=executorService.submit(()->{
            JSONObject obj=new JSONObject();
            obj.put("goal","get_list_servers");
            AdminController.list=parseResponseToList(sendRequestToServer("http","127.0.0.1",5505,obj));
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
}
