package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by guilin on 2014/8/7.
 */
@Controller
public class RegisterController extends BaseController {

    @Autowired
    private UserService userService;


    /**
     * 页面视图显示
     * @return
     */
    @RequestMapping(value="/register", method=RequestMethod.GET)
    public String show(){

        return "/register";
    }

    /**
     * 用户注册
     * @param ub    用户实例
     * @param request   request
     * @return  页面视图
     */
    @RequestMapping(value="/register.do", method= RequestMethod.POST)
    public ModelAndView register(UserBean ub, HttpServletRequest request){

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/success");
        try{

            userService.add(ub);
        } catch(Exception e){

            mav.addObject(ERROR_MSG_KEY, "用户名已经存在，请选择其它的名字");
            mav.setViewName("forward:/register");
        }
        setUserToSession(request, ub);

        return mav;
    }
}
