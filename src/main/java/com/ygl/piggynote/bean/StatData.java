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
     * 本月日常支出合计
     */
    private float curMonthDailyCostTotal;
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
     * 本月份垫付合计
     */
    private float curMonthPrepayTotal;
    /**
     * 本月份收回合计
     */
    private float curMonthPaybackTotal;
    /**
     * 全部收入支出盈余
     */
    private float finalTotal;
    /**
     * 全部收入总计
     */
    private float finalIncomeTotal;
    /**
     * 全部支出总计
     */
    private float finalCostTotal;
    /**
     * 全部垫付总计
     */
    private float finalPrepayTotal;
    /**
     * 全部收回总计
     */
    private float finalPaybackTotal;
    /**
     * 本月份收入列表
     */
    private List<DailyRecordBean> curMonthIncomeList;
    /**
     * 按月份统计数据列表
     */
    private List<MonthStatData> monthStatDataList;
    /**
     * 按月份统计垫付数据列表
     */
    private List<MonthPrepayStatData> monthPrepayStatDataList;


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

    public float getCurMonthPrepayTotal() {
        return curMonthPrepayTotal;
    }

    public void setCurMonthPrepayTotal(float curMonthPrepayTotal) {
        this.curMonthPrepayTotal = curMonthPrepayTotal;
    }

    public float getCurMonthPaybackTotal() {
        return curMonthPaybackTotal;
    }

    public void setCurMonthPaybackTotal(float curMonthPayBackTotal) {
        this.curMonthPaybackTotal = curMonthPayBackTotal;
    }

    public List<MonthStatData> getMonthStatDataList() {
        return monthStatDataList;
    }

    public void setMonthStatDataList(List<MonthStatData> monthStatDataList) {
        this.monthStatDataList = monthStatDataList;
    }

    public float getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(float finalTotal) {
        this.finalTotal = finalTotal;
    }

    public float getFinalIncomeTotal() {
        return finalIncomeTotal;
    }

    public void setFinalIncomeTotal(float finalIncomeTotal) {
        this.finalIncomeTotal = finalIncomeTotal;
    }

    public float getFinalCostTotal() {
        return finalCostTotal;
    }

    public void setFinalCostTotal(float finalCostTotal) {
        this.finalCostTotal = finalCostTotal;
    }

    public float getFinalPrepayTotal() {
        return finalPrepayTotal;
    }

    public void setFinalPrepayTotal(float finalPrepayTotal) {
        this.finalPrepayTotal = finalPrepayTotal;
    }

    public float getFinalPaybackTotal() {
        return finalPaybackTotal;
    }

    public void setFinalPaybackTotal(float finalPaybackTotal) {
        this.finalPaybackTotal = finalPaybackTotal;
    }

    public List<MonthPrepayStatData> getMonthPrepayStatDataList() {
        return monthPrepayStatDataList;
    }

    public void setMonthPrepayStatDataList(List<MonthPrepayStatData> monthPrepayStatDataList) {
        this.monthPrepayStatDataList = monthPrepayStatDataList;
    }

    public float getCurMonthDailyCostTotal() {
        return curMonthDailyCostTotal;
    }

    public void setCurMonthDailyCostTotal(float curMonthDailyCostTotal) {
        this.curMonthDailyCostTotal = curMonthDailyCostTotal;
    }

    public StatData(){

    }
}
