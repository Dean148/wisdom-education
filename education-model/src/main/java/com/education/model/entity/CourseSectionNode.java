package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zengjintao
 * @create_at 2021/10/6 9:55
 * @since version 1.0.3
 */
@TableName("course_section_node")
public class CourseSectionNode extends BaseEntity<CourseSectionNode> {

    @TableField("course_id")
    @NotNull(message = "请选择课程")
    private Integer courseId;

    @NotBlank(message = "请添加课时标题")
    private String title;

    @TableField("course_section_id")
    @NotNull(message = "请选择课时章节")
    private Integer courseSectionId;

    private String enclosure;

    @TableField("video_info")
    private String videoInfo;

    @TableField("free_flag")
    private Integer freeFlag;

    private String synopsis;

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Integer getFreeFlag() {
        return freeFlag;
    }

    public void setFreeFlag(Integer freeFlag) {
        this.freeFlag = freeFlag;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCourseSectionId() {
        return courseSectionId;
    }

    public void setCourseSectionId(Integer courseSectionId) {
        this.courseSectionId = courseSectionId;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public String getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(String videoInfo) {
        this.videoInfo = videoInfo;
    }
}
