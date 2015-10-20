package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.mp.MyWordContentBean;

/**
 * Created by yanggavin on 15/10/15.
 */
public interface MpMyWordContentService {
    int add(MyWordContentBean bean);
    boolean update(MyWordContentBean bean);
    boolean delete(int id, int userId);
    MyWordContentBean get(int id);
}
