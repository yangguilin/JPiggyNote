package com.ygl.piggynote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yanggavin on 15/1/22.
 */
@Controller
@RequestMapping
public class StockController {

    @RequestMapping(value="/stock", method = RequestMethod.GET)
    public String show(){
        return "/stock";
    }
}
