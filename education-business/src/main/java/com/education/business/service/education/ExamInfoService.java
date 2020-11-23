package com.education.business.service.education;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.ExamInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.constants.EnumConstants;
import com.education.common.model.PageInfo;
import com.education.common.utils.DateUtils;
import com.education.common.utils.ObjectUtils;
import com.education.model.dto.ExamQuestionAnswer;
import com.education.model.dto.StudentExamInfoDto;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.StudentQuestionAnswer;
import com.education.model.request.PageParam;
import com.education.model.request.QuestionAnswer;
import com.education.model.request.StudentQuestionRequest;
import com.education.model.response.ExamQuestionItemResponse;
import com.education.model.response.ExamQuestionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 16:12
 */
@Service
public class ExamInfoService extends BaseService<ExamInfoMapper, ExamInfo> {

    @Autowired
    private StudentQuestionAnswerService studentQuestionAnswerService;

    public PageInfo<StudentExamInfoDto> selectStudentExamInfoList(PageParam pageParam, StudentExamInfoDto studentExamInfoDto) {
        Page<StudentExamInfoDto> page = new Page(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectStudentExamList(page, studentExamInfoDto));
    }

    @Transactional
    public void commitTestPaperInfoQuestion(StudentQuestionRequest studentQuestionRequest) {
        Integer testPaperInfoId = studentQuestionRequest.getTestPaperInfoId();
        Date now = new Date();
        List<StudentQuestionAnswer> studentQuestionAnswerList = new ArrayList<>();
        Integer studentId = getStudentInfo().getId();
        int systemMark = 0;
        int objectiveQuestionNumber = 0; // 客观题数量
        int rightNumber = 0;
        int errorNumber = 0;
        int questionNumber = studentQuestionRequest.getQuestionAnswerList().size();
        for (QuestionAnswer item : studentQuestionRequest.getQuestionAnswerList()) {
            StudentQuestionAnswer studentQuestionAnswer = new StudentQuestionAnswer();
            studentQuestionAnswer.setQuestionInfoId(item.getQuestionInfoId());
            studentQuestionAnswer.setStudentId(studentId);
            studentQuestionAnswer.setQuestionPoints(item.getQuestionMark());
            String studentAnswer = item.getStudentAnswer();
            // 验证试题是否为客观题
            if (isObjectiveQuestion(item.getQuestionType())) {
                String questionAnswer = item.getAnswer();
                if (questionAnswer.equals(studentAnswer)) {
                    studentQuestionAnswer.setMark(item.getQuestionMark());
                    systemMark += item.getQuestionMark();
                    rightNumber++;
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.RIGHT.getValue());
                } else {
                    errorNumber++;
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.ERROR.getValue());
                }
                objectiveQuestionNumber++;
            } else {
                studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.CORRECT_RUNNING.getValue());
            }
            studentQuestionAnswer.setStudentAnswer(studentAnswer);
            studentQuestionAnswer.setTestPaperInfoId(testPaperInfoId);
            studentQuestionAnswer.setCreateDate(now);
            studentQuestionAnswerList.add(studentQuestionAnswer);
        }

        studentQuestionAnswerService.saveBatch(studentQuestionAnswerList);

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
            examInfo.setRightNumber(rightNumber);
            examInfo.setErrorNumber(errorNumber);
        }
        super.save(examInfo);
    }

    public ExamQuestionResponse selectExamQuestionAnswer(Integer examInfoId) {
        StudentExamInfoDto studentExamInfoDto = baseMapper.selectById(examInfoId);
        List<ExamQuestionAnswer> examQuestionAnswerList = studentQuestionAnswerService
                .getQuestionAnswerByTestPaperInfoId(studentExamInfoDto.getTestPaperInfoId());
        EnumConstants.QuestionType questionType[] = EnumConstants.QuestionType.values();
        ExamQuestionResponse examQuestionResponse = new ExamQuestionResponse();
        List<ExamQuestionItemResponse> list = new ArrayList<>();
        for (EnumConstants.QuestionType item : questionType) {
            int value = item.getValue();
            List<ExamQuestionAnswer> questionList = examQuestionAnswerList.stream()
                    .filter(examQuestionAnswer -> value == examQuestionAnswer.getQuestionType().intValue())
                    .collect(Collectors.toList());
            if (ObjectUtils.isNotEmpty(questionList)) {
                ExamQuestionItemResponse examQuestionItemResponse = new ExamQuestionItemResponse();
                examQuestionItemResponse.setQuestionTypeName(item.getName());
                examQuestionItemResponse.setExamQuestionAnswerList(questionList);
                list.add(examQuestionItemResponse);
            }
        }
        examQuestionResponse.setExamQuestionItemResponseList(list);
        examQuestionResponse.setStudentExamInfoDto(studentExamInfoDto);
        return examQuestionResponse;
    }
}
