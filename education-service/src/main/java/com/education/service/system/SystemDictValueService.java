package com.education.service.system;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.mapper.system.SystemDictValueMapper;
import com.education.model.entity.SystemDictValue;
import com.education.service.BaseService;
import org.springframework.stereotype.Service;



@Service
public class SystemDictValueService extends BaseService<SystemDictValueMapper, SystemDictValue> {

    public void deleteByDictId(Integer dictId) {
        // 获取删除字典值id
        QueryWrapper queryWrapper = Wrappers.query().eq("system_dict_id", dictId);
        baseMapper.delete(queryWrapper);
    }

  /*  private static final String DICT_VALUE_CACHE_NAME = "system:dict:value:";

    public List<ModelBeanMap> getDictValueTreeList() {
        return MapTreeUtils.buildTreeData(mapper.treeList());
    }

    public List<ModelBeanMap> getDictValueByType(Map params) {
        String key = (String) params.get("type");
        List<ModelBeanMap> dictValueList = ehcacheBean.get(Constants.ONE_MINUTE_CACHE, DICT_VALUE_CACHE_NAME + key);
        if (ObjectUtils.isEmpty(dictValueList)) {
            dictValueList = mapper.getDictValueByType(params);
            ehcacheBean.put(Constants.ONE_MINUTE_CACHE, DICT_VALUE_CACHE_NAME + key, dictValueList);
        }
        return dictValueList;
    }

    public List<ModelBeanMap> getDictValueByParentId(Map params) {
        return mapper.getDictValueByParentId(params);
    }

    public Integer getDictValueByName(String type, String name) {
        Map params = new HashMap<>();
        params.put("type", type);
        List<ModelBeanMap> gradeTypeList = this.getDictValueByType(params);
        if (ObjectUtils.isNotEmpty(gradeTypeList)) {
            for (ModelBeanMap grade : gradeTypeList) {
                if (name.equals(grade.getStr("value"))) {
                    return grade.getInt("code");
                }
            }
        }
        return null;
    }

    public ModelBeanMap getDictValueForMapByName(String type, String name) {
        Map params = new HashMap<>();
        params.put("type", type);
        List<ModelBeanMap> dictValueList = this.getDictValueByType(params);
        if (ObjectUtils.isNotEmpty(dictValueList)) {
            for (ModelBeanMap dictValue : dictValueList) {
                if (name.equals(dictValue.getStr("value"))) {
                    return dictValue;
                }
            }
        }
        return null;
    }

    public String getDictNameByValue(String type, Integer code) {
        Map params = new HashMap<>();
        params.put("type", type);
        List<ModelBeanMap> gradeTypeList = this.getDictValueByType(params);
        if (ObjectUtils.isNotEmpty(gradeTypeList)) {
            for (ModelBeanMap grade : gradeTypeList) {
                if (grade.getInt("code") == code) {
                    return grade.getStr("value");
                }
            }
        }
        return null;
    }

    public int deleteByDictId(Integer dictId) {
        return mapper.deleteByDictId(dictId);
    }

    public int deleteDictValueById(ModelBeanMap dictValueBeanMap) {
        return mapper.deleteById(dictValueBeanMap.getInt("id"));
    }*/
}
