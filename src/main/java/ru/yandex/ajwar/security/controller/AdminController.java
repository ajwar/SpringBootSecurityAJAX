package ru.yandex.ajwar.security.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.concurrent.Future;

import static ru.yandex.ajwar.security.utils.Util.getPrincipal;

@Controller
public class AdminController {

	public static List list;
	public static Future future;

	@RequestMapping(value = "/admin", method = {RequestMethod.GET,RequestMethod.POST})
	public String adminPage(ModelMap model) {
		model.addAttribute("user", getPrincipal());
		while (true){
			if (future.isDone() || future.isCancelled()) break;
		}
		model.addAttribute("list",list);

		return "admin";
	}

}