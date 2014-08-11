package com.ygl.piggynote.bean;

/**
 * 用户自定义配置Bean
 * Created by yanggavin on 14-7-18.
 */
public class CustomConfigBean {

    private String userName;
    private float monthCostPlan;
    private int categorySwitch;
    private int prepaySwitch;


    public CustomConfigBean() {

        this.monthCostPlan = 888f;
        this.categorySwitch = 0;
        this.prepaySwitch = 0;
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

    public int getCategorySwitch() {
        return categorySwitch;
    }

    public void setCategorySwitch(int categorySwitch) {
        this.categorySwitch = categorySwitch;
    }

    public int getPrepaySwitch() {
        return prepaySwitch;
    }

    public void setPrepaySwitch(int prepaySwitch) {
        this.prepaySwitch = prepaySwitch;
    }

    /**
     * 系统默认数据
     */
    public void fillDefaultData(){
        this.monthCostPlan = 3000l;
    }
}
