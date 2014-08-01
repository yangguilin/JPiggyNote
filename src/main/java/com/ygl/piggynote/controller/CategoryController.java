package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.CategoryBean;
import com.ygl.piggynote.service.impl.CategoryServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by guilin on 2014/8/1.
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;


    /**
     * 添加用户分类
     * @param category
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void add(CategoryBean category, HttpServletResponse response){

        // add category to db.
        Boolean ret = categoryService.add(category);
        // write response content.
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    /**
     * 更新用户分类
     * @param category  用户分类实例
     * @param response  response实例
     */
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public void update(CategoryBean category, HttpServletResponse response){

        // update data to db.
        Boolean ret = categoryService.update(category);
        // write response content.
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    /**
     * 恢复默认用户分类
     * @param userName  用户名
     * @param response  response实例
     */
    @RequestMapping(value="/reset", method = RequestMethod.POST)
    public void reset(String userName, HttpServletResponse response){

        CategoryBean cb = new CategoryBean();
        cb.setUserName(userName);
        cb.fillDefaultData();

        // update data to db.
        Boolean ret = categoryService.update(cb);
        // write response content.
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }
}
