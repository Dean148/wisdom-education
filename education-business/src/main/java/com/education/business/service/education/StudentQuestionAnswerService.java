package com.education.business.service.education;

import com.education.business.mapper.education.StudentQuestionAnswerMapper;
import com.education.business.service.BaseService;
import com.education.common.constants.EnumConstants;
import com.education.common.utils.DateUtils;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.StudentQuestionAnswer;
import com.education.model.request.QuestionAnswer;
import com.education.model.request.StudentQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 15:21
 */
@Service
public class StudentQuestionAnswerService extends BaseService<StudentQuestionAnswerMapper, StudentQuestionAnswer> {

    @Autowired
    private ExamInfoService examInfoService;

    @Transactional
    public void commitTestPaperInfoQuestion(StudentQuestionRequest studentQuestionRequest) {
        Integer testPaperInfoId = studentQuestionRequest.getTestPaperInfoId();
        Date now = new Date();
        List<StudentQuestionAnswer> studentQuestionAnswerList = new ArrayList<>();
        Integer studentId = getStudentInfo().getId();
        int systemMark = 0;
        int objectiveQuestionNumber = 0; // 客观题数量
        int questionNumber = studentQuestionRequest.getQuestionAnswerList().size();
        for (QuestionAnswer item : studentQuestionRequest.getQuestionAnswerList()) {
            StudentQuestionAnswer studentQuestionAnswer = new StudentQuestionAnswer();
            studentQuestionAnswer.setQuestionInfoId(item.getQuestionInfoId());
            studentQuestionAnswer.setStudentId(studentId);
            studentQuestionAnswer.setQuestionPoints(item.getQuestionMark());
            if (isObjectiveQuestion(item.getQuestionType())) {
                String studentAnswer = item.getStudentAnswer();
                String questionAnswer = item.getAnswer();
                if (questionAnswer.equals(studentAnswer)) {
                    studentQuestionAnswer.setMark(item.getQuestionMark());
                    systemMark += item.getQuestionMark();
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.RIGHT.getValue());
                } else {
                    studentQuestionAnswer.setMark(0);
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.ERROR.getValue());
                }
                objectiveQuestionNumber++;
            }
            studentQuestionAnswer.setTestPaperInfoId(testPaperInfoId);
            studentQuestionAnswer.setCreateDate(now);
            studentQuestionAnswerList.add(studentQuestionAnswer);
        }

        super.saveBatch(studentQuestionAnswerList);

        // 保存考试记录表
        ExamInfo examInfo = new ExamInfo();
        examInfo.setStudentId(studentId);
        examInfo.setTestPaperInfoId(testPaperInfoId);
        examInfo.setCreateDate(now);
        examInfo.setSystemMark(systemMark);
        long examTime = studentQuestionRequest.getExamTime();
        examInfo.setExamTime(DateUtils.getDate(examTime));
        if (objectiveQuestionNumber == questionNumber) { // 如果全部为客观题的话，直接设置为已批改状态
            examInfo.setCorrectFlag(true);
            examInfo.setCorrectType(EnumConstants.CorrectType.SYSTEM.getValue());
        }
        examInfoService.save(examInfo);
    }
}
