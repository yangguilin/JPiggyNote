package com.ygl.piggynote.bean;

import com.ygl.piggynote.enums.MoneyTypeEnum;
import com.ygl.piggynote.enums.StatTypeEnum;

import java.util.Date;

/**
 * 日常账目记录Bean
 * Created by yanggavin on 14-7-18.
 */
public class DailyRecordBean {

    private int id;
    private String userName;
    private MoneyTypeEnum moneyType;
    private StatTypeEnum statType;
    private String categoryId;
    private String categoryName;
    private float amount;
    private String remark;
    private Date createDate;
    private Date latestModifiedDate;


    public DailyRecordBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public MoneyTypeEnum getMoneyType() {
        return moneyType;
    }

    public void setMoneyType(MoneyTypeEnum moneyType) {
        this.moneyType = moneyType;
    }

    public StatTypeEnum getStatType() {
        return statType;
    }

    public void setStatType(StatTypeEnum statType) {
        this.statType = statType;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLatestModifiedDate() {
        return latestModifiedDate;
    }

    public void setLatestModifiedDate(Date latestModifiedDate) {
        this.latestModifiedDate = latestModifiedDate;
    }
}
