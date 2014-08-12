package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.CustomConfigBean;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.service.impl.CategoryServiceImpl;
import com.ygl.piggynote.service.impl.CustomConfigServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by guilin on 2014/8/1.
 */
@Controller
@RequestMapping("/custom_config")
public class CustomConfigController extends BaseController{

    @Autowired
    private CustomConfigServiceImpl ccService;

    @RequestMapping(method=RequestMethod.GET)
    public String show(HttpServletRequest request, ModelMap model){

        CustomConfigBean ccb = null;

        // 获取用户自定义配置实例
        UserBean ub = getUserFromSession(request);
        if (ub != null) {
            ccb = ccService.get(ub.getUserName());
        }

        // 放入视图map
        model.addAttribute("customConfig", ccb);

        return "/custom_config";
    }

    /**
     * 添加自定义配置
     * @param ccb   实例
     * @param response  response
     */
    @RequestMapping(value="/add.do", method= RequestMethod.POST)
    public void add(CustomConfigBean ccb, HttpServletResponse response){

        // add category to db.
        Boolean ret = ccService.add(ccb);
        // write response content.
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    /**
     * 更新自定义配置
     * @param ccb   实例
     * @param response  response
     */
    @RequestMapping(value="/update.do", method=RequestMethod.POST)
    public void update(CustomConfigBean ccb, HttpServletResponse response){

        // add category to db.
        Boolean ret = ccService.update(ccb);
        // write response content.
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    /**
     * 重置自定义配置
     * @param userName  用户名
     * @param response  response
     */
    @RequestMapping(value="/reset.do", method=RequestMethod.POST)
    public void reset(String userName, HttpServletResponse response){

        CustomConfigBean ccb = new CustomConfigBean();
        ccb.setUserName(userName);
        ccb.fillDefaultData();

        // add category to db.
        Boolean ret = ccService.update(ccb);
        // write response content.
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }
}
