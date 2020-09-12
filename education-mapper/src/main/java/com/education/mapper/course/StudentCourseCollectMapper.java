package com.education.mapper.course;

import com.education.common.base.BaseMapper;
import com.education.common.model.ModelBeanMap;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/9/8 21:45
 */
public interface StudentCourseCollectMapper extends BaseMapper<StudentCourseCollectMapper> {


    /**
     * 删除收藏课程
     * @param studentId
     * @param courseId
     * @return
     */
    @Delete("delete from student_course_collect where student_id = #{studentId} and course_id = #{courseId}")
    int deleteCollectCourse(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);

}
