package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.mp.MyPasswordBean;
import com.ygl.piggynote.rowmapper.MpMyPasswordRowMapper;
import com.ygl.piggynote.service.MpMyPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

/**
 * Created by yanggavin on 15/10/16.
 */
@Service("mpMyPasswordService")
public class MpMyPasswordServiceImpl implements MpMyPasswordService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean add(MyPasswordBean bean) {
        int ret = jdbcTemplate.update("insert into mp_my_passwords(user_id, password, show_name, latest_update_time) value(?, ?, ?, now())",
                new Object[]{bean.getUserId(), bean.getPassword(), bean.getShowName()},
                new int[]{Types.INTEGER, Types.VARCHAR, Types.VARCHAR});
        return ret == 1;
    }

    @Override
    public boolean update(MyPasswordBean bean) {
        int ret = jdbcTemplate.update("update mp_my_passwords set password=?, show_name=?, latest_update_time=now() where id=? and user_id=?",
                new Object[]{bean.getPassword(), bean.getShowName(), bean.getId(), bean.getUserId()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER});
        return ret == 1;
    }

    @Override
    public boolean delete(int id, int userId) {
        int ret = jdbcTemplate.update("delete from mp_my_passwords where id=? and user_id=?",
                new Object[]{id, userId},
                new int[]{Types.INTEGER, Types.INTEGER});
        return ret == 1;
    }

    @Override
    public List<MyPasswordBean> getByUserId(int userId) {
        return (List<MyPasswordBean>)jdbcTemplate.query("select * from mp_my_passwords where user_id=?",
                new Object[]{userId},
                new int[]{Types.INTEGER},
                new MpMyPasswordRowMapper());
    }
}
