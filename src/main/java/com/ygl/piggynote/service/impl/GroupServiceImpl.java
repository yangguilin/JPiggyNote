package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.GroupBean;
import com.ygl.piggynote.bean.QueryCountBean;
import com.ygl.piggynote.rowmapper.GroupRowMapper;
import com.ygl.piggynote.rowmapper.QueryCountRowMapper;
import com.ygl.piggynote.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;

/**
 * Created by yanggavin on 15/3/17.
 */
@Service("pn_groupService")
public class GroupServiceImpl implements GroupService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public GroupBean get(int id) {
        return (GroupBean)jdbcTemplate.queryForObject("select * from pn_groups where group_id=?",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new GroupRowMapper());
    }

    @Override
    public Boolean add(GroupBean bean) {
        int ret = jdbcTemplate.update("insert into pn_groups(group_name, deleted, active_num) value(?, 0, 0)",
                new Object[]{bean.getGroupName()},
                new int[]{Types.VARCHAR});
        return ret == 1;
    }

    @Override
    public Boolean update(GroupBean bean) {
        int ret = jdbcTemplate.update("update pn_groups set group_name=?, active_num=? where group_id=?",
                new Object[]{bean.getGroupName(), bean.getActiveNum(), bean.getGroupId()},
                new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER});
        return ret == 1;
    }

    @Override
    public Boolean delete(int id) {
        int ret = jdbcTemplate.update("update pn_groups set deleted=1 where group_id=?",
                new Object[]{id},
                new int[]{Types.INTEGER});
        return ret == 1;
    }

    @Override
    public Boolean exist(int id) {
        QueryCountBean qcb = (QueryCountBean)jdbcTemplate.queryForObject("select count(*) as num from pn_groups where group_id=?",
                new Object[]{id},
                new int[]{Types.INTEGER},
                new QueryCountRowMapper());
        return qcb.getNum() > 0;
    }
}
