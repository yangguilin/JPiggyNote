package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户操作服务控制器
 * Created by yanggavin on 14-7-18.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 添加用户
     * @param ub    用户表单实例
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addUser(UserBean ub){

        // check form content


        // add to db
        userService.add(ub);

        return "user/user";
    }

    /**
     * 更新用户信息
     * @param ub    用户表单实例
     * @return
     */
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public String updateUser(UserBean ub){

        // check form content

        // update to db
        userService.update(ub);

        return "user/update";
    }

    /**
     * 删除用户
     * @param request
     * @return
     */
    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public String delUser(HttpServletRequest request){

        String userName = request.getParameter("user_name");

        return "user/delete";
    }
}
