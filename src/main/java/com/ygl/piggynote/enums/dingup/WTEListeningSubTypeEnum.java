package com.ygl.piggynote.enums.dingup;

/**
 * Created by yanggavin on 16/2/27.
 */
public enum WTEListeningSubTypeEnum {
    DEFAULT(0, "默认值,无意义"),
    CONVERSATION(1, "对话"),
    LECTURE(2, "讲座");

    private int value;
    private String des;


    public int getValue() {
        return value;
    }
    public String getDes(){
        return des;
    }

    WTEListeningSubTypeEnum(int value, String des) {
        this.value = value;
        this.des = des;
    }
}
