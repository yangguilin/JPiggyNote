package com.ygl.piggynote.bean;

import java.util.Date;

/**
 * Created by yanggavin on 15/2/9.
 */
public class StockCookieBean {
    private String userName;
    private String stockCookie;
    private Date savedDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStockCookie() {
        return stockCookie;
    }

    public void setStockCookie(String stockCookie) {
        this.stockCookie = stockCookie;
    }

    public Date getSavedDate() {
        return savedDate;
    }

    public void setSavedDate(Date savedDate) {
        this.savedDate = savedDate;
    }
}
