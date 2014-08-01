package com.ygl.piggynote.bean;

/**
 * 用户自定义配置Bean
 * Created by yanggavin on 14-7-18.
 */
public class CustomConfigBean {

    private String userName;
    private float monthCostPlan;


    public CustomConfigBean() {
    }

    public float getMonthCostPlan() {
        return monthCostPlan;
    }

    public void setMonthCostPlan(float monthCostPlan) {
        this.monthCostPlan = monthCostPlan;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 系统默认数据
     */
    public void fillDefaultData(){
        this.monthCostPlan = 3000l;
    }
}
