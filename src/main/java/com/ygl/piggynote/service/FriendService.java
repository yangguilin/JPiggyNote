package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.FriendBean;

import java.util.List;

/**
 * Created by yanggavin on 15/3/12.
 */
public interface FriendService {
    public Boolean add(FriendBean bean);
    public Boolean update(FriendBean bean);
    public Boolean delete(FriendBean bean);
    public Boolean exist(FriendBean bean);
    public List<FriendBean> getFriendsByUserName(String userName);
    public List<FriendBean> getFriendsByFriendName(String friendName);
}
