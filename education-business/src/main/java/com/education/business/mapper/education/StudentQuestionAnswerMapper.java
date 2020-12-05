package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.education.model.dto.QuestionInfoAnswer;
import com.education.model.entity.StudentQuestionAnswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 15:21
 */
public interface StudentQuestionAnswerMapper extends BaseMapper<StudentQuestionAnswer> {

    List<QuestionInfoAnswer> selectQuestionAnswerList(@Param("studentId") Integer studentId, @Param("testPaperInfoId") Integer testPaperInfoId);
 }
