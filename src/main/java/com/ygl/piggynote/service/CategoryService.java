package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.CategoryBean;

/**
 * 账目分类Service
 * Created by yanggavin on 14-7-18.
 */
public interface CategoryService {

    public CategoryBean getByUserName(String userName);
    public Boolean add(CategoryBean cb);
    public Boolean deleteByUserName(String userName);
    public Boolean update(CategoryBean cb);
}
