package com.education.mapper.school;

import com.education.common.base.BaseCommonMapper;
import com.education.common.model.ModelBeanMap;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/9 14:36
 */
public interface SchoolInfoMapper extends BaseCommonMapper {

    List<ModelBeanMap> getSchoolRegionInfo();
}
