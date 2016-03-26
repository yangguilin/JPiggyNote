package com.ygl.piggynote.enums.dingup;

/**
 * Created by yanggavin on 16/2/27.
 */
public enum WTEListeningThirdTypeEnum {
    DEFAULT(0, "默认值,无意义"),
    STUDENT_STUDENT(1, "学生与学生"),
    STUDENT_PROFESSOR(2, "学生与教授"),
    STUDENT_EMPLOYEE(3, "学生与雇员"),
    STUDENT_LIBRARIAN(4, "学生与图书管理员"),
    PROFESSOR_STUDENT(5, "教授与学生"),
    PROFESSOR_PROFESSOR(6, "教授与教授"),
    PROFESSOR_EMPLOYEE(7, "教授与雇员"),
    PROFESSOR_LIBRARIAN(8, "教授与图书管理员"),
    EMPLOYEE_STUDENT(9, "雇员与学生"),
    EMPLOYEE_PROFESSOR(10, "雇员与教授"),
    EMPLOYEE_EMPLOYEE(11, "雇员与雇员"),
    EMPLOYEE_LIBRARIAN(12, "雇员与图书管理员"),
    LIBRARIAN_STUDENT(13, "图书管理员与学生"),
    LIBRARIAN_PROFESSOR(14, "图书管理员与教授"),
    LIBRARIAN_EMPLOYEE(15, "图书管理员与雇员"),
    LIBRARIAN_LIBRARIAN(16, "图书管理员与图书管理员");

    private int value;
    private String des;


    public int getValue() {
        return value;
    }
    public String getDes(){
        return des;
    }

    WTEListeningThirdTypeEnum(int value, String des) {
        this.value = value;
        this.des = des;
    }
}
