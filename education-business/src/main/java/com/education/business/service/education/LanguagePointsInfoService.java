package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.LanguagePointsInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import com.education.model.entity.LanguagePointsInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 15:10
 */
@Service
public class LanguagePointsInfoService extends BaseService<LanguagePointsInfoMapper, LanguagePointsInfo> {

    /**
     * 获取一级知识点
     * @param languagePointsInfo
     * @return
     */
    public List<LanguagePointsInfo> selectFirstPoints(LanguagePointsInfo languagePointsInfo) {
        languagePointsInfo.setParentId(ResultCode.FAIL);
        LambdaQueryWrapper queryWrapper = Wrappers.<LanguagePointsInfo>lambdaQuery()
                .eq(LanguagePointsInfo::getParentId, ResultCode.FAIL)
                .eq(ObjectUtils.isNotEmpty(languagePointsInfo.getGradeInfoId()), LanguagePointsInfo::getGradeInfoId, languagePointsInfo.getGradeInfoId())
                .eq(ObjectUtils.isNotEmpty(languagePointsInfo.getSubjectId()), LanguagePointsInfo::getSubjectId, languagePointsInfo.getSubjectId());
        return baseMapper.selectList(queryWrapper);
    }

    /**
     * 根据parentId 查找子节点
     * @param parentId
     * @return
     */
    public List<LanguagePointsInfo> selectByParentId(Integer parentId) {
        LambdaQueryWrapper queryWrapper = Wrappers.<LanguagePointsInfo>lambdaQuery()
                .eq(LanguagePointsInfo::getParentId, parentId);
        return baseMapper.selectList(queryWrapper);
    }
}
