package com.education.business.service.education;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.ExamInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.constants.EnumConstants;
import com.education.common.model.PageInfo;
import com.education.common.utils.DateUtils;
import com.education.model.dto.QuestionInfoAnswer;
import com.education.model.dto.StudentExamInfoDto;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.StudentQuestionAnswer;
import com.education.model.entity.StudentWrongBook;
import com.education.model.entity.TestPaperInfo;
import com.education.model.request.PageParam;
import com.education.model.request.QuestionAnswer;
import com.education.model.request.StudentQuestionRequest;
import com.education.model.response.ExamInfoReport;
import com.education.model.response.ExamQuestionGroupResponse;
import com.education.model.response.QuestionGroupItemResponse;
import com.education.model.response.QuestionGroupResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 16:12
 */
@Service
public class ExamInfoService extends BaseService<ExamInfoMapper, ExamInfo> {

    @Autowired
    private StudentQuestionAnswerService studentQuestionAnswerService;
    @Autowired
    private StudentWrongBookService studentWrongBookService;
    @Autowired
    private QuestionInfoService questionInfoService;

    public PageInfo<StudentExamInfoDto> selectStudentExamInfoList(PageParam pageParam, StudentExamInfoDto studentExamInfoDto) {
        Page<StudentExamInfoDto> page = new Page(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectExamList(page, studentExamInfoDto));
    }

    @Transactional
    public void commitTestPaperInfoQuestion(StudentQuestionRequest studentQuestionRequest) {
        Integer studentId = getStudentInfo().getId();
        this.batchSaveStudentQuestionAnswer(studentQuestionRequest, studentId, new ExamInfo());
    }

