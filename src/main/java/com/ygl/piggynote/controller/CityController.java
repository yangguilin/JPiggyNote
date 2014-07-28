package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.CityBean;
import com.ygl.piggynote.service.impl.CityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by yanggavin on 14-7-17.
 */
@Controller
@RequestMapping("/city")
public class CityController {

    @Autowired
    private CityServiceImpl csi;

    @RequestMapping(method = RequestMethod.GET)
    public String getCityById(ModelMap model){


        String cityName = "";

        CityBean cb = csi.getCity(17);

        cityName = cb.getName();
        model.addAttribute("cityName", cityName);

        return "city";
    }
}
