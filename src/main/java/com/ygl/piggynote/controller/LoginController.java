package com.ygl.piggynote.controller;

import com.ygl.piggynote.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by guilin on 2014/8/5.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value="", method= RequestMethod.GET)
    public String showLoginPage(HttpServletRequest request){


        return "/login";
    }

    @RequestMapping(value="login.do", method=RequestMethod.POST)
    public void login(){

    }
}
