package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.StockBean;
import com.ygl.piggynote.bean.UserBean;
import com.ygl.piggynote.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yanggavin on 15/2/9.
 */
@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public StockBean get(int id) {
        return null;
    }

    @Override
    public List<StockBean> getStocks(int userId) {
        return null;
    }

    @Override
    public Boolean add(UserBean bean) {
        return null;
    }

    @Override
    public Boolean delete(String userName) {
        return null;
    }

    @Override
    public Boolean update(UserBean bean) {
        return null;
    }
}
