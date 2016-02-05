package com.ygl.piggynote.util;

import com.ygl.piggynote.bean.WeiXinTokenBean;

import java.util.Arrays;

/**
 * Created by yanggavin on 15/6/24.
 */
public class WeiXinAuthUtil {
    private static final String _myToken = "suantianxigua2015";

    public static Boolean checkSignature(WeiXinTokenBean bean){
        Boolean ret = false;
            if (checkWeiXinTokenBean(bean)) {
            String[] ArrTmp = {_myToken, bean.getTimestamp(), bean.getNonce()};
            Arrays.sort(ArrTmp);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < ArrTmp.length; i++) {
                sb.append(ArrTmp[i]);
            }
            String pwd = WeiXinMessageDigestUtil.getInstance().encipher(sb.toString());
            ret = bean.getSignature().equals(pwd);
        }
        return ret;
    }

    private static Boolean checkWeiXinTokenBean(WeiXinTokenBean bean){
        return !(bean.getSignature().isEmpty() || bean.getTimestamp().isEmpty() || bean.getNonce().isEmpty() || bean.getEchostr().isEmpty());
    }
}
