package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.FriendBean;
import com.ygl.piggynote.bean.QueryCountBean;
import com.ygl.piggynote.rowmapper.FriendRowMapper;
import com.ygl.piggynote.rowmapper.QueryCountRowMapper;
import com.ygl.piggynote.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

/**
 * Created by yanggavin on 15/3/12.
 */
@Service("pnFriendService")
public class FriendServiceImpl implements FriendService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Boolean add(FriendBean bean) {
        int ret = jdbcTemplate.update("insert into pn_friends(user_name, friend_name, create_time, status) value(?, ?, now(), ?)",
                new Object[]{bean.getUserName(), bean.getFriendName(), bean.getStatus()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER});
        return ret == 1;
    }

    @Override
    public Boolean update(FriendBean bean) {
        int ret = jdbcTemplate.update("update pn_friends set friend_name=?, create_time=now(), status=? where user_name=?",
                new Object[]{ bean.getFriendName(), bean.getStatus(), bean.getUserName() },
                new int[]{Types.VARCHAR, Types.INTEGER, Types.VARCHAR});
        return ret > 0;
    }

    @Override
    public Boolean delete(FriendBean bean) {
        int ret = jdbcTemplate.update("delete from pn_friends where user_name=? and friend_name=?",
                new Object[]{bean.getUserName(), bean.getFriendName()},
                new int[]{Types.VARCHAR, Types.VARCHAR});
        return ret > 0;
    }

    @Override
    public Boolean exist(FriendBean bean){
        QueryCountBean qcb = (QueryCountBean)jdbcTemplate.queryForObject("select count(id) as num from pn_friends where user_name=? and friend_name=?",
                new Object[]{bean.getUserName(), bean.getFriendName()},
                new int[]{Types.VARCHAR, Types.VARCHAR},
                new QueryCountRowMapper());
        return qcb.getNum() > 0;
    }

    @Override
    public List<FriendBean> getFriendsByUserName(String userName) {
        return (List<FriendBean>)jdbcTemplate.query("select * from pn_friends where user_name=? order by create_time desc",
                new Object[]{userName},
                new int[]{Types.VARCHAR},
                new FriendRowMapper());
    }

    @Override
    public List<FriendBean> getFriendsByFriendName(String friendName) {
        return (List<FriendBean>)jdbcTemplate.query("select * from pn_friends where friend_name=? order by create_time desc",
                new Object[]{friendName},
                new int[]{Types.VARCHAR},
                new FriendRowMapper());
    }
}
