package com.ygl.piggynote.enums.dingup;

/**
 * Created by yanggavin on 16/2/26.
 */
public enum CollinsEntityEnum {
    DEFAULT("", "默认值,无意义"),
    N_COUNT("N-COUNT", "名词"),
    VERB("VERB", "动词"),
    ADJ_GRADED("ADJ-GRADED", "形容词");

    private String value;
    private String des;


    public String getValue() {
        return value;
    }
    public String getDes(){
        return des;
    }

    CollinsEntityEnum(String value, String des) {
        this.value = value;
        this.des = des;
    }
}
