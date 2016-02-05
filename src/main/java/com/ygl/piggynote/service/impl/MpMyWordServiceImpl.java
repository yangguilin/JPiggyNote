package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.mp.MyWordBean;
import com.ygl.piggynote.bean.mp.MyWordContentBean;
import com.ygl.piggynote.rowmapper.MpMyWordRowMapper;
import com.ygl.piggynote.service.MpMyWordService;
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
import java.util.List;

/**
 * Created by yanggavin on 15/10/15.
 */
@Service("mpMyWordService")
public class MpMyWordServiceImpl implements MpMyWordService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(MyWordBean bean) {
        final MyWordBean fBean = bean;
        final String sql = "insert into mp_my_words(user_id, show_name, content_id) value(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps = jdbcTemplate.getDataSource().getConnection()
                                .prepareStatement(sql, new String[]{"user_id", "show_name", "content_id"});
                        ps.setInt(1, fBean.getUserId());
                        ps.setString(2, fBean.getShowName());
                        ps.setInt(3, fBean.getContentId());
                        return ps;
                    }
                }
                , keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public boolean update(MyWordBean bean) {
        int ret = jdbcTemplate.update("update mp_my_words set show_name=?, content_id=? where id=? and user_id=?",
                new Object[]{bean.getShowName(), bean.getContentId(), bean.getId(), bean.getUserId()},
                new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER});
        return ret == 1;
    }

    @Override
    public boolean delete(int id, int userId) {
        int ret = jdbcTemplate.update("delete from mp_my_words where id=? and user_id=?",
                new Object[]{id, userId},
                new int[]{Types.INTEGER, Types.INTEGER});
        return ret == 1;
    }

    @Override
    public List<MyWordBean> get(int userId) {
        return (List<MyWordBean>)jdbcTemplate.query("select * from mp_my_words where user_id=?",
                new Object[]{userId},
                new int[]{Types.INTEGER},
                new MpMyWordRowMapper());
    }

    @Override
    public MyWordBean get(int userId, String showName) {
        return (MyWordBean)jdbcTemplate.queryForObject("select * from mp_my_words where user_id=? and show_name=?",
                new Object[]{userId, showName},
                new int[]{Types.INTEGER, Types.VARCHAR},
                new MpMyWordRowMapper());
    }

    @Override
    public MyWordBean get(int userId, int id) {
        return (MyWordBean)jdbcTemplate.queryForObject("select * from mp_my_words where user_id=? and id=?",
                new Object[]{userId, id},
                new int[]{Types.INTEGER, Types.INTEGER},
                new MpMyWordRowMapper());
    }
}
