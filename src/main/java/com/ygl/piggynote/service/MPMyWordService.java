package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.mp.MyWordBean;

import java.util.List;

/**
 * Created by yanggavin on 15/10/15.
 */
public interface MpMyWordService {
    boolean add(MyWordBean bean);
    boolean update(MyWordBean bean);
    boolean delete(int id, int userId);
    List<MyWordBean> getByUserId(int userId);
}
