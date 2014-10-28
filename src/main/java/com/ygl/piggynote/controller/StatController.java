package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.*;
import com.ygl.piggynote.enums.MoneyTypeEnum;
import com.ygl.piggynote.service.impl.CustomConfigServiceImpl;
import com.ygl.piggynote.service.impl.DailyRecordServiceImpl;
import com.ygl.piggynote.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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

            // 获取用户月计划花费
            CustomConfigBean ccb = customConfigService.get(ub.getUserName());
            model.addAttribute("monthCostPlan", ccb.getMonthCostPlan());
        } else {

            // 修改跳转链接为首页
            try {
                response.sendRedirect("/");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "/home";
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

        // 查询并统计本月数据
        queryAndStatCurMonthData(userName, sd);

        // 查询并统计月份数据
        queryAndStatMonthsData(userName, sd);

        // 查询垫付收回数据
        queryAndStatPrepayData(userName, sd);

        return sd;
    }

    /**
     * 查询并统计垫付部分数据
     * @param userName  用户名
     * @param sd    统计数据实例
     */
    private void queryAndStatPrepayData(String userName, StatData sd) {


    }

    /**
     * 查询并统计月份数据（含垫付部分数据）
     * @param userName  用户名
     * @param sd    统计数据对象
     */
    private void queryAndStatMonthsData(String userName, StatData sd) {

        // 获取用户所有数据
        List<DailyRecordBean> allRecords = dailyRecordService.getAllRecords(userName);

        // 将数据按照月份保存
        HashMap<String, List<DailyRecordBean>> monthDataMap = new HashMap<String, List<DailyRecordBean>>();
        String monthStr = "";
        for (DailyRecordBean drb : allRecords){

            monthStr = DateUtil.getMonthStr(drb.getCreateDate());

            // 月份不存在，添加新数组
            if (!monthDataMap.containsKey(monthStr)){
                monthDataMap.put(monthStr, new ArrayList<DailyRecordBean>());
            }
            monthDataMap.get(monthStr).add(drb);
        }

        // 统计月份数据
        float mFinalIncomeTotal = 0.0f;
        float mFinalCostTotal = 0.0f;
        float mFinalPrepayTotal = 0.0f;
        float mFinalPaybackTotal = 0.0f;
        List<MonthStatData> monthStatDataList = new ArrayList<MonthStatData>();
        List<MonthPrepayStatData> monthPrepayStatDataList = new ArrayList<MonthPrepayStatData>();
        Iterator iterator = monthDataMap.entrySet().iterator();
        while (iterator.hasNext()){

            Map.Entry<String, List<DailyRecordBean>> entry = (Map.Entry<String, List<DailyRecordBean>>)iterator.next();

            String mTitle = entry.getKey();
            float mCostTotal = 0.0f;
            float mIncomeTotal = 0.0f;
            float mOtherCostTotal = 0.0f;
            float mOtherIncomeTotal = 0.0f;
            float mPrepayTotal = 0.0f;
            float mPaybackTotal = 0.0f;
            List<DailyRecordBean> detailList = new ArrayList<DailyRecordBean>();
            List<DailyRecordBean> prepayDetailList = new ArrayList<DailyRecordBean>();

            // 数据统计
            for (DailyRecordBean record : entry.getValue()){

                MoneyTypeEnum mt = record.getMoneyType();
                float amount = record.getAmount();

                // 累加
                if (mt == MoneyTypeEnum.COST){
                    mCostTotal += amount;
                } else if (mt == MoneyTypeEnum.INCOME){
                    mIncomeTotal += amount;
                } else if (mt == MoneyTypeEnum.PREPAY){

                    mPrepayTotal += amount;
                    prepayDetailList.add(record);
                } else if (mt == MoneyTypeEnum.PAYBACK){

                    mPaybackTotal += amount;
                    prepayDetailList.add(record);
                }

                // 含备注记录（仅对收入和支出项，不含垫付和收回项）
                if (mt == MoneyTypeEnum.INCOME || mt == MoneyTypeEnum.COST) {

                    if (!record.getRemark().isEmpty()) {
                        detailList.add(record);
                    } else {

                        // 按类型累加无备注记录合计
                        if (mt == MoneyTypeEnum.COST) {
                            mOtherCostTotal += amount;
                        } else if (mt == MoneyTypeEnum.INCOME) {
                            mOtherIncomeTotal += amount;
                        }
                    }
                }
            }

            // 全部数据累加
            mFinalCostTotal += mCostTotal;
            mFinalIncomeTotal += mIncomeTotal;
            mFinalPrepayTotal += mPrepayTotal;
            mFinalPaybackTotal += mPaybackTotal;

            // 赋值按月份统计数据
            MonthStatData msd = new MonthStatData();
            msd.setMonth(mTitle);
            msd.setTotal(mIncomeTotal - mCostTotal);
            msd.setCostTotal(mCostTotal);
            msd.setIncomeTotal(mIncomeTotal);
            msd.setDetailList(detailList);
            msd.setOtherCostTotal(mOtherCostTotal);
            msd.setOtherIncomeTotal(mOtherIncomeTotal);

            monthStatDataList.add(msd);

            // 赋值按月份统计垫付数据
            MonthPrepayStatData mpsd = new MonthPrepayStatData();
            mpsd.setMonth(mTitle);
            mpsd.setPrepayTotal(mPrepayTotal);
            mpsd.setPaybackTotal(mPaybackTotal);
            mpsd.setTotal(mPaybackTotal - mPrepayTotal);
            mpsd.setDetailList(prepayDetailList);

            monthPrepayStatDataList.add(mpsd);
        }

        // 按日期降序排序数组
        sortMonthStatList(monthStatDataList);
        sortMonthPrepayStatList(monthPrepayStatDataList);

        // 赋值统计数据实例
        sd.setFinalCostTotal(mFinalCostTotal);
        sd.setFinalIncomeTotal(mFinalIncomeTotal);
        sd.setFinalTotal(mFinalIncomeTotal - mFinalCostTotal);
        sd.setFinalPrepayTotal(mFinalPrepayTotal);
        sd.setFinalPaybackTotal(mFinalPaybackTotal);
        sd.setMonthStatDataList(monthStatDataList);
        sd.setMonthPrepayStatDataList(monthPrepayStatDataList);
    }

    /**
     * 按日期降序排序数组（按月统计）
     * @param monthStatDataList 月份统计数据列表
     */
    private void sortMonthStatList(List<MonthStatData> monthStatDataList) {
        Collections.sort(monthStatDataList, new Comparator<MonthStatData>() {
            @Override
            public int compare(MonthStatData msd1, MonthStatData msd2) {
                return msd2.getMonth().compareTo(msd1.getMonth());
            }
        });
    }

    /**
     * 按日期降序排序数组（预付统计）
     * @param monthPrepayStatDataList 月份预付统计数据列表
     */
    private void sortMonthPrepayStatList(List<MonthPrepayStatData> monthPrepayStatDataList) {
        Collections.sort(monthPrepayStatDataList, new Comparator<MonthPrepayStatData>() {
            @Override
            public int compare(MonthPrepayStatData msd1, MonthPrepayStatData msd2) {
                return msd2.getMonth().compareTo(msd1.getMonth());
            }
        });
    }

    /**
     * 查询并统计本月数据
     * @param userName  用户名
     * @param sd    统计数据对象
     */
    private void queryAndStatCurMonthData(String userName, StatData sd) {

        //
        // 获取用户本月份记录列表
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String beginDate = year + "-" + month + "-01";
        String endDate = year + "-" + (month + 1) + "-01";
        List<DailyRecordBean> curMonthDataList = dailyRecordService.getRecordsByDateRange(beginDate, endDate, userName);

        float curMonthOtherCostTotal = 0.0f;
        float curMonthCostTotal = 0.0f;
        float curMonthIncomeTotal = 0.0f;
        float curMonthPrepayTotal = 0.0f;
        float curMonthPayBackTotal = 0.0f;

        int curMonthCostCount = 0;
        List<DailyRecordBean> curMonthCostList = new ArrayList<DailyRecordBean>();
        List<DailyRecordBean> curMonthIncomeList = new ArrayList<DailyRecordBean>();
        for (DailyRecordBean drb : curMonthDataList){

            MoneyTypeEnum moneyType = drb.getMoneyType();
            float amount = drb.getAmount();
            if (moneyType == MoneyTypeEnum.COST || moneyType == MoneyTypeEnum.PREPAY) {

                // 根据备注金额进行不同的数据累加
                if (!drb.getRemark().isEmpty()) {
                    curMonthCostList.add(drb);
                } else {
                    curMonthOtherCostTotal += amount;
                }

                // 累加总数
                curMonthCostTotal += amount;
                curMonthCostCount++;

                // 垫付部分累加
                if (moneyType == MoneyTypeEnum.PREPAY){
                    curMonthPrepayTotal += amount;
                }

            } else if (moneyType == MoneyTypeEnum.INCOME || moneyType == MoneyTypeEnum.PAYBACK){

                // 插入数组
                curMonthIncomeList.add(drb);

                // 累加总数
                curMonthIncomeTotal += amount;

                // 收回部分累加
                if (moneyType == MoneyTypeEnum.PAYBACK){
                    curMonthPayBackTotal += amount;
                }
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
        sd.setCurMonthPrepayTotal(curMonthPrepayTotal);
        sd.setCurMonthPaybackTotal(curMonthPayBackTotal);
    }
}
