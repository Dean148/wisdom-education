package com.education.mapper.system;

import com.education.common.base.BaseCommonMapper;
import com.education.common.model.ModelBeanMap;

import java.util.List;
import java.util.Map;

public interface SystemDictValueMapper extends BaseCommonMapper {

    List<ModelBeanMap> getDictValueByType(Map params);

    List<ModelBeanMap> getDictValueByParentId(Map params);

    int deleteByDictId(Integer dictId);

}
