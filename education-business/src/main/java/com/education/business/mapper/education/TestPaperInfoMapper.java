package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.dto.TestPaperInfoDto;
import com.education.model.dto.TestPaperQuestionDto;
import com.education.model.entity.QuestionInfo;
import com.education.model.entity.TestPaperInfo;
import com.education.model.request.TestPaperQuestionRequest;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/20 21:27
 */
public interface TestPaperInfoMapper extends BaseMapper<TestPaperInfo> {

    /**
     * 试卷分列表
     * @param page
     * @param testPaperInfo
     * @return
     */
    Page<TestPaperInfoDto> selectPageList(Page<TestPaperInfoDto> page, TestPaperInfo testPaperInfo);

    /**
     * 试卷试题列表
     * @param page
     * @param questionInfo
     * @return
     */
    Page<TestPaperQuestionDto> selectPaperQuestionList(Page<TestPaperQuestionDto> page, TestPaperQuestionRequest testPaperQuestionRequest);
}
