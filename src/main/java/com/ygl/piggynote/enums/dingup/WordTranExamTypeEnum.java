package com.ygl.piggynote.enums.dingup;

/**
 * Created by yanggavin on 16/2/27.
 */
public enum WordTranExamTypeEnum {
    DEFAULT(0, "默认值,无意义"),
    LISTENING(1, "听"),
    SPEAKING(2, "说"),
    READING(3, "读"),
    WRITING(4, "写");

    private int value;
    private String des;


    public int getValue() {
        return value;
    }
    public String getDes(){
        return des;
    }

    WordTranExamTypeEnum(int value, String des) {
        this.value = value;
        this.des = des;
    }

    public static WordTranExamTypeEnum getByValue(int value){
        switch (value){
            case 1:
                return LISTENING;
            case 2:
                return SPEAKING;
            case 3:
                return READING;
            case 4:
                return WRITING;
            default:
                return DEFAULT;
        }
    }
}
