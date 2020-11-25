package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.LanguagePointsInfoMapper;
import com.education.business.service.BaseService;
import com.education.model.dto.LanguagePointsInfoDto;
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
    public List<LanguagePointsInfoDto> selectList(LanguagePointsInfo languagePointsInfo) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(languagePointsInfo);
        return baseMapper.selectList(queryWrapper);
    }
}
