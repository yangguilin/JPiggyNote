package com.ygl.piggynote.bean.mp;

/**
 * Created by yanggavin on 15/10/19.
 */
public class MpSearchResultBean {
    private int id;
    private String showName;
    private String accountTip;
    private String passwordTip;

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
}
