package com.ygl.piggynote.bean;

import java.util.List;

/**
 * 统计数据实体类
 * Created by yanggavin on 14-10-10.
 */
public class StatData {

    /**
     * 本月份收入记录数
     */
    private int curMonthIncomeCount;
    /**
     * 本月份支出记录数
     */
    private int curMonthCostCount;
    /**
     * 本月份收入合计
     */
    private float curMonthIncomeTotal;
    /**
     * 本月份支出合计
     */
    private float curMonthCostTotal;
    /**
     * 本月份支出列表（金额等于或超过备注金额的部分）
     */
    private List<DailyRecordBean> curMonthCostList;
    /**
     * 本月份其他支出项合计（金额小于备注金额的部分）
     */
    private float curMonthOtherCostTotal;
    /**
     * 本月份收入列表
     */
    private List<DailyRecordBean> curMonthIncomeList;


    public int getCurMonthIncomeCount() {
        return curMonthIncomeCount;
    }

    public void setCurMonthIncomeCount(int curMonthIncomeCount) {
        this.curMonthIncomeCount = curMonthIncomeCount;
    }

    public int getCurMonthCostCount() {
        return curMonthCostCount;
    }

    public void setCurMonthCostCount(int curMonthCostCount) {
        this.curMonthCostCount = curMonthCostCount;
    }

    public float getCurMonthIncomeTotal() {
        return curMonthIncomeTotal;
    }

    public void setCurMonthIncomeTotal(float curMonthIncomeTotal) {
        this.curMonthIncomeTotal = curMonthIncomeTotal;
    }

    public float getCurMonthCostTotal() {
        return curMonthCostTotal;
    }

    public void setCurMonthCostTotal(float curMonthCostTotal) {
        this.curMonthCostTotal = curMonthCostTotal;
    }

    public List<DailyRecordBean> getCurMonthCostList() {
        return curMonthCostList;
    }

    public void setCurMonthCostList(List<DailyRecordBean> curMonthCostList) {
        this.curMonthCostList = curMonthCostList;
    }

    public float getCurMonthOtherCostTotal() {
        return curMonthOtherCostTotal;
    }

    public void setCurMonthOtherCostTotal(float curMonthOtherCostTotal) {
        this.curMonthOtherCostTotal = curMonthOtherCostTotal;
    }

    public List<DailyRecordBean> getCurMonthIncomeList() {
        return curMonthIncomeList;
    }

    public void setCurMonthIncomeList(List<DailyRecordBean> curMonthIncomeList) {
        this.curMonthIncomeList = curMonthIncomeList;
    }


    public StatData(){

    }
}
