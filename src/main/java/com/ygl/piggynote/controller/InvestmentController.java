package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.InvestmentBean;
import com.ygl.piggynote.bean.InvestmentRecordBean;
import com.ygl.piggynote.bean.InvestmentStatDataBean;
import com.ygl.piggynote.enums.InvestmentTypeEnum;
import com.ygl.piggynote.service.impl.InvestmentServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import com.ygl.piggynote.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * Created by yanggavin on 15/5/12.
 */
@Controller
@RequestMapping("/investment")
public class InvestmentController extends BaseController {
    @Autowired
    private InvestmentServiceImpl investmentService;

    @RequestMapping(method = RequestMethod.GET)
    public String show(ModelMap model, HttpServletRequest request, HttpServletResponse response){
        if (!checkUserLoginStatus(request)){
            redirectToHomePage(response);
        }
        InvestmentStatDataBean isdb = new InvestmentStatDataBean(curLoginUserName);
        isdb.fillDataAndCalculateStatData(investmentService.get(curLoginUserName));
        model.addAttribute("statData", isdb);
        return "investment";
    }

    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
    public void add(InvestmentBean bean, HttpServletRequest request, HttpServletResponse response){
        if (checkUserLoginStatus(request)) {
            Boolean ret = false;
            if (checkFormData4Investment(bean)) {
                bean.setUserName(curLoginUserName);
                bean.setType(InvestmentTypeEnum.DEFAULT);
                bean.setCurrentNum(bean.getPrincipalNum());
                ret = investmentService.add(bean);
            }
            CommonUtil.writeResponse4BooleanResult(ret, response);
        }
    }
    private Boolean checkFormData4Investment(InvestmentBean bean){
        return !(bean.getName().isEmpty() || bean.getPrincipalNum() <= 0);
    }

    @RequestMapping(value = "/add_record.do", method = RequestMethod.POST)
    public void addRecord(InvestmentRecordBean bean, HttpServletRequest request, HttpServletResponse response){
        Boolean ret = false;
        if (checkUserLoginStatus(request)){
            if (checkFormData4InvestmentRecord(bean)){
                int id = Integer.parseInt(request.getParameter("id"));
                InvestmentBean investmentBean = investmentService.get(id);
                if (investmentBean != null && investmentBean.getUserName().equalsIgnoreCase(curLoginUserName)) {
                    bean.setDate(DateUtil.getShortDateStr(new Date()));
                    List<InvestmentRecordBean> records = investmentBean.getRecords();
                    records.add(bean);
                    investmentBean.setCurrentNum(bean.getNum());
                    ret = investmentService.update(investmentBean);
                }
            }
        }
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }
    private Boolean checkFormData4InvestmentRecord(InvestmentRecordBean bean){
        return (bean.getNum() > 0);
    }

    @RequestMapping(value = "/finish_record.do", method = RequestMethod.POST)
    public void finishRecord(HttpServletRequest request, HttpServletResponse response){
        Boolean ret = false;
        if (checkUserLoginStatus(request)){
            int id = Integer.parseInt(request.getParameter("id"));
            InvestmentBean bean = investmentService.get(id);
            if (bean != null && bean.getUserName().equalsIgnoreCase(curLoginUserName)){
                ret = investmentService.finish(bean);
            }
        }
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }

    @RequestMapping(value = "/delete_record.do", method = RequestMethod.POST)
    public void deleteRecord(HttpServletRequest request, HttpServletResponse response){
        Boolean ret = false;
        if (checkUserLoginStatus(request)){
            int id = Integer.parseInt(request.getParameter("id"));
            InvestmentBean bean = investmentService.get(id);
            if (bean != null && bean.getUserName().equalsIgnoreCase(curLoginUserName)){
                ret = investmentService.delete(id, curLoginUserName);
            }
        }
        CommonUtil.writeResponse4BooleanResult(ret, response);
    }
}
