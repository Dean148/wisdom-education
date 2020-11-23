package com.education.business.service.education;

import com.education.business.mapper.education.StudentQuestionAnswerMapper;
import com.education.business.service.BaseService;
import com.education.model.dto.ExamQuestionAnswer;
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
    public List<ExamQuestionAnswer> getQuestionAnswerByTestPaperInfoId(Integer testPaperInfoId) {
        Integer studentId = getStudentInfo().getId();
        return baseMapper.selectQuestionAnswerList(studentId, testPaperInfoId);
    }
}