    private void batchSaveStudentQuestionAnswer(StudentQuestionRequest studentQuestionRequest, Integer studentId, ExamInfo examInfo) {
        Integer testPaperInfoId = studentQuestionRequest.getTestPaperInfoId();
        Date now = new Date();
        List<StudentQuestionAnswer> studentQuestionAnswerList = new ArrayList<>();
        int systemMark = 0;
        int objectiveQuestionNumber = 0; // 客观题数量
        int subjectiveQuestionNumber = 0; // 主观题数量
        int rightNumber = 0;
        int errorNumber = 0;
        int teacherMark = 0;
        int questionNumber = studentQuestionRequest.getQuestionAnswerList().size();
        List<StudentWrongBook> studentWrongBookList = new ArrayList<>(); // 存储学员考试错题
        for (QuestionAnswer item : studentQuestionRequest.getQuestionAnswerList()) {
            StudentQuestionAnswer studentQuestionAnswer = new StudentQuestionAnswer();
            studentQuestionAnswer.setQuestionInfoId(item.getQuestionInfoId());
            studentQuestionAnswer.setStudentId(studentId);
            studentQuestionAnswer.setQuestionPoints(item.getQuestionMark());
            String studentAnswer = item.getStudentAnswer();
            // 验证试题是否为客观题subjective;
            if (isObjectiveQuestion(item.getQuestionType())) {
                String questionAnswer = item.getAnswer();
                if (questionAnswer.equals(studentAnswer)) {
                    studentQuestionAnswer.setMark(item.getQuestionMark());
                    systemMark += item.getQuestionMark();
                    rightNumber++;
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.RIGHT.getValue());
                } else {
                    studentWrongBookList.add(new StudentWrongBook(studentId, item.getQuestionInfoId(),
                            item.getQuestionMark()));
                    errorNumber++;
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.ERROR.getValue());
                }
                objectiveQuestionNumber++;
            } else {
                if (studentQuestionRequest.isTeacherCorrectFlag()) {
                    teacherMark += item.getStudentMark();
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.CORRECTED.getValue());
                    if (item.isErrorQuestionFlag()) {
                        studentWrongBookList.add(new StudentWrongBook(studentId, item.getQuestionInfoId(),
                                item.getQuestionMark()));
                    }
                } else {
                    studentQuestionAnswer.setCorrectStatus(EnumConstants.CorrectStatus.CORRECT_RUNNING.getValue());
                }
                subjectiveQuestionNumber++;
            }
            studentQuestionAnswer.setStudentAnswer(studentAnswer);
            studentQuestionAnswer.setTestPaperInfoId(studentQuestionRequest.getTestPaperInfoId());
            studentQuestionAnswer.setCreateDate(now);
            studentQuestionAnswerList.add(studentQuestionAnswer);
        }

        studentQuestionAnswerService.saveBatch(studentQuestionAnswerList);

        if (studentWrongBookList.size() > 0) {
            studentWrongBookService.saveBatch(studentWrongBookList); // 批量保存错题
        }

        if (!studentQuestionRequest.isTeacherCorrectFlag()) {
            // 保存考试记录表
            examInfo.setStudentId(studentId);
            examInfo.setQuestionNumber(questionNumber);
            examInfo.setTestPaperInfoId(testPaperInfoId);
            examInfo.setCreateDate(now);
            examInfo.setSystemMark(systemMark);
            examInfo.setSubjectiveQuestionNumber(subjectiveQuestionNumber);
            long examTime = studentQuestionRequest.getExamTime();
            examInfo.setExamTime(DateUtils.getDate(examTime));
            if (subjectiveQuestionNumber == 0) { // 如果全部为客观题的话，直接设置为已批改状态
                examInfo.setCorrectFlag(EnumConstants.Flag.YES.getValue());
                examInfo.setCorrectType(EnumConstants.CorrectType.SYSTEM.getValue());
                examInfo.setMark(systemMark);
            }
            examInfo.setRightNumber(rightNumber);
            examInfo.setErrorNumber(errorNumber);
            examInfo.setCreateDate(now);
            super.save(examInfo);
        } else {
            if (objectiveQuestionNumber == 0) {
                examInfo.setCorrectType(EnumConstants.CorrectType.TEACHER.getValue());
            } else {
                examInfo.setCorrectType(EnumConstants.CorrectType.SYSTEM_AND_TEACHER.getValue());
            }
            examInfo.setMark(systemMark + teacherMark);
            examInfo.setUpdateDate(now);
            examInfo.setCorrectFlag(EnumConstants.Flag.YES.getValue());
            examInfo.setAdminId(getAdminUserId());
            super.updateById(examInfo);
        }
    }


    public QuestionGroupResponse selectExamQuestionAnswer(Integer studentId, Integer examInfoId) {
        StudentExamInfoDto studentExamInfoDto = baseMapper.selectById(examInfoId);
        List<QuestionInfoAnswer> examQuestionAnswerList = studentQuestionAnswerService
                .getQuestionAnswerByTestPaperInfoId(studentId, studentExamInfoDto.getTestPaperInfoId());
        List<QuestionGroupItemResponse> list = questionInfoService.groupQuestion(examQuestionAnswerList);
        ExamQuestionGroupResponse examQuestionResponse = new ExamQuestionGroupResponse();
        examQuestionResponse.setQuestionGroupItemResponseList(list);
        examQuestionResponse.setTotalQuestion(list.size());
        examQuestionResponse.setStudentExamInfoDto(studentExamInfoDto);
        return examQuestionResponse;
    }

    /**
     * 批改学员试卷
     * @param studentQuestionRequest
     */
    @Transactional
    public void correctStudentExam(StudentQuestionRequest studentQuestionRequest) {
        ExamInfo examInfo = super.getById(studentQuestionRequest.getExamInfoId());
        Integer studentId = studentQuestionRequest.getStudentId();
        studentQuestionAnswerService.deleteByTestPaperInfoId(studentId, examInfo.getTestPaperInfoId());
        studentQuestionRequest.setTestPaperInfoId(examInfo.getTestPaperInfoId());
        this.batchSaveStudentQuestionAnswer(studentQuestionRequest, studentQuestionRequest.getStudentId(), examInfo);
    }

    public PageInfo<ExamInfoReport> selectExamReportList(PageParam pageParam, TestPaperInfo testPaperInfo) {
        Page<ExamInfoReport> page = new Page(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectExamReportList(page, testPaperInfo));
    }
}
