package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.mp.MyWordBean;
import com.ygl.piggynote.rowmapper.MpMyWordRowMapper;
import com.ygl.piggynote.service.MpMyWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
    public boolean add(MyWordBean bean) {
        int ret = jdbcTemplate.update("insert into mp_my_words(user_id, show_name, content_id) value(?, ?, ?)",
                new Object[]{bean.getUserId(), bean.getShowName(), bean.getContentId()},
                new int[]{Types.INTEGER, Types.VARCHAR, Types.INTEGER});
        return ret == 1;
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
    public List<MyWordBean> getByUserId(int userId) {
        return (List<MyWordBean>)jdbcTemplate.query("select * from mp_my_words where user_id=?",
                new Object[]{userId},
                new int[]{Types.INTEGER},
                new MpMyWordRowMapper());
    }
}
