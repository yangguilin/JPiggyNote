package com.ygl.piggynote.bean;

import com.ygl.piggynote.enums.InvestmentTypeEnum;
import com.ygl.piggynote.util.CommonUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by yanggavin on 15/5/11.
 */
public class InvestmentBean {
    private int id;
    private String name;
    private InvestmentTypeEnum type;
    private String description;
    private float principalNum;
    private Date createDate;
    private List<InvestmentRecordBean> records;
    private float finalNum;
    private Date finishDate;
    private Boolean finishStatus;
    private String userName;
    private float currentNum;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InvestmentTypeEnum getType() {
        return type;
    }

    public void setType(InvestmentTypeEnum type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrincipalNum() {
        return principalNum;
    }

    public void setPrincipalNum(float principalNum) {
        this.principalNum = principalNum;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<InvestmentRecordBean> getRecords() {
        return records;
    }

    public void setRecords(List<InvestmentRecordBean> records) {
        this.records = records;
    }

    public float getFinalNum() {
        return finalNum;
    }

    public void setFinalNum(float finalNum) {
        this.finalNum = finalNum;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Boolean isFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(Boolean finishStatus) {
        this.finishStatus = finishStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public float getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(float currentNum) {
        this.currentNum = currentNum;
    }

    public String getRecordsStr(){
        String val = "";
        for (InvestmentRecordBean bean : records){
            val += bean.getDate() + "," + bean.getNum() + ";";
        }
        return val.isEmpty() ? "" : CommonUtil.removeLastWord(val);
    }
}
