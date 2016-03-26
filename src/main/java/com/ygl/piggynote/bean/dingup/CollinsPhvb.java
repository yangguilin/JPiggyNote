package com.ygl.piggynote.bean.dingup;

import java.util.List;

/**
 * Created by yanggavin on 16/3/4.
 */
public class CollinsPhvb {
    private CollinsHeadWord headWord;
    private List<CollinsSense> senseList;

    public CollinsHeadWord getHeadWord() {
        return headWord;
    }

    public void setHeadWord(CollinsHeadWord headWord) {
        this.headWord = headWord;
    }

    public List<CollinsSense> getSenseList() {
        return senseList;
    }

    public void setSenseList(List<CollinsSense> senseList) {
        this.senseList = senseList;
    }
}
