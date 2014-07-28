package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.CustomConfigBean;

/**
 * 用户自定义信息服务接口
 * Created by yanggavin on 14-7-18.
 */
public interface CustomConfigService {

    public CustomConfigBean getByUserName(String userName);
    public Boolean add(CustomConfigBean bean);
    public Boolean deleteByUserName(String userName);
    public Boolean update(CustomConfigBean bean);
}
