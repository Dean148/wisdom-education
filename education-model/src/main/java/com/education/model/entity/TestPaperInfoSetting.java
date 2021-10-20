package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * 试卷设置表实体类
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/30 21:16
 */
@TableName("test_paper_info_setting")
public class TestPaperInfoSetting extends BaseEntity<TestPaperInfoSetting> {

    @TableField("test_paper_info_id")
    private Integer testPaperInfoId;

    @TableField("commit_after_type")
    private Integer commitAfterType;

    @TableField("show_mark_sort")
    private Integer showMrkSort;

    @TableField("show_student_sort")
    private Integer showStudentSort;

    @TableField("start_time")
    private Date startTime;

    @TableField("end_time")
    private Date endTime;

    @TableField("exam_type")
    private Integer examType;

    @TableField("reference_number")
    private Integer referenceNumber;

    public Integer getTestPaperInfoId() {
        return testPaperInfoId;
    }

    public void setTestPaperInfoId(Integer testPaperInfoId) {
        this.testPaperInfoId = testPaperInfoId;
    }

    public Integer getCommitAfterType() {
        return commitAfterType;
    }

    public void setCommitAfterType(Integer commitAfterType) {
        this.commitAfterType = commitAfterType;
    }

    public Integer getShowMrkSort() {
        return showMrkSort;
    }

    public void setShowMrkSort(Integer showMrkSort) {
        this.showMrkSort = showMrkSort;
    }

    public Integer getShowStudentSort() {
        return showStudentSort;
    }

    public void setShowStudentSort(Integer showStudentSort) {
        this.showStudentSort = showStudentSort;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getExamType() {
        return examType;
    }

    public void setExamType(Integer examType) {
        this.examType = examType;
    }
}
