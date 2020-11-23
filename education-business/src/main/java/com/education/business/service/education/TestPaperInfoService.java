package com.education.business.service.education;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.TestPaperInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.model.PageInfo;
import com.education.model.dto.TestPaperInfoDto;
import com.education.model.dto.TestPaperQuestionDto;
import com.education.model.entity.QuestionInfo;
import com.education.model.entity.TestPaperInfo;
import com.education.model.request.PageParam;
import com.education.model.request.TestPaperQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/20 21:22
 */
@Service
public class TestPaperInfoService extends BaseService<TestPaperInfoMapper, TestPaperInfo> {

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


}
