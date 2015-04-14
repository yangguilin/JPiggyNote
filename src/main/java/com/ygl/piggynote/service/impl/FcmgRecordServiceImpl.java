package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.FcmgRecordBean;
import com.ygl.piggynote.rowmapper.FcmgRecordRowMapper;
import com.ygl.piggynote.service.FcmgRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.List;

/**
 * Created by yanggavin on 15/4/14.
 */
@Service("pnFcmgRecordService")
public class FcmgRecordServiceImpl implements FcmgRecordService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<FcmgRecordBean> getTop10Record(int sideNum, int goldNum, int glanceTime) {
        return (List<FcmgRecordBean>)jdbcTemplate.query("select * from pn_fcmg where side_num=? and gold_num=? and glance_time=? order by cost_second desc limit 0,10",
                new Object[]{sideNum, goldNum, glanceTime},
                new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER},
                new FcmgRecordRowMapper());
    }

    @Override
    public Boolean add(FcmgRecordBean bean) {
        int ret = jdbcTemplate.update("insert into pn_fcmg(player_name, cost_second, side_num, gold_num, glance_time, create_time) value(?, ?, ?, ?, ?, now())",
                new Object[]{bean.getPlayerName(), bean.getCostSecond(), bean.getSideNum(), bean.getGoldNum(), bean.getGlanceTime()},
                new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER});
        return ret == 1;
    }
}
