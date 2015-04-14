package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.HoneycombUnitBaseBean;

/**
 * Created by yanggavin on 15/4/7.
 */
public interface FcmgService {
    public void initHoneycomb(int sideUnitNum, int goldUnitNum);
    public String generateHoneycombViewData();
    public HoneycombUnitBaseBean getCurrentUnit4User(String unitSide);
    public String generateHoneycombData();
}
