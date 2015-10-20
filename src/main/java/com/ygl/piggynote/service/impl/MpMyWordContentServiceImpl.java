package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.mp.MyWordContentBean;
import com.ygl.piggynote.rowmapper.MpMyWordContentRowMapper;
import com.ygl.piggynote.service.MpMyWordContentService;
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
 * Created by yanggavin on 15/10/15.
 */
@Service("mpMyWordContentService")
public class MpMyWordContentServiceImpl implements MpMyWordContentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(MyWordContentBean bean) {
        final MyWordContentBean fBean = bean;
        final String sql = "insert into mp_my_word_contents(user_id, content) value(?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps = jdbcTemplate.getDataSource().getConnection()
                                .prepareStatement(sql, new String[]{"user_id", "content"});
                        ps.setString(1, fBean.getUserId() + "");
                        ps.setString(2, fBean.getContent());
                        return ps;
                    }
                }
        , keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public boolean update(MyWordContentBean bean) {
        int ret = jdbcTemplate.update("update mp_my_word_contents set content=? where id=? and user_id=?",
                new Object[]{bean.getContent(), bean.getId(), bean.getUserId()},
                new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER});
        return ret == 1;
    }

    @Override
    public boolean delete(int id, int userId) {
        int ret = jdbcTemplate.update("delete from mp_my_word_contents where id=? and user_id=?",
                new Object[]{id, userId},
                new int[]{Types.INTEGER, Types.INTEGER});
        return ret == 1;
    }

    @Override
    public MyWordContentBean get(int id) {
        return (MyWordContentBean)jdbcTemplate.queryForObject("select * from mp_my_word_contents where id=?",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new MpMyWordContentRowMapper());
    }
}
