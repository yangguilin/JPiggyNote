package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.DailyRecordBean;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.service.impl.DailyRecordServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * 日常账目记录管理
 * Created by guilin on 2014/8/4.
 */
@Controller
@RequestMapping("/daily_record")
public class DailyRecordController extends BaseController {

    @Autowired
    private DailyRecordServiceImpl dailyRecordService;


    /**
     * 显示视图页面
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    public String show(ModelMap model, HttpServletRequest request){

        UserBean ub = getUserFromSession(request);
        model.addAttribute("userBean", ub);

        return "/daily_record";
    }

    /**
     * 添加记录
     * @param drb   记录实例
     * @param response  response
     */
    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public void add(DailyRecordBean drb, HttpServletResponse response){

        Boolean ret = dailyRecordService.add(drb);
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    /**
     * 更新记录
     * @param drb   记录实例
     * @param response  response
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public void update(DailyRecordBean drb, HttpServletResponse response){

        Boolean ret = dailyRecordService.update(drb);
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    /**
     * 删除记录
     * @param id    记录id
     * @param userName  用户名
     * @param response  response
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public void delete(int id, String userName, HttpServletResponse response){

        Boolean ret = dailyRecordService.delete(id, userName);
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }
}
