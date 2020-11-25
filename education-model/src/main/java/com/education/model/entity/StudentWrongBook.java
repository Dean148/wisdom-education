package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 学员错题本实体类
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 14:24
 */
@TableName("student_wrong_book")
public class StudentWrongBook extends BaseEntity<StudentWrongBook> {

    @TableField("student_id")
    private Integer studentId;
    @TableField("question_info_id")
    private Integer questionInfoId;
    @TableField("question_mark")
    private Integer questionMark;

    public StudentWrongBook() {

    }

    public StudentWrongBook(Integer studentId, Integer questionInfoId, Integer questionMark) {
        this.studentId = studentId;
        this.questionInfoId = questionInfoId;
        this.questionMark = questionMark;
    }

    public Integer getQuestionInfoId() {
        return questionInfoId;
    }

    public void setQuestionInfoId(Integer questionInfoId) {
        this.questionInfoId = questionInfoId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
}
