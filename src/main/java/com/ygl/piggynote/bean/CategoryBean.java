package com.ygl.piggynote.bean;

import com.google.gson.Gson;

import java.util.Date;

/**
 * 账目类型Bean
 * Created by yanggavin on 14-7-18.
 */
public class CategoryBean {

    private String userName;
    private String categoryData;
    private String categoryDataSorted;
    private Date latestModifiedDate;
    private CategoryList categoryList;


    public CategoryBean() {
    }

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

    public String getCategoryData() {

        // 如果没有生成json字符串，通过分类列表生成
        if (categoryData == null || categoryData.isEmpty()){
            getJsonFromCategoryList();
        }

        return this.categoryData;
    }

    public void setCategoryData(String categoryData) {
        this.categoryData = categoryData;
    }

    public String getCategoryDataSorted() {
        return categoryDataSorted;
    }

    public void setCategoryDataSorted(String categoryDataSorted) {
        this.categoryDataSorted = categoryDataSorted;
    }

    public CategoryList getGetCategoryList() {

        // 如果没有分类列表，通过json字符串实例化
        if (categoryList == null){
            getCategoryListFromJson();
        }

        return categoryList;
    }

    /**
     * 填充默认用户分类数据
     */
    public void fillDefaultData() {

        this.categoryData = "";
        this.categoryDataSorted = "";
        // 默认分类数据
        this.categoryList = new CategoryList();
        this.categoryList.defaultData();
    }

    /**
     * 转化Json字符串为categoryList实例
     */
    private void getCategoryListFromJson(){

        if(categoryData != null && !categoryData.isEmpty()){

            Gson gson = new Gson();
            this.categoryList = gson.fromJson(categoryData, CategoryList.class);
        }
    }

    /**
     * 转化categoryList为Json字符串
     */
    private void getJsonFromCategoryList(){

        if (categoryList != null) {

            Gson gson = new Gson();
            this.categoryData = gson.toJson(categoryList);
        }
    }

    /**
     * 强制由分类列表更新为json字符串
     */
    public void updateJsonDataByCategoryList(){

        Gson gson = new Gson();
        this.categoryData = gson.toJson(categoryList);
    }
}
