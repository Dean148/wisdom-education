package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.LanguagePointsInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import com.education.model.dto.LanguagePointsInfoDto;
import com.education.model.entity.LanguagePointsInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 知识点管理service
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
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(LanguagePointsInfo.class)
                .eq(LanguagePointsInfo::getParentId, languagePointsInfo.getParentId())
                .like(ObjectUtils.isNotEmpty(languagePointsInfo.getName()),
                        LanguagePointsInfo::getName, languagePointsInfo.getName());
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public boolean saveOrUpdate(LanguagePointsInfo languagePointsInfo) {
        boolean flag = super.saveOrUpdate(languagePointsInfo);
        if (flag) {
            if (languagePointsInfo.getParentId() != 0) {
                LambdaUpdateWrapper updateWrapper = Wrappers.<LanguagePointsInfo>lambdaUpdate()
                        .set(LanguagePointsInfo::getHasChildren, true)
                        .eq(LanguagePointsInfo::getId, languagePointsInfo.getParentId());
                super.update(updateWrapper);
            }
        }
        return flag;
    }

    public ResultCode deleteById(Integer id) {
        LanguagePointsInfo languagePointsInfo = super.getById(id);
        if (languagePointsInfo.getHasChildren()) {
            return new ResultCode(ResultCode.FAIL, "存在子节点,无法删除");
        }
        super.removeById(id);
        return new ResultCode(ResultCode.SUCCESS, "删除成功");
    }
}
