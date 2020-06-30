package com.education.mapper.course;

import com.education.common.base.BaseMapper;
import com.education.common.model.ModelBeanMap;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/9 15:44
 */
public interface QuestionInfoMapper extends BaseMapper {

    ModelBeanMap findBySubjectId(Integer subjectId);

    @Select("select * from question_info where language_points_id = #{languagePointsId} limit 1")
    ModelBeanMap findByLanguagePointsId(@Param("languagePointsId") Integer languagePointsId);
}
