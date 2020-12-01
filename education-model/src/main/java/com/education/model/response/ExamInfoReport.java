package com.education.model.response;

import com.education.model.entity.ExamInfo;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/1 15:26
 */
public class ExamInfoReport extends ExamInfo {

    private float avgSource; // 试卷平均分
    private String gradeName;
    private String subjectName;

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public float getAvgSource() {
        return avgSource;
    }

    public void setAvgSource(float avgSource) {
        this.avgSource = avgSource;
    }
}
