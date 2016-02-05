package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.GroupBean;

/**
 * Created by yanggavin on 15/3/17.
 */
public interface GroupService {
    public GroupBean get(int id);
    public Boolean add(GroupBean bean);
    public Boolean update(GroupBean bean);
    public Boolean delete(int id);
    public Boolean exist(int id);
}
