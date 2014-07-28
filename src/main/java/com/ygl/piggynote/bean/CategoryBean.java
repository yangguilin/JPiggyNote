package com.ygl.piggynote.bean;

import java.util.Date;

/**
 * 账目类型Bean
 * Created by yanggavin on 14-7-18.
 */
public class CategoryBean {

    private String userName;
    private String categoryXml;
    private String categoryXmlSorted;
    private Date latestModifiedDate;


    public CategoryBean() {}

    public Date getLatestModifiedDate() {
        return latestModifiedDate;
    }

    public void setLatestModifiedDate(Date latestModifiedDate) {
        this.latestModifiedDate = latestModifiedDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCategoryXml() {
        return categoryXml;
    }

    public void setCategoryXml(String categoryXml) {
        this.categoryXml = categoryXml;
    }

    public String getCategoryXmlSorted() {
        return categoryXmlSorted;
    }

    public void setCategoryXmlSorted(String categoryXmlSorted) {
        this.categoryXmlSorted = categoryXmlSorted;
    }
}
