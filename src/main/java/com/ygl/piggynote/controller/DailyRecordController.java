package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.DailyRecordBean;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.service.impl.DailyRecordServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import com.ygl.piggynote.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public void add(DailyRecordBean drb, HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        if (checkUserLoginStatus(request)) {
            ret = dailyRecordService.add(drb);
        }
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    /**
     * 添加记录（记录日期可调）
     * @param drb   记录实例
     * @param index 日期偏差数值：昨天=-1，前天=-2
     * @param response  response
     */
    @RequestMapping(value="/add2.do", method=RequestMethod.POST)
    public void addByDate(DailyRecordBean drb, int index, HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        if (checkUserLoginStatus(request)) {
            // 如果有日期参数，调整创建日期参数
            if (index < 0) {
                drb.setCreateDate(DateUtil.getDateByIndex(index));
            }
            ret = dailyRecordService.addByCreateDate(drb);
        }
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    /**
     * 更新记录
     * @param drb   记录实例
     * @param response  response
     */
    @RequestMapping(value = "/update.do", method = RequestMethod.POST)
    public void update(DailyRecordBean drb, HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        if (checkUserLoginStatus(request)) {
            ret = dailyRecordService.update(drb);
        }
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    /**
     * 删除记录
     * @param id    记录id
     * @param userName  用户名
     * @param response  response
     */
    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    public void delete(int id, String userName, HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        if (checkUserLoginStatus(request)) {
            ret = dailyRecordService.delete(id, userName);
        }
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }
}
