package com.ygl.piggynote.bean.dingup;

import java.io.Serializable;

/**
 * Created by yanggavin on 16/2/24.
 */
public class SearchTranBean implements Serializable {
    private int id;
    private String wordName;
    private String chineseMeaning;
    private String clsChineseMeaning;
    private String englishMeaning;
    private String chineseEx;
    private String englishEx;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChineseMeaning() {
        return chineseMeaning;
    }

    public void setChineseMeaning(String chineseMeaning) {
        this.chineseMeaning = chineseMeaning;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getEnglishMeaning() {
        return englishMeaning;
    }

    public void setEnglishMeaning(String englishMeaning) {
        this.englishMeaning = englishMeaning;
    }

    public String getChineseEx() {
        return chineseEx;
    }

    public void setChineseEx(String chineseEx) {
        this.chineseEx = chineseEx;
    }

    public String getEnglishEx() {
        return englishEx;
    }

    public void setEnglishEx(String englishEx) {
        this.englishEx = englishEx;
    }

    public String getClsChineseMeaning() {
        return clsChineseMeaning;
    }

    public void setClsChineseMeaning(String clsChineseMeaning) {
        this.clsChineseMeaning = clsChineseMeaning;
    }
}
