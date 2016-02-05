package com.ygl.piggynote.bean;

import java.util.List;

/**
 * 月份垫付统计数据实体类
 * Created by yanggavin on 14/10/24.
 */
public class MonthPrepayStatData {

    /**
     * 月份标题，形如：2014年10月
     */
    private String month;
    /**
     * 本月垫付合计
     */
    private float prepayTotal;
    /**
     * 本月收回合计
     */
    private float paybackTotal;
    /**
     * 本月盈余
     */
    private float total;
    /**
     * 本月垫付相关记录列表
     */
    private List<DailyRecordBean> detailList;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public float getPrepayTotal() {
        return prepayTotal;
    }

    public void setPrepayTotal(float prepayTotal) {
        this.prepayTotal = prepayTotal;
    }

    public float getPaybackTotal() {
        return paybackTotal;
    }

    public void setPaybackTotal(float paybackTotal) {
        this.paybackTotal = paybackTotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<DailyRecordBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DailyRecordBean> detailList) {
        this.detailList = detailList;
    }
}
