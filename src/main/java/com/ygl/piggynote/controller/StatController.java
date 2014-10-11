package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.CustomConfigBean;
import com.ygl.piggynote.bean.DailyRecordBean;
import com.ygl.piggynote.bean.StatData;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.enums.MoneyTypeEnum;
import com.ygl.piggynote.service.impl.CustomConfigServiceImpl;
import com.ygl.piggynote.service.impl.DailyRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by yanggavin on 14-10-8.
 */
@Controller
@RequestMapping("/stat")
public class StatController extends BaseController {

    @Autowired
    private DailyRecordServiceImpl dailyRecordService;

    @Autowired
    private CustomConfigServiceImpl customConfigService;


    @RequestMapping(method= RequestMethod.GET)
    public String show(ModelMap model, HttpServletRequest request, HttpServletResponse response){

        // user
        UserBean ub = getUserFromSession(request);
        model.addAttribute("curUser", ub);

        if (ub != null) {

            // 查询并统计用户记录数据
            StatData sd = queryAndStatUserData(ub.getUserName());
            model.addAttribute("statData", sd);
        }

        return "stat";
    }


    /**
     * 查询并统计用户记录数据
     * @param userName  用户名
     * @return  统计数据实例
     */
    private StatData queryAndStatUserData(String userName){

        StatData sd = new StatData();

        // 获取用户本月份记录列表
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String beginDate = year + "-" + month + "-01";
        String endDate = year + "-" + (month + 1) + "-01";
        List<DailyRecordBean> curMonthDataList = dailyRecordService.getRecordsByDateRange(beginDate, endDate, userName);

        // 获取用户自定义备注金额
        CustomConfigBean ccb = customConfigService.get(userName);
        float remarkAmount = ccb.getRemarkAmount();

        float curMonthOtherCostTotal = 0.0f;
        float curMonthCostTotal = 0.0f;
        float curMonthIncomeTotal = 0.0f;
        int curMonthCostCount = 0;
        List<DailyRecordBean> curMonthCostList = new ArrayList<DailyRecordBean>();
        List<DailyRecordBean> curMonthIncomeList = new ArrayList<DailyRecordBean>();
        for (DailyRecordBean drb : curMonthDataList){

            MoneyTypeEnum moneyType = drb.getMoneyType();
            float amount = drb.getAmount();
            if (moneyType == MoneyTypeEnum.COST || moneyType == MoneyTypeEnum.PREPAY) {

                // 根据备注金额进行不同的数据累加
                if (amount >= remarkAmount) {
                    curMonthCostList.add(drb);
                } else {
                    curMonthOtherCostTotal += amount;
                }

                // 累加总数
                curMonthCostTotal += amount;
                curMonthCostCount++;

            } else if (moneyType == MoneyTypeEnum.INCOME || moneyType == MoneyTypeEnum.PAYBACK){

                // 插入数组
                curMonthIncomeList.add(drb);

                // 累加总数
                curMonthIncomeTotal += amount;
            }
        }

        // 赋值统计数据实例
        sd.setCurMonthCostList(curMonthCostList);
        sd.setCurMonthIncomeList(curMonthIncomeList);
        sd.setCurMonthCostTotal(curMonthCostTotal);
        sd.setCurMonthIncomeTotal(curMonthIncomeTotal);
        sd.setCurMonthOtherCostTotal(curMonthOtherCostTotal);
        sd.setCurMonthCostCount(curMonthCostCount);
        sd.setCurMonthIncomeCount(curMonthIncomeList.size());

        return sd;
    }
}
