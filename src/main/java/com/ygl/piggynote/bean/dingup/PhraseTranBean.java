package com.ygl.piggynote.bean.dingup;

import com.ygl.piggynote.enums.dingup.WordTranExamTypeEnum;
import com.ygl.piggynote.enums.dingup.TranStatusEnum;

import java.util.Date;

/**
 * Created by yanggavin on 16/3/2.
 */
public class PhraseTranBean {
    private int id;
    private WordTranExamTypeEnum examType;
    private String examSubType;
    private String examThirdType;
    private String phrase;
    private String originChMeaning;
    private String clsChMeaning;
    private String clsEnMeaning;
    private String clsEnEx;
    private String clsEnExTran;
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

    public WordTranExamTypeEnum getExamType() {
        return examType;
    }

    public void setExamType(WordTranExamTypeEnum examType) {
        this.examType = examType;
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

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getOriginChMeaning() {
        return originChMeaning;
    }

    public void setOriginChMeaning(String originChMeaning) {
        this.originChMeaning = originChMeaning;
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

    public TranStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TranStatusEnum status) {
        this.status = status;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public String getClsEnExTran() {
        return clsEnExTran;
    }

    public void setClsEnExTran(String clsEnExTran) {
        this.clsEnExTran = clsEnExTran;
    }
}
