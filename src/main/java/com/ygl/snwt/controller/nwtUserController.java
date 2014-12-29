package com.ygl.snwt.controller;

import com.ygl.piggynote.controller.BaseController;
import com.ygl.piggynote.util.CommonUtil;
import com.ygl.snwt.bean.NwtUser;
import com.ygl.snwt.service.impl.NwtUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.*;

/**
 * Created by yanggavin on 14/12/11.
 */
@Controller
@RequestMapping("/snwt")
public class NwtUserController extends BaseController {

    @Autowired
    private NwtUserServiceImpl nwtUserService;


    /**
     * 页面显示
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String show(){

        return "snwt/snwt_login";
    }

    /**
     * 用户登录
     * @param user user
     * @return  用户首页链接
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String login(NwtUser user){

        String pageName = "snwt/snwt_login";

        NwtUser queryUser = nwtUserService.exist(user.getName(), user.getPassword());
        if (queryUser != null){
            pageName = "snwt/snwt_home";
        }

        return pageName;
    }

    /**
     * 注册用户
     * @param user   request
     * @param response  response
     */
    @RequestMapping(value="/register.do", method= RequestMethod.POST)
    public void register(NwtUser user, HttpServletResponse response){

        Boolean success = true;

        // check form values
        if (user.getName().isEmpty()
                || user.getYear().isEmpty()
                || user.getMobilePhone().isEmpty()){

            success = false;
        } else {
            success = nwtUserService.add(user);
        }

        // write result to response
        CommonUtil.writeResponse4BooleanResult(success, response);
    }

    /**
     * 用户验证
     * @param user  用户实例
     */
    @RequestMapping(value="/verification.do", method = RequestMethod.POST)
    public void verification(NwtUser user, HttpServletResponse response){

        Boolean success = false;
        String userId = "";

        // check user from db
        NwtUser queryUser = nwtUserService.exist(user.getName(), user.getPassword());
        if (queryUser != null){
            success = true;
            userId = queryUser.getId() + "";
        }

        // response
        CommonUtil.writeResponse4ReturnStrResult(success, userId, response);
    }
}
