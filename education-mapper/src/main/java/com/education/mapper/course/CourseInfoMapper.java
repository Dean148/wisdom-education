package com.education.mapper.course;

import com.education.common.base.BaseMapper;
import com.education.common.model.ModelBeanMap;

import java.util.List;
import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/9 15:58
 */
public interface CourseInfoMapper extends BaseMapper {

    static final String GET_RECOMMEND_COURSE_LIST = "getRecommendCourseList";


    List<ModelBeanMap> getRecommendCourseList(Map params);

}
