package com.ygl.piggynote.controller;

import com.ygl.piggynote.bean.mp.*;
import com.ygl.piggynote.service.impl.MpMyAccountContentServiceImpl;
import com.ygl.piggynote.service.impl.MpMyPasswordServiceImpl;
import com.ygl.piggynote.service.impl.MpMyWordContentServiceImpl;
import com.ygl.piggynote.service.impl.MpMyWordServiceImpl;
import com.ygl.piggynote.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yanggavin on 15/10/15.
 */
@Controller
@RequestMapping("/mp")
public class MpController extends BaseController {
    @Autowired
    private MpMyPasswordServiceImpl myPasswordService;
    @Autowired
    private MpMyWordServiceImpl mpMyWordService;
    @Autowired
    private MpMyWordContentServiceImpl mpMyWordContentService;
    @Autowired
    private MpMyAccountContentServiceImpl mpMyAccountContentService;

    @RequestMapping(method= RequestMethod.GET)
    public String show(ModelMap model, HttpServletRequest request){
        return "mp";
    }

    @RequestMapping(value = "/search.do", method = RequestMethod.POST)
    public void search(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        int userId = Integer.parseInt(request.getParameter("userId"));
        String showName = request.getParameter("showName");
        List<MpSearchResultBean> resultList = new ArrayList<MpSearchResultBean>();
        if (userId > 0 && !showName.isEmpty()) {
            resultList = searchByShowName(userId, showName);
            ret = (resultList.size() > 0);
        }
        // String jsonString = gson.toJson(resultList, new TypeToken<List<MyPasswordBean>>(){}.getType());
        CommonUtil.writeResJsonResult(ret, resultList, response);
    }

    @RequestMapping(value="/show_content.do", method=RequestMethod.POST)
    public void showContent(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        if (checkUserLoginStatus(request)) {
            int userId = getUserFromSession(request).getId();
            String type = request.getParameter("type").toString();
            String showName = request.getParameter("showName").toString();
            if (!type.isEmpty() && !showName.isEmpty()) {
                if ("account".equalsIgnoreCase(type)) {
                    MyAccountContentBean accountContentBean = mpMyAccountContentService.get(userId, showName);
                    content = (accountContentBean != null) ? accountContentBean.getContent() : "";
                } else if ("password".equalsIgnoreCase(type)) {
                    MyWordBean wordBean = mpMyWordService.get(userId, showName);
                    MyWordContentBean wordContentBean = mpMyWordContentService.get(wordBean.getContentId());
                    content = (wordContentBean != null) ? wordContentBean.getContent() : "";
                }
                ret = (!content.isEmpty());
            }
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    private List<MpSearchResultBean> searchByShowName(int userId, String showName){
        List<MpSearchResultBean> resultList = new ArrayList<MpSearchResultBean>();
        List<MyPasswordBean> allPasswords = myPasswordService.getByUserId(userId);
        for (MyPasswordBean bean : allPasswords){
            if (bean.getShowName().indexOf(showName) >= 0){
                MpSearchResultBean sBean = new MpSearchResultBean();
                sBean.setId(bean.getId());
                sBean.setShowName(bean.getShowName());
                sBean.setAccountTip(getAccountTipByAccountId(userId, bean.getAccountId()));
                sBean.setPasswordTip(getPasswordTipByWordId(userId, bean.getPassword()));
                resultList.add(sBean);
            }
        }
        return resultList;
    }

    private String getPasswordTipByWordId(int userId, String words){
        String passwordTip = "";
        HashMap<Integer, String> wordMap = new HashMap<Integer, String>();
        List<MyWordBean> myWords = mpMyWordService.get(userId);
        for (MyWordBean bean : myWords){
            wordMap.put(bean.getId(), bean.getShowName());
        }

        String[] wordArr = words.split(",");
        for (int i = 0; i < wordArr.length; i++){
            passwordTip += wordMap.get(Integer.parseInt(wordArr[i])) + ",";
        }
        if (passwordTip.length() > 0){
            passwordTip = CommonUtil.removeLastWord(passwordTip);
        }
        return passwordTip;
    }

    private String getAccountTipByAccountId(int userId, int accountId){
        String accountTip = "";
        MyAccountContentBean bean = mpMyAccountContentService.get(accountId);
        return bean.getShowName();
    }
}
