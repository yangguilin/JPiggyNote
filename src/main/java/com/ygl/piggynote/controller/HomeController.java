package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.UserBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model, HttpServletRequest request) {

        UserBean ub = getUserFromSession(request);

        model.addAttribute("curUser", ub);
        model.addAttribute("un", "");

		return "home";
	}
}