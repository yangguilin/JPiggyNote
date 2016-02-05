package com.ygl.piggynote.bean.mp;

/**
 * Created by yanggavin on 15/10/19.
 */
public class MpSearchResultBean {
    private int id;
    private String showName;
    private String accountTip;
    private String passwordTip;
    private int accountId;
    private String passwordIds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getPasswordTip() {
        return passwordTip;
    }

    public void setPasswordTip(String passwordTip) {
        this.passwordTip = passwordTip;
    }

    public String getAccountTip() {
        return accountTip;
    }

    public void setAccountTip(String accountTip) {
        this.accountTip = accountTip;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getPasswordIds() {
        return passwordIds;
    }

    public void setPasswordIds(String passwordIds) {
        this.passwordIds = passwordIds;
    }
}
