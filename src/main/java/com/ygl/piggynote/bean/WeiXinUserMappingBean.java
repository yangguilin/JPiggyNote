package com.ygl.piggynote.bean;

import java.util.Date;

/**
 * Created by yanggavin on 15/7/2.
 */
public class WeiXinUserMappingBean {
    private int id;
    private String weiXinUserName;
    private String piggyUserName;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeiXinUserName() {
        return weiXinUserName;
    }

    public void setWeiXinUserName(String weiXinUserName) {
        this.weiXinUserName = weiXinUserName;
    }

    public String getPiggyUserName() {
        return piggyUserName;
    }

    public void setPiggyUserName(String piggyUserName) {
        this.piggyUserName = piggyUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
