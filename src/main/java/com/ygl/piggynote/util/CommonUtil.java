package com.ygl.piggynote.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by guilin on 2014/8/1.
 */
public class CommonUtil {


    /**
     * 为Boolean类型的返回结果，写入response返回字符串
     * @param ret   布尔结果
     * @param response  response实例
     */
    public static void writeResponse4BooleanResult(Boolean ret, HttpServletResponse response){

        String res = ret ? "success" : "fail";

        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.write(res);
        pw.close();
        pw.flush();
    }

    /**
     * 为String类型的返回结果，写入response返回字符串
     * @param ret   操作是否成功
     * @param resContent    返回内容
     * @param response  response
     */
    public static void writeResponse4ReturnStrResult(Boolean ret, String resContent, HttpServletResponse response){

        String res = ret ? resContent : "fail";

        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.write(res);
        pw.flush();
    }

    public static String removeLastWord(String content){
        return ((content != "") ? content.substring(0, content.length() - 1) : "");
    }
}
