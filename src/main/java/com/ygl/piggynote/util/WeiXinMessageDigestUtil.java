package com.ygl.piggynote.util;

import java.security.MessageDigest;

/**
 * Created by yanggavin on 15/6/18.
 */
public final class WeiXinMessageDigestUtil {
    private static final WeiXinMessageDigestUtil _instance = new WeiXinMessageDigestUtil();
    private MessageDigest alga;

    private WeiXinMessageDigestUtil() {
        try {
            alga = MessageDigest.getInstance("SHA-1");
        } catch(Exception e) {
            throw new InternalError("init MessageDigest error:" + e.getMessage());
        }
    }

    public static WeiXinMessageDigestUtil getInstance() {
        return _instance;
    }

    public static String byte2hex(byte[] b) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < b.length; i++) {
            tmp = (Integer.toHexString(b[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public String encipher(String strSrc) {
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        alga.update(bt);
        strDes = byte2hex(alga.digest());
        return strDes;
    }
}
