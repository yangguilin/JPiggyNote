package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.mp.MyAccountContentBean;
import com.ygl.piggynote.rowmapper.MpMyAccountContentRowMapper;
import com.ygl.piggynote.service.MpMyAccountContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Created by yanggavin on 15/10/22.
 */
@Service("mpMyAccountContentService")
public class MpMyAccountContentServiceImpl implements MpMyAccountContentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(MyAccountContentBean bean) {
        final MyAccountContentBean fBean = bean;
        final String sql = "insert into mp_my_account_contents(user_id, content, show_name) value(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps = jdbcTemplate.getDataSource().getConnection()
                                .prepareStatement(sql, new String[]{"user_id", "content", "show_name"});
                        ps.setString(1, fBean.getUserId() + "");
                        ps.setString(2, fBean.getContent());
                        ps.setString(2, fBean.getShowName());
                        return ps;
                    }
                }
                , keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public boolean update(MyAccountContentBean bean) {
        int ret = jdbcTemplate.update("update mp_my_account_contents set content=?, show_name=? where id=? and user_id=?",
                new Object[]{bean.getContent(), bean.getShowName(), bean.getId(), bean.getUserId()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.INTEGER});
        return ret == 1;
    }

    @Override
    public boolean delete(int id, int userId) {
        int ret = jdbcTemplate.update("delete from mp_my_account_contents where id=? and user_id=?",
                new Object[]{id, userId},
                new int[]{Types.INTEGER, Types.INTEGER});
        return ret == 1;
    }

    @Override
    public MyAccountContentBean get(int id) {
        return (MyAccountContentBean)jdbcTemplate.queryForObject("select * from mp_my_account_contents where id=?",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new MpMyAccountContentRowMapper());
    }

    @Override
    public MyAccountContentBean get(int userId, String showName) {
        return (MyAccountContentBean)jdbcTemplate.queryForObject("select * from mp_my_account_contents where user_id=? and show_name=?",
                new Object[]{userId, showName},
                new int[]{Types.INTEGER, Types.VARCHAR},
                new MpMyAccountContentRowMapper());
    }
}
