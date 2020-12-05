package com.education.model.response;

import com.education.model.dto.StudentExamInfoDto;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 15:43
 */
public class QuestionGroupResponse {

    private List<QuestionGroupItemResponse> questionGroupItemResponseList;
    private StudentExamInfoDto studentExamInfoDto;
    private long totalQuestion;

    public long getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(long totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public void setStudentExamInfoDto(StudentExamInfoDto studentExamInfoDto) {
        this.studentExamInfoDto = studentExamInfoDto;
    }

    public StudentExamInfoDto getStudentExamInfoDto() {
        return studentExamInfoDto;
    }

    public List<QuestionGroupItemResponse> getQuestionGroupItemResponseList() {
        return questionGroupItemResponseList;
    }

    public void setQuestionGroupItemResponseList(List<QuestionGroupItemResponse> questionGroupItemResponseList) {
        this.questionGroupItemResponseList = questionGroupItemResponseList;
    }
}
