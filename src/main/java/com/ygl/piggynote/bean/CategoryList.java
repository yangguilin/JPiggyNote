package com.ygl.piggynote.bean;

import java.util.HashMap;

/**
 * 分类列表实体类
 * Created by guilin on 2014/8/12.
 */
public class CategoryList {

    public HashMap<String, String> costMap;
    public HashMap<String, String> incomeMap;
    public HashMap<String, Integer> useageMap;

    public CategoryList(){

        this.costMap = new HashMap<String, String>();
        this.incomeMap = new HashMap<String, String>();
        this.useageMap = new HashMap<String, Integer>();
    }

    /**
     * 填充默认分类数据
     */
    public void defaultData(){

        // 支出分类
        costMap.put( "c1", "穿衣");
        costMap.put("c2", "吃饭");
        costMap.put( "c3", "居住");
        costMap.put("c4", "交通");
        costMap.put( "c5", "娱乐");
        costMap.put("c6", "投资");

        // 收入分类
        incomeMap.put("i1", "工资");
        incomeMap.put("i2", "其他收入");
    }
}
