package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

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
    private  Boolean showMrkSort;

    @TableField("show_student_sort")
    private Boolean showStudentSort;

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

    public Boolean getShowMrkSort() {
        return showMrkSort;
    }

    public void setShowMrkSort(Boolean showMrkSort) {
        this.showMrkSort = showMrkSort;
    }

    public Boolean getShowStudentSort() {
        return showStudentSort;
    }

    public void setShowStudentSort(Boolean showStudentSort) {
        this.showStudentSort = showStudentSort;
    }
}
