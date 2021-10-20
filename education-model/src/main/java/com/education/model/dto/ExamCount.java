package com.education.model.dto;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/2/1 12:17
 */
public class ExamCount {

    private String dayGroup;
    private int examNumber;


    public String getDayGroup() {
        return dayGroup;
    }

    public void setDayGroup(String dayGroup) {
        this.dayGroup = dayGroup;
    }

    public int getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(int examNumber) {
        this.examNumber = examNumber;
    }
}
