package com.ygl.piggynote.rowmapper.dingup;

import com.ygl.piggynote.bean.dingup.WordTranBean;
import com.ygl.piggynote.enums.dingup.WordTranExamTypeEnum;
import com.ygl.piggynote.enums.dingup.TranStatusEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by yanggavin on 16/2/27.
 */
public class WordTranRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        WordTranBean bean = new WordTranBean();
        bean.setId(rs.getInt("id"));
        bean.setEntityType(rs.getString("entity_type"));
        bean.setPron(rs.getString("pron"));
        bean.setExamType(WordTranExamTypeEnum.getByValue(rs.getInt("exam_type")));
        bean.setExamSubType(rs.getString("exam_sub_type"));
        bean.setExamThirdType(rs.getString("exam_third_type"));
        bean.setWordName(rs.getString("word_name"));
        bean.setOriginChMeaning(rs.getString("origin_ch_meaning"));
        bean.setClsChMeaning(rs.getString("cls_ch_meaning"));
        bean.setClsEnMeaning(rs.getString("cls_en_meaning"));
        bean.setClsChEx(rs.getString("cls_ch_ex"));
        bean.setClsEnEx(rs.getString("cls_en_ex"));
        bean.setClsFullResult(rs.getString("cls_full_result"));
        bean.setCreateTime(rs.getDate("create_time"));
        bean.setLastUpdateTime(rs.getDate("last_update_time"));
        bean.setStatus(TranStatusEnum.getByValue(rs.getInt("status")));
        bean.setFreq(rs.getInt("freq"));
        return bean;
    }
}
