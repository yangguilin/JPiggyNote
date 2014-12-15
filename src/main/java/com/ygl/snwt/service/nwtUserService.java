package com.ygl.snwt.service;

import com.ygl.snwt.bean.NwtUser;

/**
 * Created by yanggavin on 14/12/12.
 */
public interface NwtUserService {

    public NwtUser get(int userId);
    public Boolean add(NwtUser nwtUser);
    public Boolean update(NwtUser nwtUser);
    public Boolean delete(int userId);
}
