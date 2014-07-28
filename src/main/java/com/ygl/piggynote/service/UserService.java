package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.*;

/**
 * 用户服务接口
 * Created by yanggavin on 14-7-18.
 */
public interface UserService {

    public UserBean getByUserName(String userName);
    public Boolean add(UserBean bean);
    public Boolean deleteByUserName(String userName);
    public Boolean update(UserBean bean);
}
