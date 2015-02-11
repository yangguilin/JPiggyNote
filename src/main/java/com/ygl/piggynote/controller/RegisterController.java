package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.CategoryBean;
import com.ygl.piggynote.bean.CustomConfigBean;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.service.UserService;
import com.ygl.piggynote.service.impl.CategoryServiceImpl;
import com.ygl.piggynote.service.impl.CustomConfigServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import com.ygl.piggynote.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by guilin on 2014/8/7.
 */
@Controller
public class RegisterController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomConfigServiceImpl customConfigService;

    @Autowired
    private CategoryServiceImpl categoryService;


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
        mav.setViewName("/register_success");

        // 添加页面参数
        mav.addObject("title", "注册新用户");
        mav.addObject("msgContent", "恭喜您成功注册新的小猪账号！");

        // 用户密码md5加密
        String newPsw = Md5Util.getMD5Code(ub.getPassword());
        ub.setPassword(newPsw);

        try{
            // 添加新用户
            userService.add(ub);

            // 添加默认自定义设定
            CustomConfigBean ccb = new CustomConfigBean();
            ccb.setUserName(ub.getUserName());
            ccb.fillDefaultData();
            customConfigService.add(ccb);

            // 添加默认分类数据
            CategoryBean cb = new CategoryBean();
            cb.setUserName(ub.getUserName());
            cb.fillDefaultData();
            categoryService.add(cb);
        } catch(Exception e){

            mav.addObject(ERROR_MSG_KEY, "用户名已经存在，请选择其它的名字");
            mav.setViewName("forward:/register");
        }
        // 保存到session
        setUserToSession(request, ub);

        return mav;
    }

    /**
     * 用户注册
     * @param ub    用户实例
     */
    @RequestMapping(value="/simple_register.do", method= RequestMethod.POST)
    public void register(UserBean ub, HttpServletResponse response){
        Boolean isSuccess = false;
        String newPsw = Md5Util.getMD5Code(ub.getPassword());
        ub.setPassword(newPsw);
        try{
            isSuccess = userService.add(ub);
            if (isSuccess) {
                CustomConfigBean ccb = new CustomConfigBean();
                ccb.setUserName(ub.getUserName());
                ccb.fillDefaultData();
                customConfigService.add(ccb);
            }
        } catch(Exception e){
            isSuccess = false;
        }
        CommonUtil.writeResponse4BooleanResult(isSuccess, response);
    }
}
