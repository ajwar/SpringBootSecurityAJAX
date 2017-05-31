package ru.yandex.ajwar.security.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static ru.yandex.ajwar.security.configuration.SpringSecurityRememberMeAnnotation.mapProps;
import static ru.yandex.ajwar.security.utils.Constant.HOST;
import static ru.yandex.ajwar.security.utils.Constant.SCHEMA;
import static ru.yandex.ajwar.security.utils.Util.sendRequestToServer;

/**
 * Created by Ajwar on 30.05.2017.
 */
@Controller
public class RedirectAJAXController {
    @RequestMapping(value = "/redirect", method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody String redirectPage(@RequestBody String string) {
        int port= Integer.parseInt(mapProps.get("port")==null?"5505":mapProps.get("port"));
        Object obj=sendRequestToServer(SCHEMA,HOST,port,new JSONObject(string));

        return obj!=null?obj.toString():"";
    }
}
