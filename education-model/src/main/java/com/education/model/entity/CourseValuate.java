package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 课程评价实体类
 * @author zengjintao
 * @create_at 2021/10/17 9:29
 * @since version 1.0.3
 */
@TableName("course_valuate")
public class CourseValuate extends BaseEntity<CourseValuate> {

    @NotNull(message = "课程id不能为空")
    @TableField("course_id")
    private Integer courseId;

    @TableField("student_id")
    private Integer studentId;

    @NotBlank(message = "请输入评价内容")
    private String content;

    @TableField("valuate_type")
    private Integer valuateType;

    @NotNull(message = "请评价课程分数")
    private Integer level;


    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getValuateType() {
        return valuateType;
    }

    public void setValuateType(Integer valuateType) {
        this.valuateType = valuateType;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
