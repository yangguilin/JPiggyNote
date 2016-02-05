package com.ygl.piggynote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yanggavin on 16/2/3.
 */
@Controller
@RequestMapping("/aliway")
public class AliwayController {
    @RequestMapping(method = RequestMethod.GET)
    public String index(ModelMap model){
        return "aliway";
    }
}
