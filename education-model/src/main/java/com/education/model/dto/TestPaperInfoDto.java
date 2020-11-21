package com.education.model.dto;

import com.education.model.entity.TestPaperInfo;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/20 21:28
 */
public class TestPaperInfoDto extends TestPaperInfo {

    private String subjectName;
    private String gradeInfoName;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getGradeInfoName() {
        return gradeInfoName;
    }

    public void setGradeInfoName(String gradeInfoName) {
        this.gradeInfoName = gradeInfoName;
    }
}
