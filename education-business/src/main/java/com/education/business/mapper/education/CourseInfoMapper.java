package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.dto.CourseInfoDto;
import com.education.model.entity.CourseInfo;
import org.apache.ibatis.annotations.Update;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 18:46
 */
public interface CourseInfoMapper extends BaseMapper<CourseInfo> {

    /**
     * 课程分页列表
     * @param page
     * @param courseInfo
     * @return
     */
    Page<CourseInfoDto> selectPageList(Page<CourseInfoDto> page, CourseInfo courseInfo);

    /**
     * 课程章数量加一
     * @param id
     */
    @Update("update course_info set section_number = section_number + 1 where id = #{id}")
    void increaseSectionNumber(Integer id);


    /**
     * 课程章数量减一
     * @param id
     */
    @Update("update course_info set section_number = section_number - 1 where id = #{id}")
    void decreaseSectionNumber(Integer id);

    /**
     * 课程课时数量加一
     * @param id
     */
    @Update("update course_info set section_node_number = section_node_number + 1 where id = #{id}")
    void increaseSectionNodeNumber(Integer id);

    /**
     * 课程课时数量加一
     * @param id
     */
    @Update("update course_info set section_node_number = section_node_number - 1 where id = #{id}")
    void decreaseSectionNodeNumber(Integer id);


   // @Update("update course_info set valuate_mark = valuate_mark + #{} where id = #{id}")
    void updatCommentNumberAndValuateMark(Integer courseId);

    /**
     * 课程评论数量加1
     * @param courseId
     */
    @Update("update course_info set comment_number = comment_number + 1 where id = #{id}")
    void increaseCommentNumber(Integer courseId);
}
