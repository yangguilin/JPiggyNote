package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.WeiXinInputMessageBean;
import com.ygl.piggynote.bean.WeiXinTokenBean;
import com.ygl.piggynote.service.impl.DailyRecordServiceImpl;
import com.ygl.piggynote.service.impl.UserServiceImpl;
import com.ygl.piggynote.service.impl.WeiXinServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import com.ygl.piggynote.util.WeiXinAccessTokenUtil;
import com.ygl.piggynote.util.WeiXinAuthUtil;
import com.ygl.piggynote.util.WeiXinMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by yanggavin on 15/6/18.
 */
@Controller
@RequestMapping("/weixin")
public class WeiXinController extends BaseController {
    @Autowired
    private WeiXinServiceImpl weiXinService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private DailyRecordServiceImpl dailyRecordService;

    @RequestMapping(method = RequestMethod.GET)
    public void checkToken(WeiXinTokenBean bean, HttpServletRequest request, HttpServletResponse response) {
        if (WeiXinAuthUtil.checkSignature(bean)) {
            try {
                writeWeiXinMessageLog(bean, request);
                writeResponseContent(bean.getEchostr(), response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public void receiveWeiXinServerMsg(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String responseMsg = parseAndHandleMessage(request);
            writeResponseContent(responseMsg, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/get_access_token", method=RequestMethod.GET)
    public void getAccessToken(HttpServletResponse response){
        String accessToken = WeiXinAccessTokenUtil.getAccessToken();
        CommonUtil.writeResponse4ReturnStrResult(true, accessToken, response);
    }

    private void writeWeiXinMessageLog(WeiXinTokenBean bean, HttpServletRequest request){
        weiXinService.addWeiXinLog(bean, request.getMethod());
    }

    private void writeResponseContent(String resContent, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(resContent);
        writer.close();
    }

    private String getWeiXinPostMsgXmlContent(HttpServletRequest request) throws IOException {
        StringBuffer sb = new StringBuffer();
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String s = "";
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        return sb.toString();
    }

    private String parseAndHandleMessage(HttpServletRequest request) throws Exception{
        String result = "";
        String postMsgXmlContent = getWeiXinPostMsgXmlContent(request);
        WeiXinInputMessageBean bean = new WeiXinInputMessageBean();
        bean.setMessageContent(postMsgXmlContent);
        weiXinService.addWeiXinMsg(bean);

        @SuppressWarnings("unchecked")
        Map<String, String> map = WeiXinMessageUtil.parseXml(postMsgXmlContent);
        String msgType = map.get("MsgType");
        String respContent = "";
        // 文本消息
        if (msgType.equals("text")) {
            result = WeiXinMessageUtil.getResponseXmlByUserTextMsg(map, weiXinService, userService, dailyRecordService);
        }
        // 图片消息
        else if (msgType.equals("image")) {
            respContent = "";
            return null;
        }
        // 声音消息
        else if (msgType.equals("voice")) {
            respContent = "";
            return null;
        }
        // 视频消息
        else if (msgType.equals("video")) {
            respContent = "";
            return null;
        }
        // 地理位置
        else if (msgType.equals("location")) {
            respContent = "";
            return null;
        }
        // 事件类型
        else if (msgType.equals("event")) {
            String eventType = map.get("Event");
            // 订阅
            if (eventType.equals("subscribe")) {
                respContent = "欢迎订阅我的公众号！";
                result = respContent;
            }
            // 取消订阅
            else if (eventType.equals("unsubscribe")) {
                // TODO
                return null;
            }
            // 点击菜单
            else if (eventType.equals("CLICK")) {
                // TODO
                return null;
            }
        }
        return result;
    }
}
