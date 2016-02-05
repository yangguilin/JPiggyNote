package com.ygl.piggynote.bean;

import java.util.Date;

/**
 * Created by yanggavin on 15/6/24.
 */
public class WeiXinInputMessageBean {
    private int id;
    private String messageContent;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
