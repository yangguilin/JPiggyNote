package com.ygl.piggynote.filter;


import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.common.CommonConstant;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户权是否登录过滤器
 * Created by guilin on 2014/8/5.
 */
@Service("filter_auth")
public class AuthFilter implements Filter {

    /**
     * 无需进行用户信息验证的链接数组
     */
    private static final String[] NO_NEED_AUTH_URIS = {
            "/",
            "/login.do",
            "/register",
            "/user/exist.do",
            "/simple_register.do",
            "/error",
            "/login_ajax.do",
            "/stock",
            "/stock/save_cookie.do",
            "/stock/get_cookie.do",
            "/snwt/user/register.do",
            "/snwt/user/verification.do",
            "/fcmg/add_record.do",
            "/fcmg/get_top10.do"
    };


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 执行过滤
     * @param servletRequest    request
     * @param servletResponse   response
     * @param filterChain   filterChain
     * @throws IOException  IOException
     * @throws ServletException ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse)servletResponse;

        // 检查是否不需要检查用户信息
        if (noNeedToAuthUser(httpRequest) == false) {

            // 从session中获取用户信息
            UserBean ub = (UserBean)httpRequest.getSession().getAttribute(CommonConstant.SESSION_USER_CONTENT);

            // 根据用户信息是否存在进行跳转
            if (ub == null) {
                httpResponse.sendRedirect("/");
            }
        }

        // let it go
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }


    /**
     * 是否属于不必进行用户信息验证的请求
     * @param request   request
     * @return  true:不需要验证;false:需要验证
     */
    public Boolean noNeedToAuthUser(HttpServletRequest request) {

        String reqUri = request.getRequestURI();

        // 系统某些页面无需进行用户信息检查
        for (String uri : NO_NEED_AUTH_URIS){

            if (reqUri != null && reqUri.equalsIgnoreCase(uri)){
                return true;
            }
        }

        // 对js,css,jpg等文件不进行用户信息检查
        Pattern p = Pattern.compile("^/.*\\.(js|css|jpg|png)$");
        Matcher matcher = p.matcher(reqUri);

        return matcher.matches();
    }
}
