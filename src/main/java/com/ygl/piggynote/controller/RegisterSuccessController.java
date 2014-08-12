package com.ygl.piggynote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by guilin on 2014/8/8.
 */
@Controller
@RequestMapping("/register_success")
public class RegisterSuccessController {

    @RequestMapping(method= RequestMethod.GET)
    public String show(ModelMap model){

        return "/register_success";
    }
}
