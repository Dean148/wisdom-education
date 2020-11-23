package com.education.model.response;

import com.education.model.dto.StudentExamInfoDto;
import com.education.model.entity.ExamInfo;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 15:43
 */
public class ExamQuestionResponse {

    private List<ExamQuestionItemResponse> examQuestionItemResponseList;
    private StudentExamInfoDto studentExamInfoDto;

    public void setStudentExamInfoDto(StudentExamInfoDto studentExamInfoDto) {
        this.studentExamInfoDto = studentExamInfoDto;
    }

    public StudentExamInfoDto getStudentExamInfoDto() {
        return studentExamInfoDto;
    }

    public void setExamQuestionItemResponseList(List<ExamQuestionItemResponse> examQuestionItemResponseList) {
        this.examQuestionItemResponseList = examQuestionItemResponseList;
    }

    public List<ExamQuestionItemResponse> getExamQuestionItemResponseList() {
        return examQuestionItemResponseList;
    }
}
