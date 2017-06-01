package ru.yandex.ajwar.security.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static ru.yandex.ajwar.security.configuration.SpringSecurityRememberMeAnnotation.mapProps;
import static ru.yandex.ajwar.security.utils.Constant.*;
import static ru.yandex.ajwar.security.utils.Util.sendRequestToServer;


/**
 * Created by Ajwar on 30.05.2017.
 */
@Controller
public class RedirectAJAXController {
    @RequestMapping(value = "/redirect", method = {RequestMethod.GET, RequestMethod.POST})
    public
    @ResponseBody
    String redirectPage(@RequestBody String string) {
        String protocol = mapProps.get(NAME_PROTOCOL_PROP);
        String host = mapProps.get(NAME_HOST_PROP);
        int port = Integer.parseInt(mapProps.get(NAME_PORT_PROP));
        Object obj = sendRequestToServer(protocol, host, port, new JSONObject(string));
        return obj != null ? obj.toString() : "";
    }
}
