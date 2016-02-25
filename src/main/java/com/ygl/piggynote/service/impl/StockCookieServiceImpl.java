package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.QueryCountBean;
import com.ygl.piggynote.bean.StockCookieBean;
import com.ygl.piggynote.rowmapper.QueryCountRowMapper;
import com.ygl.piggynote.rowmapper.StockCookieRowMapper;
import com.ygl.piggynote.service.StockCookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;

/**
 * Created by yanggavin on 15/2/9.
 */
@Service
public class StockCookieServiceImpl implements StockCookieService {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public StockCookieBean get(String userName) {
        return (StockCookieBean)jdbcTemplate.queryForObject("select * from pn_stock_cookie where user_name=?",
                new Object[]{userName},
                new int[]{Types.VARCHAR},
                new StockCookieRowMapper());
    }

    @Override
    public Boolean add(StockCookieBean bean) {
        int ret = jdbcTemplate.update("insert into pn_stock_cookie(user_name, stock_cookie, saved_date, quick_cookie) value(?, ?, now(), ?)",
                new Object[]{ bean.getUserName(), bean.getStockCookie(),bean.getQuickCookie() },
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
        return ret == 1;
    }

    @Override
    public Boolean delete(String userName) {
        int ret = jdbcTemplate.update("delete from pn_stock_cookie where user_name=?",
                new Object[]{userName},
                new int[]{Types.VARCHAR});
        return ret > 0;
    }

    @Override
    public Boolean update(StockCookieBean bean) {
        int ret = jdbcTemplate.update("update pn_stock_cookie set stock_cookie=?, saved_date=now(), quick_cookie=? where user_name=?",
                new Object[]{ bean.getStockCookie(), bean.getQuickCookie(), bean.getUserName() },
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
        return ret > 0;
    }

    @Override
    public Boolean exist(String userName) {
        QueryCountBean qcb = (QueryCountBean)jdbcTemplate.queryForObject("select count(*) as num from pn_stock_cookie where user_name=?",
                new Object[]{ userName },
                new int[]{Types.VARCHAR},
                new QueryCountRowMapper());
        return qcb.getNum() > 0;
    }
}
