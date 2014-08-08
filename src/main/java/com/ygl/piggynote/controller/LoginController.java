package com.ygl.piggynote.controller;

import com.mysql.jdbc.StringUtils;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.common.CommonConstant;
import com.ygl.piggynote.service.impl.UserServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 用户登录控制器
 * Created by guilin on 2014/8/5.
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value="/login", method= RequestMethod.GET)
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

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/login");

        if (!userService.exist(ub.getUserName())){
            mav.addObject(ERROR_MSG_KEY, "用户名不存在");
        } else {

            // 数据库查询用户
            UserBean dbUser = userService.get(ub.getUserName());

            if (!dbUser.getPassword().equals(ub.getPassword())) {
                mav.addObject(ERROR_MSG_KEY, "用户密码不正确");
            } else {

                // 最后登录时间
                dbUser.setLatestLoginDate(new Date());
                // 保存到session
                setUserToSession(request, dbUser);
                // 之前请求的链接
                String toUrl = (String) request.getSession().getAttribute(CommonConstant.LOGIN_TO_URL);
                request.getSession().removeAttribute(CommonConstant.LOGIN_TO_URL);

                // 如果当前会话中没有保存登录之前的请求URL，则直接跳转到主页
                if (toUrl == null || toUrl.isEmpty()) {
                    toUrl = "/";
                }
                mav.setViewName("redirect:" + toUrl);
            }
        }

        return mav;
    }

    /**
     * 用户注销
     * @param session   session
     * @param response  response
     */
    @RequestMapping(value = "/logout.do", method = RequestMethod.POST)
    public void logout(HttpSession session, HttpServletResponse response){

        // 去除session
        session.removeAttribute(CommonConstant.SESSION_USER_CONTENT);
        // 返回操作结果
        CommonUtil.writeResponse4BooleanResult(true, response);
    }
}
