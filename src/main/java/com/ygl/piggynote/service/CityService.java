package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.CityBean;

import java.util.List;

/**
 * Created by yanggavin on 14-7-17.
 */
public interface CityService {

    // save
    public void save(CityBean cityBean);
    // update
    public void update(CityBean cityBean);
    // delete
    public void delete(int id);
    // get city by id
    public CityBean getCity(int id);
    // get all cities
    public List<CityBean> getCityBean();
}
