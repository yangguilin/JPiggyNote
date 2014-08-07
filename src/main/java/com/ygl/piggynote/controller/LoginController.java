package com.ygl.piggynote.controller;

import com.mysql.jdbc.StringUtils;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.common.CommonConstant;
import com.ygl.piggynote.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 用户登录控制器
 * Created by guilin on 2014/8/5.
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(method= RequestMethod.GET)
    public String showLoginPage(HttpServletRequest request){

        return "/login";
    }

    /**
     * 用户登录
     * @param ub    用户实例
     * @param request   request
     * @return  页面视图
     */
    @RequestMapping(value="/login.do", method=RequestMethod.POST)
    public ModelAndView login(UserBean ub, HttpServletRequest request){

        UserBean dbUser = userService.get(ub.getUserName());
        ModelAndView mav = new ModelAndView();
        mav.setViewName("forward:/login");

        if (dbUser == null){
            mav.addObject(ERROR_MSG_KEY, "用户名不存在");
        } else if (dbUser.getPassword().equals(ub.getPassword())){
            mav.addObject(ERROR_MSG_KEY, "用户密码不正确");
        } else {

            dbUser.setLatestLoginDate(new Date());
            setUserToSession(request, dbUser);
            String toUrl = (String)request.getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
            request.getSession().removeAttribute(CommonConstant.LOGIN_TO_URL);

            // 如果当前会话中没有保存登录之前的请求URL，则直接跳转到主页
            if (toUrl.isEmpty()){
                toUrl = "/home";
            }
            mav.setViewName("redirect:" + toUrl);
        }

        return mav;
    }

    /**
     * 用户注销
     * @param session   session
     * @return  视图名称
     */
    @RequestMapping(value = "/logout.do", method = RequestMethod.POST)
    public String logout(HttpSession session){

        session.removeAttribute(CommonConstant.SESSION_USER_CONTENT);
        return "forward:/index";
    }
}
