package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.TestPaperInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.exception.BusinessException;
import com.education.common.model.PageInfo;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.dto.TestPaperInfoDto;
import com.education.model.dto.TestPaperQuestionDto;
import com.education.model.entity.TestPaperInfo;
import com.education.model.entity.TestPaperQuestionInfo;
import com.education.model.request.PageParam;
import com.education.model.request.TestPaperQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/20 21:22
 */
@Service
public class TestPaperInfoService extends BaseService<TestPaperInfoMapper, TestPaperInfo> {

    @Autowired
    private TestPaperQuestionInfoService testPaperQuestionInfoService;

    /**
     * 试卷分页列表
     * @param pageParam
     * @param testPaperInfo
     * @return
     */
    public PageInfo<TestPaperInfoDto> selectPageList(PageParam pageParam, TestPaperInfo testPaperInfo) {
        Page<TestPaperInfoDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectPageList(page, testPaperInfo));
    }

    /**
     * 试卷试题列表
     * @param pageParam
     * @param testPaperQuestionRequest
     * @return
     */
    public PageInfo<TestPaperQuestionDto> selectPaperQuestionList(PageParam pageParam, TestPaperQuestionRequest testPaperQuestionRequest) {
        Page<TestPaperQuestionDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectPaperQuestionList(page, testPaperQuestionRequest));
    }

    @Transactional
    public void updatePaperQuestionMarkOrSort(TestPaperQuestionDto testPaperQuestionDto) {
        // 更新试卷总分
        if (ObjectUtils.isNotEmpty(testPaperQuestionDto.getUpdateType()) &&
                testPaperQuestionDto.getUpdateType().intValue() == ResultCode.SUCCESS) {
            TestPaperInfo testPaperInfo = this.getById(testPaperQuestionDto.getTestPaperInfoId());
            int testPaperInfoMark = testPaperInfo.getMark();
            TestPaperQuestionInfo testPaperQuestionInfo = testPaperQuestionInfoService.getById(testPaperQuestionDto.getId());
            if (testPaperQuestionInfo.getMark() == 0) {
                testPaperInfo.setMark(testPaperQuestionDto.getMark() + testPaperInfoMark);
            } else {
                testPaperInfoMark -= testPaperQuestionInfo.getMark();
                testPaperInfoMark += testPaperQuestionDto.getMark();
                testPaperInfo.setMark(testPaperInfoMark);
            }
            this.updateById(testPaperInfo);
        }

        testPaperQuestionDto.setUpdateDate(new Date());
        LambdaUpdateWrapper updateWrapper = Wrappers.lambdaUpdate(TestPaperQuestionInfo.class)
                .eq(TestPaperQuestionInfo::getQuestionInfoId, testPaperQuestionDto.getQuestionInfoId())
                .eq(TestPaperQuestionInfo::getTestPaperInfoId, testPaperQuestionDto.getTestPaperInfoId())
                .set(TestPaperQuestionInfo::getMark, testPaperQuestionDto.getMark())
                .set(TestPaperQuestionInfo::getSort, testPaperQuestionDto.getSort());
        testPaperQuestionInfoService.update(updateWrapper);
    }

    @Override
    public boolean saveOrUpdate(TestPaperInfo testPaperInfo) {
        if (testPaperInfo.getId() != null && testPaperInfo.getExamNumber() > 0) {
            throw new BusinessException(new ResultCode(ResultCode.FAIL, "试卷已被使用, 无法修改"));
        }
        return super.saveOrUpdate(testPaperInfo);
    }

    @Transactional
    public ResultCode deleteById(Integer id) {
        TestPaperInfo testPaperInfo = super.getById(id);
        if (testPaperInfo.getExamNumber() == 0) {
            super.removeById(id);
            // 删除试卷试题关联信息
            testPaperQuestionInfoService.deleteByTestPaperInfoId(id);
            return new ResultCode(ResultCode.SUCCESS, "删除成功");
        }
        return new ResultCode(ResultCode.FAIL, "试卷已被使用, 无法删除");
    }

    public ResultCode publishTestPaperInfo(Integer testPaperInfoId) {
        TestPaperInfo testPaperInfo = super.getById(testPaperInfoId);
        if (testPaperInfo.getPublishFlag()) {
            return new ResultCode(ResultCode.FAIL, "试卷已发布,请勿重复操作");
        }

        boolean flag = testPaperQuestionInfoService.hasTestPaperInfoQuestion(testPaperInfoId);
        if (!flag) {
            return new ResultCode(ResultCode.FAIL, "改试卷暂未关联试题,请关联试题之后在发布");
        }

        testPaperInfo.setPublishFlag(true);
        testPaperInfo.setPublishTime(new Date());
        super.updateById(testPaperInfo);
        return new ResultCode(ResultCode.SUCCESS, "发布成功");
    }

    public Object cancelTestPaperInfo(Integer testPaperInfoId) {
        TestPaperInfo testPaperInfo = super.getById(testPaperInfoId);
        if (testPaperInfo.getExamNumber() > 0) {
            return new ResultCode(ResultCode.FAIL, "试卷已有学员作答, 无法撤回");
        }
        testPaperInfo.setPublishFlag(false);
        super.updateById(testPaperInfo);
        return new ResultCode(ResultCode.SUCCESS, "撤销成功");
    }

    @Transactional
    public void saveTestPaperInfoQuestion(List<TestPaperQuestionInfo> testPaperQuestionInfoList) {
        Date now = new Date();
        Integer testPaperInfoId = testPaperQuestionInfoList.get(0).getTestPaperInfoId();
        testPaperQuestionInfoList.forEach(item -> {
            item.setCreateDate(now);
        });
        // 更新试卷试题数量
        TestPaperInfo testPaperInfo = super.getById(testPaperInfoId);
        int questionNumber = testPaperInfo.getQuestionNumber() + testPaperQuestionInfoList.size();
        testPaperInfo.setQuestionNumber(questionNumber);
        super.updateById(testPaperInfo);
        // 保存试卷试题
        testPaperQuestionInfoService.saveBatch(testPaperQuestionInfoList);
    }

    @Transactional
    public ResultCode removePaperQuestion(TestPaperQuestionInfo testPaperQuestionInfo) {
        TestPaperInfo testPaperInfo = super.getById(testPaperQuestionInfo.getTestPaperInfoId());
        if (testPaperInfo.getExamNumber() > 0) {
            return new ResultCode(ResultCode.FAIL, "试卷已被使用,无法移除试题");
        }
        Integer testPaperInfoId = testPaperQuestionInfo.getTestPaperInfoId();
        testPaperQuestionInfoService.removeById(testPaperQuestionInfo.getId());
        // 更新试卷试题数量
        int questionNumber = testPaperInfo.getQuestionNumber() - 1;
        int mark = testPaperInfo.getMark() - testPaperQuestionInfo.getMark();
        testPaperInfo.setQuestionNumber(testPaperInfo.getQuestionNumber() - 1);
        LambdaUpdateWrapper updateWrapper = Wrappers.<TestPaperInfo>lambdaUpdate()
                .set(TestPaperInfo::getQuestionNumber, questionNumber)
                .set(TestPaperInfo::getMark, mark)
                .eq(TestPaperInfo::getId, testPaperInfoId);
        super.update(updateWrapper);
        return new ResultCode(ResultCode.SUCCESS, "删除成功");
    }

    /**
     * 更新考试人数
     * @param testPaperInfoId
     * @return
     */
    public boolean updateExamNumber(Integer testPaperInfoId) {
        // 更新考试参考人数
        TestPaperInfo testPaperInfo = super.getById(testPaperInfoId);
        int examNumber = testPaperInfo.getExamNumber() + 1;
        LambdaUpdateWrapper updateWrapper = new LambdaUpdateWrapper<>(TestPaperInfo.class)
                .set(TestPaperInfo::getExamNumber, examNumber)
                .eq(TestPaperInfo::getId, testPaperInfo.getId());
        return super.update(updateWrapper);
    }
}
