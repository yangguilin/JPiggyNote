package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.FcmgRecordBean;

import java.util.List;

/**
 * Created by yanggavin on 15/4/14.
 */
public interface FcmgRecordService {
    public Boolean add(FcmgRecordBean bean);
    public List<FcmgRecordBean> getTop10Record(int sideNum, int goldNum, int glanceTime);
}
