package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.InvestmentBean;
import com.ygl.piggynote.rowmapper.InvestmentRowMapper;
import com.ygl.piggynote.service.InvestmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

/**
 * Created by yanggavin on 15/5/12.
 */
@Service("investmentService")
public class InvestmentServiceImpl implements InvestmentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Boolean add(InvestmentBean bean) {
        int ret = jdbcTemplate.update("insert into pn_investment(name, type, description, principal_num, create_date, records, final_num, finish_date, finish_status, user_name, current_num) value(?, ?, ?, ?, now(), '', null, null, 0, ?, ?)",
                new Object[]{bean.getName(), bean.getType(), bean.getDescription(), bean.getPrincipalNum(), bean.getUserName(), bean.getCurrentNum()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.FLOAT, Types.VARCHAR, Types.FLOAT});
        return ret == 1;
    }

    @Override
    public Boolean update(InvestmentBean bean) {
        int ret = jdbcTemplate.update("update pn_investment set name=?, type=?, description=?, principal_num=?, records=?, current_num=? where id=? and user_name=?",
                new Object[]{ bean.getName(), bean.getType(), bean.getDescription(), bean.getPrincipalNum(), bean.getRecordsStr(), bean.getCurrentNum(), bean.getId(), bean.getUserName() },
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.FLOAT, Types.VARCHAR, Types.FLOAT, Types.INTEGER, Types.VARCHAR});
        return ret > 0;
    }

    @Override
    public Boolean finish(InvestmentBean bean) {
        int ret = jdbcTemplate.update("update pn_investment set final_num=current_num, finish_date=now(), finish_status=1 where id=? and user_name=?",
                new Object[]{ bean.getId(), bean.getUserName() },
                new int[]{Types.INTEGER, Types.VARCHAR});
        return ret > 0;
    }

    @Override
    public Boolean delete(int id, String userName) {
        int ret = jdbcTemplate.update("delete from pn_investment where user_name=? and id=?",
                new Object[]{userName, id},
                new int[]{Types.VARCHAR, Types.INTEGER});
        return ret > 0;
    }

    @Override
    public InvestmentBean get(int id) {
        return (InvestmentBean)jdbcTemplate.queryForObject("select * from pn_investment where id=?",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new InvestmentRowMapper());
    }

    @Override
    public List<InvestmentBean> get(String userName) {
        return (List<InvestmentBean>)jdbcTemplate.query("select * from pn_investment where user_name=? order by create_date desc",
                new Object[]{userName},
                new int[]{Types.VARCHAR},
                new InvestmentRowMapper());
    }
}
