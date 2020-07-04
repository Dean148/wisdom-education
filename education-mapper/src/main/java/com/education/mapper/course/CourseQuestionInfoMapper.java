package com.education.mapper.course;

import com.education.common.base.BaseMapper;
import com.education.common.model.ModelBeanMap;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/9 15:57
 */
public interface CourseQuestionInfoMapper extends BaseMapper {

    String GET_COURSE_QUESTION_LIST = "getCourseQuestionList";
    /**
     * 获取课程试题列表
     * @param params
     * @return
     */
    List<ModelBeanMap> getCourseQuestionList(Map params);

    List<ModelBeanMap> getCourseQuestionAnswerList(Map params);

    int delete(Map params);

    int updateCourseQuestionMark(Map params);

    int updateCourseQuestionSort(Map params);

    long getStudyNumberByCourse(Integer courseId);

    @Delete("delete from course_question_info where course_id = #{courseId} and question_info_id = #{questionId}")
    int deleteByCourseId(Map params);
}
