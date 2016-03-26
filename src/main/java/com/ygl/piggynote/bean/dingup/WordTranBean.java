package com.ygl.piggynote.bean.dingup;

import com.ygl.piggynote.enums.dingup.WordTranExamTypeEnum;
import com.ygl.piggynote.enums.dingup.TranStatusEnum;

import java.util.Date;

/**
 * Created by yanggavin on 16/2/26.
 */
public class WordTranBean {
    private int id;
    private String entityType;
    private String pron;
    private WordTranExamTypeEnum examType;
    private String examSubType;
    private String examThirdType;
    private String wordName;
    private String originChMeaning;
    private String clsChMeaning;
    private String clsEnMeaning;
    private String clsChEx;
    private String clsEnEx;
    private String clsFullResult;
    private Date createTime;
    private Date lastUpdateTime;
    private TranStatusEnum status;
    private int freq;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public WordTranExamTypeEnum getExamType() {
        return examType;
    }

    public void setExamType(WordTranExamTypeEnum examType) {
        this.examType = examType;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getOriginChMeaning() {
        return originChMeaning;
    }

    public void setOriginChMeaning(String originChMeaning) {
        this.originChMeaning = originChMeaning;
    }

    public String getClsChEx() {
        return clsChEx;
    }

    public void setClsChEx(String clsChEx) {
        this.clsChEx = clsChEx;
    }

    public String getClsEnEx() {
        return clsEnEx;
    }

    public void setClsEnEx(String clsEnEx) {
        this.clsEnEx = clsEnEx;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getClsChMeaning() {
        return clsChMeaning;
    }

    public void setClsChMeaning(String clsChMeaning) {
        this.clsChMeaning = clsChMeaning;
    }

    public String getClsEnMeaning() {
        return clsEnMeaning;
    }

    public void setClsEnMeaning(String clsEnMeaning) {
        this.clsEnMeaning = clsEnMeaning;
    }

    public String getClsFullResult() {
        return clsFullResult;
    }

    public void setClsFullResult(String clsFullResult) {
        this.clsFullResult = clsFullResult;
    }

    public String getExamSubType() {
        return examSubType;
    }

    public void setExamSubType(String examSubType) {
        this.examSubType = examSubType;
    }

    public String getExamThirdType() {
        return examThirdType;
    }

    public void setExamThirdType(String examThirdType) {
        this.examThirdType = examThirdType;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public TranStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TranStatusEnum status) {
        this.status = status;
    }

    public String getPron() {
        return pron;
    }

    public void setPron(String pron) {
        this.pron = pron;
    }
}
