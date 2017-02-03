package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.CustomConfigBean;
import com.ygl.piggynote.bean.DailyRecordBean;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.common.CommonConstant;
import com.ygl.piggynote.enums.MoneyTypeEnum;
import com.ygl.piggynote.service.impl.CustomConfigServiceImpl;
import com.ygl.piggynote.service.impl.DailyRecordServiceImpl;
import com.ygl.piggynote.util.CookieUtil;
import com.ygl.piggynote.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

    @Autowired
    private DailyRecordServiceImpl dailyRecordService;

    @Autowired
    private CustomConfigServiceImpl customConfigService;

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

        // user
        UserBean ub = getUserFromSession(request);
        model.addAttribute("curUser", ub);

        if (ub != null) {

            // 保存信息到cookie
            int  loginMaxAge = 30*24*60*60;   //定义账户密码的生命周期，这里是一个月。单位为秒
            CookieUtil.addCookie(response, CommonConstant.COOKIE_USER_NAME, ub.getUserName(), loginMaxAge);

            // 3 days history records
            String beginDateStr = DateUtil.getDateStr(0, 0, -2);
            String endDateStr = DateUtil.getDateStr(0, 0, 1);
            List<DailyRecordBean> records = dailyRecordService.getRecordsByDateRange(beginDateStr, endDateStr, ub.getUserName());

            List<DailyRecordBean> todayList = new ArrayList<DailyRecordBean>();
            List<DailyRecordBean> yesterdayList = new ArrayList<DailyRecordBean>();
            List<DailyRecordBean> dayBeforeYesterdayList = new ArrayList<DailyRecordBean>();

            String todayStr = DateUtil.getDateStr(0, 0, 0);
            String yesterdayStr = DateUtil.getDateStr(0, 0, -1);
            String dayBeforeYesterdayStr = DateUtil.getDateStr(0, 0, -2);
            for (DailyRecordBean drb : records){
                String createDateStr = DateUtil.getShortDateStr(drb.getCreateDate());
                if (createDateStr.equals(todayStr)){
                    todayList.add(drb);
                } else if (createDateStr.equals(yesterdayStr)){
                    yesterdayList.add(drb);
                } else if (createDateStr.equals(dayBeforeYesterdayStr)){
                    dayBeforeYesterdayList.add(drb);
                }
            }

            model.addAttribute("todayList", todayList);
            model.addAttribute("yesterdayList", yesterdayList);
            model.addAttribute("dayBeforeYesterdayList", dayBeforeYesterdayList);

            // money type map
            HashMap<MoneyTypeEnum, String> moneyTypeMap = new HashMap<MoneyTypeEnum, String>();
            moneyTypeMap.put(MoneyTypeEnum.COST, "支出");
            moneyTypeMap.put(MoneyTypeEnum.INCOME, "收入");
            moneyTypeMap.put(MoneyTypeEnum.PREPAY, "垫付");
            moneyTypeMap.put(MoneyTypeEnum.PAYBACK, "收回");
            model.addAttribute("moneyTypeMap", moneyTypeMap);

            // config
            CustomConfigBean ccb = customConfigService.get(ub.getUserName());
            model.addAttribute("customConfig", ccb);
            return "home";
        } else {
            // cookie中读取保存的用户名
            Cookie userNameCookie = CookieUtil.getCookieByName(request, CommonConstant.COOKIE_USER_NAME);
            if (userNameCookie != null && userNameCookie.getValue() != ""){
                String userNameInCookie = "";
                try {
                    userNameInCookie = URLDecoder.decode(userNameCookie.getValue(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                model.addAttribute("userNameInCookie", userNameInCookie);
            }
            return "login";
        }
	}
}