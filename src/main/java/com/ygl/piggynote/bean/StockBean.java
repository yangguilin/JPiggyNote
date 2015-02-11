package com.ygl.piggynote.bean;

import java.util.Date;

/**
 * Created by yanggavin on 15/2/9.
 */
public class StockBean {
    private int id;
    private int userId;
    private String stockCode;
    private String stockName;
    private float buyPrice;
    private float gainPrice;
    private float losePrice;
    private Date buyDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String code) {
        this.stockCode = code;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String name) {
        this.stockName = name;
    }

    public float getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(float buyPrice) {
        this.buyPrice = buyPrice;
    }

    public float getGainPrice() {
        return gainPrice;
    }

    public void setGainPrice(float gainPrice) {
        this.gainPrice = gainPrice;
    }

    public float getLosePrice() {
        return losePrice;
    }

    public void setLosePrice(float losePrice) {
        this.losePrice = losePrice;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }
}
