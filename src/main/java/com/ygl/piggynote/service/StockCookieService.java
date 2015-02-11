package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.StockCookieBean;

/**
 * Created by yanggavin on 15/2/9.
 */
public interface StockCookieService {
    public StockCookieBean get(String userName);
    public Boolean add(StockCookieBean bean);
    public Boolean delete(String userName);
    public Boolean update(StockCookieBean bean);
    public Boolean exist(String userName);
}
