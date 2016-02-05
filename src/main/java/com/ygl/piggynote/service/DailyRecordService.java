package com.ygl.piggynote.service;

import com.ygl.piggynote.bean.DailyRecordBean;

import java.util.List;

/**
 * 日常账目服务接口
 * Created by yanggavin on 14-7-18.
 */
public interface DailyRecordService {

    /**
     * 添加一条新纪录
     * @param   bean    记录实例
     * @return          是否成功
     */
    public Boolean add(DailyRecordBean bean);

    /**
     * 添加一条新纪录（含创建时间）
     * @param   bean    记录实例
     * @return          是否成功
     */
    public Boolean addByCreateDate(DailyRecordBean bean);

    /**
     * 通过记录id删除某用户的一条记录
     * @param id            记录id
     * @param userName      用户名
     * @return              是否成功
     */
    public Boolean delete(int id, String userName);

    /**
     * 更新一条记录内容
     * @param bean  记录实例
     * @return      是否成功
     */
    public Boolean update(DailyRecordBean bean);

    /**
     * 通过时间区间获取某用户的记录列表
     * @param beginDate     开始时间，格式“2014-07-18 12:11:11”
     * @param endDate       结束时间，格式“2014-07-18 12:11:11”
     * @param userName      用户名
     * @return              记录列表
     */
    public List<DailyRecordBean> getRecordsByDateRange(String beginDate, String endDate, String userName);

    /**
     * 获取用户全部记录数据
     * @param userName      用户名
     * @return              记录列表
     */
    public List<DailyRecordBean> getAllRecords(String userName);
}
