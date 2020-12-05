package com.education.model.response;

import com.education.model.dto.StudentWrongBookDto;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/4 19:45
 */
public class WrongQuestionGroupItemResponse extends QuestionGroupItemResponse {

    private List<StudentWrongBookDto> studentWrongBookDtoList;

    public List<StudentWrongBookDto> getStudentWrongBookDtoList() {
        return studentWrongBookDtoList;
    }

    public void setStudentWrongBookDtoList(List<StudentWrongBookDto> studentWrongBookDtoList) {
        this.studentWrongBookDtoList = studentWrongBookDtoList;
    }
}
