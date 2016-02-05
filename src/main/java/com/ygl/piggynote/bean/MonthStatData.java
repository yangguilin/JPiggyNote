package com.ygl.piggynote.bean;

import java.util.List;

/**
 * 月份统计数据实体类
 * Created by yanggavin on 14/10/21.
 */
public class MonthStatData {

    /**
     * 月份标题，形如：2014年10月
     */
    private String month;
    /**
     * 本月盈余，可为正负
     */
    private float total;
    /**
     * 支出合计
     */
    private float costTotal;
    /**
     * 收入合计
     */
    private float incomeTotal;
    /**
     * 无备注支出合计
     */
    private float otherCostTotal;
    /**
     * 无备注收入合计
     */
    private float otherIncomeTotal;
    /**
     * 详细记录列表
     */
    private List<DailyRecordBean> detailList;



    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(float costTotal) {
        this.costTotal = costTotal;
    }

    public float getIncomeTotal() {
        return incomeTotal;
    }

    public void setIncomeTotal(float incomeTotal) {
        this.incomeTotal = incomeTotal;
    }

    public List<DailyRecordBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DailyRecordBean> detailList) {
        this.detailList = detailList;
    }

    public float getOtherCostTotal() {
        return otherCostTotal;
    }

    public void setOtherCostTotal(float otherCostTotal) {
        this.otherCostTotal = otherCostTotal;
    }

    public float getOtherIncomeTotal() {
        return otherIncomeTotal;
    }

    public void setOtherIncomeTotal(float otherIncomeTotal) {
        this.otherIncomeTotal = otherIncomeTotal;
    }
}
