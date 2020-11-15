package com.education.mapper.course;

import com.education.common.base.BaseCommonMapper;
import com.education.common.model.ModelBeanMap;

import java.util.Map;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/9 15:56
 */
public interface SubjectInfoMapper extends BaseCommonMapper {

    ModelBeanMap findByNameAndGradeType(Map params);
}
