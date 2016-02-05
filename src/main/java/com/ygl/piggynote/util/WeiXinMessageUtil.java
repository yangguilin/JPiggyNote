package com.ygl.piggynote.util;


import com.ygl.piggynote.bean.DailyRecordBean;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.bean.WeiXinUserMappingBean;
import com.ygl.piggynote.enums.MoneyTypeEnum;
import com.ygl.piggynote.enums.StatTypeEnum;
import com.ygl.piggynote.service.impl.DailyRecordServiceImpl;
import com.ygl.piggynote.service.impl.UserServiceImpl;
import com.ygl.piggynote.service.impl.WeiXinServiceImpl;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanggavin on 15/6/25.
 */
public class WeiXinMessageUtil {
    public static final String HELP_RESPONSE_CONTENT = "#绑定#\n" +
            "#绑定#账号:密码\n" +
            "#解绑#账号:密码\n" +
            "#记录#\n" +
            "¥支出金额数字\n" +
            "¥支出金额数字:备注内容\n" +
            "¥+收入金额数字\n" +
            "¥+收入金额:备注内容";

    public static Map parseXml(String xml) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();
        // 遍历所有子节点
        for (Element e : elementList) {
            // 对于CDATA区域内的内容，XML解析程序不会处理，而是直接原封不动的输出。
            map.put(e.getName(), e.getText());
        }
        return map;
    }

    public static String getResponseXmlByUserTextMsg(Map<String, String> map, WeiXinServiceImpl weiXinService, UserServiceImpl userService, DailyRecordServiceImpl dailyRecordService){
        String msgContent = map.get("Content");
        String serverName = map.get("ToUserName");
        String customerName = map.get("FromUserName");
        Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;

        String resMsgContent = handleUserTextMsg(msgContent, customerName, weiXinService, userService, dailyRecordService);
        StringBuffer buffer = new StringBuffer();
        buffer.append("<xml>");
        buffer.append("<ToUserName><![CDATA[" + customerName + "]]></ToUserName>");
        buffer.append("<FromUserName><![CDATA[" + serverName + "]]></FromUserName>");
        buffer.append("<CreateTime>" + returnTime + "</CreateTime>");
        buffer.append("<MsgType><![CDATA[text]]></MsgType>");
        buffer.append("<Content><![CDATA[" + resMsgContent + "]]></Content>");
        buffer.append("</xml>");
        return buffer.toString();
    }

    public static String handleUserTextMsg(String msg, String customerName, WeiXinServiceImpl weiXinService, UserServiceImpl userService, DailyRecordServiceImpl dailyRecordService){
        String responseMsgContent = "";
        String piggyUserName = "";
        if (weiXinService.weiXinUserMappingExist(customerName)) {
            piggyUserName = weiXinService.getPiggyUserNameByWeiXinUserName(customerName).getPiggyUserName();
        }
        if (msg.startsWith("#")){  // 指令前缀
            int index = msg.lastIndexOf("#");
            String command = msg.substring(1, index);
            String context = msg.substring(index + 1);
            if (command.equals("绑定")){
                if (!context.isEmpty()){
                    String[] arr = context.split(":");
                    String relatedUserName = arr[0];
                    String relatedUserPassword = arr[1];

                    if (!relatedUserName.isEmpty() && userService.exist(relatedUserName)){
                        UserBean ub = userService.get(relatedUserName);
                        String newPsw = Md5Util.getMD5Code(relatedUserPassword);
                        if (ub.getPassword().equals(newPsw)) {
                            if (piggyUserName.isEmpty()){
                                WeiXinUserMappingBean bean = new WeiXinUserMappingBean();
                                bean.setPiggyUserName(relatedUserName);
                                bean.setWeiXinUserName(customerName);
                                if (weiXinService.addWeiXinUserMapping(bean)){
                                    responseMsgContent = "绑定成功!";
                                } else {
                                    responseMsgContent = "绑定失败!";
                                }
                            } else {
                                responseMsgContent = "亲已绑定账号[" + relatedUserName + "]，不能重复绑定!";
                            }
                        } else {
                            responseMsgContent = "绑定失败：亲提供的用户名或密码不正确.";
                        }
                    } else {
                        responseMsgContent = "绑定失败：亲提供的用户名不存在.";
                    }
                } else {
                    if (piggyUserName.isEmpty()){
                        responseMsgContent = "亲尚未绑定小猪记账账号.";
                    } else {
                        responseMsgContent = "亲已绑定账号[" + piggyUserName + "]";
                    }
                }
            } else if (command.equals("帮助")){
                responseMsgContent = HELP_RESPONSE_CONTENT;
            } else if (command.equals("解绑")){
                if (context.isEmpty()){
                    responseMsgContent = "请输入已绑定账号和密码.";
                } else {
                    String[] arr = context.split(":");
                    String relatedUserName = arr[0];
                    String relatedUserPassword = arr[1];
                    if (userService.exist(relatedUserName)) {
                        UserBean ub = userService.get(relatedUserName);
                        String newPsw = Md5Util.getMD5Code(relatedUserPassword);
                        if (ub.getPassword().equals(newPsw)) {
                            if (piggyUserName.equalsIgnoreCase(relatedUserName)) {
                                WeiXinUserMappingBean bean = new WeiXinUserMappingBean();
                                bean.setPiggyUserName(relatedUserName);
                                bean.setWeiXinUserName(customerName);
                                if (weiXinService.deleteWeiXinUserMapping(bean)) {
                                    responseMsgContent = "解绑成功!";
                                } else {
                                    responseMsgContent = "解绑失败!";
                                }
                            } else {
                                responseMsgContent = "解绑失败：账号[" + relatedUserName + "]不是亲已绑定的账号!";
                            }
                        } else {
                            responseMsgContent = "解绑失败：亲提供的用户名或密码不正确.";
                        }
                    } else {
                        responseMsgContent = "解绑失败：亲提供的用户名或密码不正确.";
                    }
                }
            } else if (command.startsWith("记录")){
                String todayStr = DateUtil.getDateStr(0, 0, 1);
                String dayBeforeYesterdayStr = DateUtil.getDateStr(0, 0, -2);
                List<DailyRecordBean> list = dailyRecordService.getRecordsByDateRange(dayBeforeYesterdayStr, todayStr, piggyUserName);
                StringBuilder latestRecords = new StringBuilder();
                for (int i = 0; i < list.size(); i++){
                    String record = DateUtil.getDayStr(list.get(i).getCreateDate()) +
                            " | " + (list.get(i).getMoneyType().toString().equalsIgnoreCase("income") ? "收入" : "支出") +
                            " | " + list.get(i).getAmount() + " 元\n";
                    latestRecords.append(record);
                }
                responseMsgContent = latestRecords.toString();
            }
        } else if (msg.startsWith("¥") && !piggyUserName.isEmpty()){
            MoneyTypeEnum moneyType = MoneyTypeEnum.COST;
            String content = msg.substring(1);
            if (!content.isEmpty()) {
                if (content.startsWith("+")) {
                    content = content.substring(1);
                    moneyType = MoneyTypeEnum.INCOME;
                }
                String[] arr = content.split(":");
                float amount = Float.parseFloat(arr[0]);
                String remark = (arr.length == 2) ? arr[1] : "";
                DailyRecordBean drb = new DailyRecordBean();
                drb.setAmount(amount);
                drb.setRemark(remark);
                drb.setUserName(piggyUserName);
                drb.setMoneyType(moneyType);
                drb.setStatType(StatTypeEnum.SIMPLE);
                drb.setCategoryId("0");
                drb.setCategoryName("");
                if (dailyRecordService.add(drb)) {
                    responseMsgContent = "成功添加一笔" + (moneyType == MoneyTypeEnum.COST ? "[支出]" : "[收入]") + "记录.";
                } else {
                    responseMsgContent = "记录失败.";
                }
            } else {
                responseMsgContent = "记录失败：亲没有输入金额数字.";
            }
        } else {
            responseMsgContent = "亲输入的指令不能识别.\n输入下面指令查询帮助：\n#帮助#";
        }
        return responseMsgContent;
    }
}
