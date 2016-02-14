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
import java.util.Collections;
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
    public String show(ModelMap model, HttpServletRequest request, HttpServletResponse response){
        if (!checkUserLoginStatus(request)){
            redirectToHomePage(response);
        }
        List<MyAccountContentBean> selAccounts = new ArrayList<MyAccountContentBean>();
        List<MyWordBean> selWords = new ArrayList<MyWordBean>();
        List<MyPasswordBean> myPasswordBeanList = myPasswordService.getByUserId(curLoginUserId);
        List<MyAccountContentBean> accounts = mpMyAccountContentService.getByUserId(curLoginUserId);
        List<MyWordBean> words = mpMyWordService.get(curLoginUserId);
        for (MyAccountContentBean b : accounts){
            if (!b.getShowName().isEmpty()){
                selAccounts.add(b);
            }
        }
        for (MyWordBean b : words){
            if (!b.getShowName().isEmpty()){
                selWords.add(b);
            }
        }
        Collections.sort(selAccounts);
        Collections.sort(selWords);
        model.addAttribute("myAccountTotalNum", myPasswordBeanList.size());
        model.addAttribute("showAccountList", selAccounts);
        model.addAttribute("showWordList", selWords);

        return "mp";
    }

    @RequestMapping(value = "/search.do", method = RequestMethod.POST)
    public void search(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        List<MpSearchResultBean> resultList = new ArrayList<MpSearchResultBean>();
        if (checkUserLoginStatus(request)) {
            String showName = request.getParameter("showName");
            if (!showName.isEmpty()) {
                resultList = searchByShowName(curLoginUserId, showName);
                ret = true;
            }
        }
        CommonUtil.writeResJsonResult(ret, resultList, response);
    }

    @RequestMapping(value = "/account_show_name_exist.do", method = RequestMethod.POST)
    public void queryExistAccountShowName(HttpServletRequest request, HttpServletResponse response){
        boolean ret = true;
        boolean exist = false;
        try {
            if (checkUserLoginStatus(request)) {
                String showName = request.getParameter("showName");
                if (!showName.isEmpty()) {
                    exist = myPasswordService.existShowName(curLoginUserId, showName);
                }
            }
        } catch (Exception e){
            ret = false;
        }
        CommonUtil.writeResJsonResult(ret, exist, response);
    }

    @RequestMapping(value="/show_content.do", method=RequestMethod.POST)
    public void showContent(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        if (checkUserLoginStatus(request)) {
            String type = request.getParameter("type").toString();
            String idStr = request.getParameter("val").toString();
            if (!type.isEmpty() && CommonUtil.isNumeric(idStr)) {
                int id = Integer.parseInt(idStr);
                if ("account".equalsIgnoreCase(type)) {
                    MyAccountContentBean accountContentBean = mpMyAccountContentService.get(curLoginUserId, id);
                    content = encryptContent4CommonlyUsed(accountContentBean.getShowName(), accountContentBean.getContent());
                } else if ("password".equalsIgnoreCase(type)) {
                    MyWordBean wordBean = mpMyWordService.get(curLoginUserId, id);
                    MyWordContentBean wordContentBean = mpMyWordContentService.get(wordBean.getContentId());
                    content = encryptContent4CommonlyUsed(wordBean.getShowName(), wordContentBean.getContent());
                }
                ret = (!content.isEmpty());
            }
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    @RequestMapping(value="/new_account.do", method = RequestMethod.POST)
    public void addNewAccount(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        if (checkUserLoginStatus(request)){
            String accountInfo = request.getParameter("accountInfo").toString();
            String originalVal = request.getParameter("originalVal").toString();
            if (!originalVal.isEmpty()){ // 账号提示信息可为空
                int newId = 0;
                List<MyAccountContentBean> existBeanList = mpMyAccountContentService.getByContent(originalVal);
                if (existBeanList.size() == 0) {
                    MyAccountContentBean macb = new MyAccountContentBean();
                    macb.setShowName(accountInfo);
                    macb.setContent(originalVal);
                    macb.setUserId(curLoginUserId);
                    newId = mpMyAccountContentService.add(macb);
                } else {
                    newId = existBeanList.get(0).getId();
                    accountInfo = existBeanList.get(0).getShowName();
                }
                if (newId > 0){
                    ret = true;
                    content = accountInfo + "," + newId;
                }
            } else {
                content = "参数错误";
            }
        } else {
            content = "尚未登录";
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    @RequestMapping(value = "/delete_my_password.do", method = RequestMethod.POST)
    public void deleteMyPassword(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        if (checkUserLoginStatus(request)){
            String id = request.getParameter("id").toString();
            String showName = request.getParameter("showName").toString();
            if (!id.isEmpty() && CommonUtil.isNumeric(id) && !showName.isEmpty()){
                if (myPasswordService.existShowName(curLoginUserId, showName)){
                    ret = myPasswordService.delete(Integer.parseInt(id), curLoginUserId);
                    content = ret + "";
                } else {
                    content = "参数错误";
                }
            } else {
                content = "参数错误";
            }
        } else {
            content = "尚未登录";
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    @RequestMapping(value = "/update_my_password.do", method = RequestMethod.POST)
    public void updateMyPassword(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        String content = "参数错误";
        if (checkUserLoginStatus(request)){
            String showName = request.getParameter("showName");
            String id = request.getParameter("id");
            String pswIds = request.getParameter("pswIds");
            if (!showName.isEmpty() && !id.isEmpty() && CommonUtil.isNumeric(id) && !pswIds.isEmpty()) {
                if (myPasswordService.existShowName(curLoginUserId, showName)) {
                    MyPasswordBean mpb = myPasswordService.get(curLoginUserId, showName);
                    if (mpb.getId() == Integer.parseInt(id)){
                        mpb.setPassword(pswIds);
                        ret = myPasswordService.update(mpb);
                        content = ret + "";
                    }
                }
            }
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    @RequestMapping(value="/new_psw_part.do", method = RequestMethod.POST)
    public void addNewPswPart(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        if (checkUserLoginStatus(request)){
            String text = request.getParameter("text").toString();
            String val = request.getParameter("value").toString();
            if (!val.isEmpty()){ // 密码块显示名称可为空
                MyWordBean mwb = new MyWordBean();
                List<MyWordContentBean> existContentBeanList = mpMyWordContentService.getByContent(val);
                int contentId = -1;
                if (existContentBeanList.size() == 0) {
                    MyWordContentBean mwcb = new MyWordContentBean();
                    mwcb.setUserId(curLoginUserId);
                    mwcb.setContent(val);
                    contentId = mpMyWordContentService.add(mwcb);
                } else {
                    contentId = existContentBeanList.get(0).getId();
                }
                if (contentId > 0) {
                    mwb.setContentId(contentId);
                    mwb.setUserId(curLoginUserId);
                    mwb.setShowName(text);
                    int newWordId = mpMyWordService.add(mwb);
                    content = text + "," + newWordId;
                    ret = true;
                }
            } else {
                content = "参数错误";
            }
        } else {
            content = "尚未登录";
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    @RequestMapping(value="/finish_new_account.do", method = RequestMethod.POST)
    public void finishNewAccount(HttpServletRequest request, HttpServletResponse response){
        boolean ret = false;
        String content = "";
        if (checkUserLoginStatus(request)){
            String showName = request.getParameter("showName");
            String accountId = request.getParameter("accountId");
            String pswIds = request.getParameter("pswIds");
            if (checkParams4FinishNewAccount(showName, accountId, pswIds)) {
                MyPasswordBean mpb = new MyPasswordBean();
                mpb.setShowName(showName);
                mpb.setAccountId(Integer.parseInt(accountId));
                mpb.setPassword(pswIds);
                mpb.setUserId(curLoginUserId);
                if (!myPasswordService.existShowName(curLoginUserId, showName)) {
                    ret = myPasswordService.add(mpb);
                } else {
                    content = "账号条目名称重复,请修改";
                }
            } else {
                content = "参数错误";
            }
        } else {
            content = "尚未登录";
        }
        CommonUtil.writeResJsonResult(ret, content, response);
    }

    private boolean checkParams4FinishNewAccount(String showName, String accountId, String pswIds){
        boolean ret = true;
        if (!showName.isEmpty() &&
                !accountId.isEmpty() &&
                !pswIds.isEmpty() &&
                CommonUtil.isNumeric(accountId)) {
            if (pswIds.indexOf(",") != -1) {
                String[] arr = pswIds.split(",");
                for (String id : arr) {
                    if (!CommonUtil.isNumeric(id)) {
                        ret = false;
                        break;
                    }
                }
            }
        } else {
            ret = false;
        }
        return ret;
    }

    private List<MpSearchResultBean> searchByShowName(int userId, String showName){
        List<MpSearchResultBean> resultList = new ArrayList<MpSearchResultBean>();
        List<MyPasswordBean> allPasswords = myPasswordService.getByUserId(userId);
        for (MyPasswordBean bean : allPasswords){
            if (bean.getShowName().toLowerCase().indexOf(showName.toLowerCase()) >= 0){
                MpSearchResultBean sBean = new MpSearchResultBean();
                sBean.setId(bean.getId());
                sBean.setShowName(bean.getShowName());
                sBean.setAccountTip(getAccountTipByAccountId(bean.getAccountId()));
                sBean.setPasswordTip(getPasswordTipByWordId(userId, bean.getPassword()));
                sBean.setAccountId(bean.getAccountId());
                sBean.setPasswordIds(bean.getPassword());
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

        if (!words.isEmpty()) {
            String[] wordArr = words.split(",");
            if (wordArr.length == 1){ // 一次性密码
                passwordTip = wordMap.get(Integer.parseInt(wordArr[0]));
                passwordTip = passwordTip.isEmpty() ? "点击查看" : passwordTip;
            } else { // 分段式密码
                String curPswPartText = "";
                for (int i = 0; i < wordArr.length; i++) {
                    curPswPartText = wordMap.get(Integer.parseInt(wordArr[i]));
                    curPswPartText = curPswPartText.isEmpty() ? "点击查看" : curPswPartText;
                    passwordTip += curPswPartText + ",";
                }
                if (passwordTip.length() > 0) {
                    passwordTip = CommonUtil.removeLastWord(passwordTip);
                }
            }
        }
        return passwordTip;
    }

    private String getAccountTipByAccountId(int accountId){
        MyAccountContentBean bean = mpMyAccountContentService.get(accountId);
        return (bean.getShowName().isEmpty() ? "点击查看" : bean.getShowName());
    }

    /**
     * 对常用的账号或密码进行加密
     * @param showName
     * @param content
     * @return
     */
    private String encryptContent4CommonlyUsed(String showName, String content){
        String encryptContent = "";
        if (showName.isEmpty()){
            encryptContent = content;
        } else {
            for (int i = 0; i < content.length(); i++){
                encryptContent += "*";
            }
        }

        return encryptContent;
    }
}
