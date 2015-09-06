package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.WeiXinInputMessageBean;
import com.ygl.piggynote.bean.WeiXinTokenBean;
import com.ygl.piggynote.bean.WeiXinUserMappingBean;

import java.util.List;

/**
 * Created by yanggavin on 15/6/23.
 */
public interface WeiXinService {
    List<WeiXinInputMessageBean> getAll();
    Boolean addWeiXinMsg(WeiXinInputMessageBean bean);
    Boolean deleteWeiXinMsg(int id);
    Boolean addWeiXinLog(WeiXinTokenBean bean, String requestType);
    Boolean addWeiXinUserMapping(WeiXinUserMappingBean bean);
    Boolean deleteWeiXinUserMapping(WeiXinUserMappingBean bean);
    Boolean weiXinUserMappingExist(String weiXinUserName);
    WeiXinUserMappingBean getPiggyUserNameByWeiXinUserName(String weiXinUserName);
}
