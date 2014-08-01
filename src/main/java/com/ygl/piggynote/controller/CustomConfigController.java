package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.CustomConfigBean;
import com.ygl.piggynote.service.impl.CategoryServiceImpl;
import com.ygl.piggynote.service.impl.CustomConfigServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by guilin on 2014/8/1.
 */
@Controller
@RequestMapping("/custom_config")
public class CustomConfigController {

    @Autowired
    private CustomConfigServiceImpl ccService;

    /**
     * 添加自定义配置
     * @param ccb   实例
     * @param response  response
     */
    @RequestMapping(value="/add", method= RequestMethod.POST)
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
    @RequestMapping(value="/update", method=RequestMethod.POST)
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
    @RequestMapping(value="/reset", method=RequestMethod.POST)
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
