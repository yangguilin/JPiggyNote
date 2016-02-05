package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.go.MonsterBean;
import com.ygl.piggynote.service.GoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by yanggavin on 15/8/18.
 */
@Controller
@RequestMapping("/go")
public class GoController {
    @Autowired
    private GoService goService;

    @RequestMapping(method = RequestMethod.GET)
    public String show(ModelMap model){

        List<MonsterBean> monsterList = goService.loadMonsterData();

        model.addAttribute("msg", "I'm here!");
        return "go";
    }
}
