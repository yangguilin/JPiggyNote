package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.GroupMemberBean;
import com.ygl.piggynote.bean.StockCookieBean;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.service.impl.GroupMemberServiceImpl;
import com.ygl.piggynote.service.impl.StockCookieServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by yanggavin on 15/3/12.
 */
@Controller
@RequestMapping("/stock/share")
public class StockShareController extends BaseController {
    @Autowired
    private StockCookieServiceImpl stockCookieService;
    @Autowired
    private GroupMemberServiceImpl groupMemberService;
    private List<GroupMemberBean> memberList = new ArrayList<GroupMemberBean>();
    private HashMap<String, List<String>> groupMemberHoldStockDataList = new HashMap<String, List<String>>();
    private List<Map.Entry<String, List<String>>> orderedDataList = new ArrayList<Map.Entry<String, List<String>>>();

    @RequestMapping(value="/{groupId}", method= RequestMethod.GET)
    public String show(@PathVariable("groupId") int groupId, HttpServletRequest request, ModelMap model){
        String userName = "";
        String stockCodes = "";
        String friendHoldStockData = "";
        UserBean ub = getUserFromSession(request);
        if (ub != null) {
            userName = ub.getUserName();
            if (groupMemberService.exist(userName, groupId)) {
                memberList = groupMemberService.getListByGroupId(groupId);
                getAllMembersHoldStockCookieData(userName);
                sortGroupMemberHoldStockDataList();
                stockCodes = getAllStockCodes();
                friendHoldStockData = getAllMembersHoldStockData();
            }
        }
        model.addAttribute("userName", userName);
        model.addAttribute("stockCodes", stockCodes);
        model.addAttribute("friendHoldStockData", friendHoldStockData);
        return "stock_share";
    }

    private void getAllMembersHoldStockCookieData(String userName){
        String ret = "";
        groupMemberHoldStockDataList.clear();
        for (GroupMemberBean gmb : memberList){
            if (stockCookieService.exist(gmb.getUserName())) {
                StockCookieBean scb = stockCookieService.get(gmb.getUserName());
                parseCookieDataAndSetToList(scb);
            }
        }
    }

    private String getAllStockCodes(){
        String ret = "";
        for (int i = 0; i < orderedDataList.size(); i++){
            ret += orderedDataList.get(i).getKey() + ",";
        }
        return CommonUtil.removeLastWord(ret);
    }

    private void parseCookieDataAndSetToList(StockCookieBean scb){
        String stockCookieData = scb.getStockCookie();
        if (!stockCookieData.isEmpty() && !stockCookieData.equals("::")) {
            String userName = scb.getUserName();
            String[] arr = stockCookieData.split("::");
            String[] arr2 = arr[0].split(";");
            for (String stockItemData : arr2) {
                String[] stockItems = stockItemData.split(",");
                String stockCode = stockItems[0];
                String buyPrice = stockItems[1];
                String holdType = stockItems[5];
                if (!holdType.equals("long")) {
                    String hashValue = userName + "," + buyPrice;
                    if (!groupMemberHoldStockDataList.containsKey(stockCode)) {
                        groupMemberHoldStockDataList.put(stockCode, new ArrayList<String>());
                    }
                    groupMemberHoldStockDataList.get(stockCode).add(hashValue);
                }
            }
        }
    }

    private String getAllMembersHoldStockData(){
        String ret = "";
        for (int i = 0; i < orderedDataList.size(); i++) {
            String codeRet = "";
            String stockCode = orderedDataList.get(i).getKey();
            List<String> holders = orderedDataList.get(i).getValue();
            codeRet += stockCode + "::";
            for (String item : holders) {
                String[] arr = item.split(",");
                String code = arr[0];
                String buyPrice = arr[1];
                codeRet += code + ":" + buyPrice + ",";
            }
            ret += CommonUtil.removeLastWord(codeRet) + ";";
        }
        return CommonUtil.removeLastWord(ret);
    }

    private void sortGroupMemberHoldStockDataList(){
        orderedDataList = new ArrayList<Map.Entry<String, List<String>>>(groupMemberHoldStockDataList.entrySet());
        Collections.sort(orderedDataList, new Comparator<Map.Entry<String, List<String>>>() {
            @Override
            public int compare(Map.Entry<String, List<String>> obj1, Map.Entry<String, List<String>> obj2) {
                return (obj2.getValue().size() - obj1.getValue().size());
            }
        });
        for (int i = 0; i < orderedDataList.size(); i++){
            groupMemberHoldStockDataList.put(orderedDataList.get(i).getKey(), orderedDataList.get(i).getValue());
        }
    }
}
