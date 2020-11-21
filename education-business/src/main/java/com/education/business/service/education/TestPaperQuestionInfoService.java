package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.TestPaperQuestionInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.dto.TestPaperQuestionDto;
import com.education.model.entity.TestPaperInfo;
import com.education.model.entity.TestPaperQuestionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 13:26
 */
@Service
public class TestPaperQuestionInfoService extends BaseService<TestPaperQuestionInfoMapper, TestPaperQuestionInfo> {

    @Autowired
    private TestPaperInfoService testPaperInfoService;

    @Transactional
    public void updatePaperQuestionMarkOrSort(TestPaperQuestionDto testPaperQuestionDto) {
        testPaperQuestionDto.setUpdateDate(new Date());
        LambdaUpdateWrapper updateWrapper = Wrappers.<TestPaperQuestionInfo>lambdaUpdate()
                .eq(TestPaperQuestionInfo::getQuestionInfoId, testPaperQuestionDto.getQuestionInfoId())
                .eq(TestPaperQuestionInfo::getTestPaperInfoId, testPaperQuestionDto.getTestPaperInfoId())
                .set(TestPaperQuestionInfo::getMark, testPaperQuestionDto.getMark())
                .set(TestPaperQuestionInfo::getSort, testPaperQuestionDto.getSort());
        super.update(updateWrapper);

        // 更新试卷总分
        if (ObjectUtils.isNotEmpty(testPaperQuestionDto.getUpdateType()) &&
                testPaperQuestionDto.getUpdateType().intValue() == ResultCode.SUCCESS) {
            TestPaperInfo testPaperInfo = testPaperInfoService.getById(testPaperQuestionDto.getTestPaperInfoId());
            testPaperInfo.setMark(testPaperQuestionDto.getMark() + testPaperInfo.getMark());
            testPaperInfoService.updateById(testPaperInfo);
        }
    }
}
