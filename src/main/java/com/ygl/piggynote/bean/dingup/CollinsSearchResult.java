package com.ygl.piggynote.bean.dingup;

import java.util.List;

/**
 * Created by yanggavin on 16/3/4.
 */
public class CollinsSearchResult {
    private String searchWord;
    private String type;
    private CollinsHeadWord headWord;
    private List<String> inflection;
    private int freq;
    private List<CollinsSense> senseList;
    private List<CollinsPhvb> phvbList;

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CollinsHeadWord getHeadWord() {
        return headWord;
    }

    public void setHeadWord(CollinsHeadWord headWord) {
        this.headWord = headWord;
    }

    public List<String> getInflection() {
        return inflection;
    }

    public void setInflection(List<String> inflection) {
        this.inflection = inflection;
    }

    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public List<CollinsSense> getSenseList() {
        return senseList;
    }

    public void setSenseList(List<CollinsSense> senseList) {
        this.senseList = senseList;
    }

    public List<CollinsPhvb> getPhvbList() {
        return phvbList;
    }

    public void setPhvbList(List<CollinsPhvb> phvbList) {
        this.phvbList = phvbList;
    }
}
