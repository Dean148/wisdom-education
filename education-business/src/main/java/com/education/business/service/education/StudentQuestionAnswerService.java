package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.education.StudentQuestionAnswerMapper;
import com.education.business.service.BaseService;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import com.education.model.dto.QuestionInfoAnswer;
import com.education.model.entity.StudentQuestionAnswer;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 15:21
 */
@Service
public class StudentQuestionAnswerService extends BaseService<StudentQuestionAnswerMapper, StudentQuestionAnswer> {

    /**
     * 获取试卷试题及学员试题答案
     * @return
     */
    public List<QuestionInfoAnswer> getQuestionAnswerByTestPaperInfoId(Integer studentId, Integer testPaperInfoId) {
        return baseMapper.selectQuestionAnswerList(studentId, testPaperInfoId);
    }

    /**
     * 删除学员试卷答题记录
     * @param studentId
     * @param testPaperInfoId
     * @return
     */
    public boolean deleteByTestPaperInfoId(Integer studentId, Integer testPaperInfoId) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(StudentQuestionAnswer.class)
                .eq(StudentQuestionAnswer::getStudentId, studentId)
                .eq(StudentQuestionAnswer::getTestPaperInfoId, testPaperInfoId);
        return super.remove(queryWrapper);
    }

    public StudentQuestionAnswer selectByQuestionInfoId(Integer questionInfoId) {
        LambdaQueryWrapper queryWrapper = Wrappers.<StudentQuestionAnswer>lambdaQuery()
                .eq(StudentQuestionAnswer::getQuestionInfoId, questionInfoId)
                .last(" limit 1");
        return super.getOne(queryWrapper);
    }

}
