package com.ygl.piggynote.rowmapper.dingup;

import com.ygl.piggynote.bean.dingup.PhraseTranBean;
import com.ygl.piggynote.enums.dingup.TranStatusEnum;
import com.ygl.piggynote.enums.dingup.WordTranExamTypeEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 16/3/2.
 */
public class PhraseTranRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        PhraseTranBean bean = new PhraseTranBean();
        bean.setId(rs.getInt("id"));
        bean.setExamType(WordTranExamTypeEnum.getByValue(rs.getInt("exam_type")));
        bean.setExamSubType(rs.getString("exam_sub_type"));
        bean.setExamThirdType(rs.getString("exam_third_type"));
        bean.setPhrase(rs.getString("phrase"));
        bean.setOriginChMeaning(rs.getString("origin_ch_meaning"));
        bean.setClsChMeaning(rs.getString("cls_ch_meaning"));
        bean.setClsEnMeaning(rs.getString("cls_en_meaning"));
        bean.setClsEnEx(rs.getString("cls_en_ex"));
        bean.setClsEnExTran(rs.getString("cls_en_ex_tran"));
        bean.setCreateTime(rs.getDate("create_time"));
        bean.setLastUpdateTime(rs.getDate("last_update_time"));
        bean.setStatus(TranStatusEnum.getByValue(rs.getInt("status")));
        bean.setFreq(rs.getInt("freq"));
        return bean;
    }
}