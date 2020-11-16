package com.education.mapper.school;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.common.model.ModelBeanMap;
import com.education.model.entity.SchoolInfo;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/9 14:36
 */
public interface SchoolInfoMapper extends BaseMapper<SchoolInfo> {

    List<ModelBeanMap> getSchoolRegionInfo();
}
