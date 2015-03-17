package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.common.CommonConstant;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by guilin on 2014/8/5.
 */
public class BaseController {

    protected static final String ERROR_MSG_KEY = "errorMsg";

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

    /**
     * 获取基于应用程序的url绝对路径
     * @param request   request
     * @param url   相对url
     * @return  绝对url
     */
    public final String getAppBaseUrl(HttpServletRequest request, String url){

        Assert.hasLength(url, "url不能为空");
        Assert.isTrue(url.startsWith("/"), "必须以/开头");
        return request.getContextPath() + url;
    }
}
