package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.service.impl.UserServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
    public void delUser(HttpServletRequest request, HttpServletResponse response){

        String userName = request.getParameter("user_name");

        if (!userName.isEmpty()){

            Boolean ret = userService.delete(userName);
            String res = ret ? "success" : "fail";

            try {
                PrintWriter writer = response.getWriter();
                writer.write(res);
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 用户登录成功
     * @param ub    用户实例
     */
    public void loginSuccess(UserBean ub){

        userService.update(ub);
    }

    /**
     * 检查用户是否存在
     * @param userName  用户名
     * @param response  response
     */
    @RequestMapping(value="/exist.do", method=RequestMethod.POST)
    public void exist(String userName, HttpServletResponse response){

        // 检查用户名是否存在
        Boolean exist = userService.exist(userName);

        CommonUtil.writeResponse4BooleanResult(exist, response);
    }
}
