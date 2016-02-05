package com.ygl.piggynote.bean.mp;

import java.util.Date;

/**
 * Created by yanggavin on 15/10/22.
 */
public class MyAccountContentBean implements Comparable<MyAccountContentBean> {
    private int id;
    private int userId;
    private String content;
    private String showName;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String accountContent) {
        this.content = accountContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    @Override
    public int compareTo(MyAccountContentBean o) {
        return this.getShowName().length() - o.getShowName().length();
    }
}
