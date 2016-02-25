package com.ygl.piggynote.bean.dingup;

import java.io.Serializable;

/**
 * Created by yanggavin on 16/2/24.
 */
public class SearchTranBean implements Serializable {
    private int id;
    private String wordName;
    private String chineseMeaning;
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
}
