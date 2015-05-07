package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.FcmgRecordBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 15/4/14.
 */
public class FcmgRecordRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        FcmgRecordBean bean = new FcmgRecordBean();
        bean.setId(rs.getInt("id"));
        bean.setPlayerName(rs.getString("player_name"));
        bean.setCostSecond(rs.getInt("cost_second"));
        bean.setSideNum(rs.getInt("side_num"));
        bean.setGoldNum(rs.getInt("gold_num"));
        bean.setGlanceTime(rs.getInt("glance_time"));
        bean.setCreateTime(rs.getDate("create_time"));
        bean.setMoveStepNum(rs.getInt("move_step_num"));
        return bean;
    }
}
