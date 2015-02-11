package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.StockCookieBean;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.service.impl.StockCookieServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yanggavin on 15/1/22.
 */
@Controller
@RequestMapping("/stock")
public class StockController extends BaseController {
    @Autowired
    private StockCookieServiceImpl stockCookieService;

    @RequestMapping(method = RequestMethod.GET)
    public String show(ModelMap model, HttpServletRequest request){
        String userName = "";
        String stockCookieVal = "";
        UserBean ub = getUserFromSession(request);
        if (ub != null) {
            userName = ub.getUserName();
            Boolean exist = stockCookieService.exist(userName);
            if (exist) {
                stockCookieVal = stockCookieService.get(userName).getStockCookie();
            }
        }
        model.addAttribute("userName", userName);
        model.addAttribute("stockCookie", stockCookieVal);
        return "/stock";
    }

    @RequestMapping(value="/save_cookie.do", method=RequestMethod.POST)
    public void saveStockCookie(StockCookieBean stockCookieBean, HttpServletRequest request,  HttpServletResponse response){
        Boolean ret = false;
        UserBean ub = getUserFromSession(request);
        if (stockCookieBean != null && ub != null && ub.getUserName().equals(stockCookieBean.getUserName())){
            if (stockCookieService.exist(ub.getUserName())){
                ret = stockCookieService.update(stockCookieBean);
            } else {
                ret = stockCookieService.add(stockCookieBean);
            }
            CommonUtil.writeResponse4BooleanResult(ret, response);
        }
    }

    @RequestMapping(value="/get_cookie.do")
    public void getStockCookie(HttpServletRequest request, HttpServletResponse response){
        Boolean exist = false;
        String userName = request.getParameter("userName");
        UserBean ub = getUserFromSession(request);
        String stockCookie = "";
        if (ub != null && ub.getUserName().equals(userName)){
            if (stockCookieService.exist(userName)) {
                stockCookie = stockCookieService.get(userName).getStockCookie();
                exist = true;
            }
        }
        CommonUtil.writeResponse4ReturnStrResult(exist, stockCookie, response);
    }
}
