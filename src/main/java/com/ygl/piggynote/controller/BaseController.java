package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.common.CommonConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by guilin on 2014/8/5.
 */
public class BaseController {

    protected static final String ERROR_MSG_KEY = "errorMsg";
    public String curLoginUserName = "";
    public int curLoginUserId = -1;

    /**
     * 从Session中获取用户实例
     * @param request   request
     * @return  用户实例
     */
    protected UserBean getUserFromSession(HttpServletRequest request){
        return (UserBean)request.getSession().getAttribute(CommonConstant.SESSION_USER_CONTENT);
    }

    /**
     * 保存用户实例到Session
     * @param request   request
     * @param user  用户实例
     */
    protected void setUserToSession(HttpServletRequest request, UserBean user) {
        HttpSession session = request.getSession();
        session.setAttribute(CommonConstant.SESSION_USER_CONTENT, user);
        session.setMaxInactiveInterval(CommonConstant.SESSION_KEEP_SECOND);
    }

    public Boolean checkUserLoginStatus(HttpServletRequest request){
        Boolean beLogin = false;
        UserBean ub = getUserFromSession(request);
        if (ub != null){
            curLoginUserName = ub.getUserName();
            curLoginUserId = ub.getId();
            beLogin = true;
        }
        return beLogin;
    }

    public void redirectToHomePage(HttpServletResponse response){
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
