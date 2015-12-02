package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.mp.MyAccountContentBean;

/**
 * Created by yanggavin on 15/10/22.
 */
public interface MpMyAccountContentService {
    int add(MyAccountContentBean bean);
    boolean update(MyAccountContentBean bean);
    boolean delete(int id, int userId);
    MyAccountContentBean get(int id);
    MyAccountContentBean get(int userId, String showName);
}
