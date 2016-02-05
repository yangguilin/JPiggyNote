package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.mp.MyPasswordBean;

import java.util.List;

/**
 * Created by yanggavin on 15/10/16.
 */
public interface MpMyPasswordService {
    boolean add(MyPasswordBean bean);
    boolean update(MyPasswordBean bean);
    boolean delete(int id, int userId);
    List<MyPasswordBean> getByUserId(int userId);
    boolean existShowName(int userId, String showName);
}
