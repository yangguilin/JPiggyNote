package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.GroupMemberBean;

import java.util.List;

/**
 * Created by yanggavin on 15/3/13.
 */
public interface GroupMemberService {
    public List<GroupMemberBean> getListByUserName(String userName);
    public List<GroupMemberBean> getListByGroupId(int groupId);
    public Boolean add(GroupMemberBean bean);
    public Boolean update(GroupMemberBean bean);
    public Boolean delete(String userName, int groupId);
    public Boolean exist(String userName, int groupId);
}
