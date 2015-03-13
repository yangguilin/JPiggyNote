package com.ygl.piggynote.bean;

/**
 * Created by yanggavin on 15/3/13.
 */
public class GroupMemberBean {
    private int id;
    private String userName;
    private int groupId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
