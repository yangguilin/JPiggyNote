package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.*;

/**
 * 用户服务接口
 * Created by yanggavin on 14-7-18.
 */
public interface UserService {
    UserBean get(String userName);
    Boolean add(UserBean bean);
    Boolean delete(String userName);
    Boolean update(UserBean bean);
    Boolean exist(String userName);
}
