package com.ygl.piggynote.rowmapper;

import com.ygl.piggynote.bean.InvestmentBean;
import com.ygl.piggynote.bean.InvestmentRecordBean;
import com.ygl.piggynote.enums.InvestmentTypeEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanggavin on 15/5/11.
 */
public class InvestmentRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        InvestmentBean bean = new InvestmentBean();
        bean.setId(rs.getInt("id"));
        bean.setName(rs.getString("name"));
        bean.setType(InvestmentTypeEnum.getEnumByValue(rs.getString("type")));
        bean.setDescription(rs.getString("description"));
        bean.setPrincipalNum(rs.getFloat("principal_num"));
        bean.setCreateDate(rs.getDate("create_date"));
        bean.setRecords(genRecordListByStr(rs.getString("records")));
        bean.setFinalNum(rs.getFloat("final_num"));
        bean.setFinishDate(rs.getDate("finish_date"));
        bean.setFinishStatus(rs.getBoolean("finish_status"));
        bean.setUserName(rs.getString("user_name"));
        bean.setCurrentNum(rs.getFloat("current_num"));
        return bean;
    }

    private List<InvestmentRecordBean> genRecordListByStr(String records){
        List<InvestmentRecordBean> list = new ArrayList<InvestmentRecordBean>();
        if (!records.isEmpty()) {
            String[] arr = records.split(";");
            for (int i = 0; i < arr.length; i++) {
                String[] arr2 = arr[i].split(",");
                InvestmentRecordBean bean = new InvestmentRecordBean();
                bean.setDate(arr2[0]);
                bean.setNum(Float.parseFloat(arr2[1]));
                list.add(bean);
            }
        }
        return list;
    }
}
