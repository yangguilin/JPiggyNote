package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.StockBean;
import com.ygl.piggynote.bean.UserBean;

import java.util.List;

/**
 * Created by yanggavin on 15/2/9.id
 */
public interface StockService {
    public StockBean get(int id);
    public List<StockBean> getStocks(int userId);
    public Boolean add(UserBean bean);
    public Boolean delete(String userName);
    public Boolean update(UserBean bean);
}
