package com.ygl.snwt.service;

import com.ygl.snwt.bean.NwtRelationship;

import java.util.List;

/**
 *
 * Created by yanggavin on 14/12/12.
 */
public interface NwtRelationshipService {

    public NwtRelationship get(int userId, int friendId);
    public List<NwtRelationship> getList(int userId);
    public Boolean add(NwtRelationship relationship);
    public Boolean update(NwtRelationship relationship);
    public Boolean delete(int userId, int friendId);
}
