package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.mp.MyAccountContentBean;

import java.util.List;

/**
 * Created by yanggavin on 15/10/22.
 */
public interface MpMyAccountContentService {
    int add(MyAccountContentBean bean);
    boolean update(MyAccountContentBean bean);
    boolean delete(int id, int userId);
    MyAccountContentBean get(int id);
    MyAccountContentBean get(int userId, String showName);
    MyAccountContentBean get(int userId, int id);
    List<MyAccountContentBean> getByUserId(int userId);
    List<MyAccountContentBean> getByContent(String content);
}
