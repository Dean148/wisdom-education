package com.education.business.message;

import com.education.model.entity.ExamInfo;
import com.education.model.entity.StudentQuestionAnswer;
import com.education.model.entity.StudentWrongBook;
import java.util.List;

/**
 * @Auther: zjt
 * @Date: 2021/4/2 15:49
 * @Version:2.1.0
 */
public class ExamMessage extends QueueMessage {

    /**
     * 错题本列表
     */
    private List<StudentWrongBook> studentWrongBookList;

    /**
     * 答题记录列表
     */
    private List<StudentQuestionAnswer> studentQuestionAnswerList;

    /**
     * 考试记录
     */
    private ExamInfo examInfo;


    public List<StudentWrongBook> getStudentWrongBookList() {
        return studentWrongBookList;
    }

    public void setStudentWrongBookList(List<StudentWrongBook> studentWrongBookList) {
        this.studentWrongBookList = studentWrongBookList;
    }

    public List<StudentQuestionAnswer> getStudentQuestionAnswerList() {
        return studentQuestionAnswerList;
    }

    public void setStudentQuestionAnswerList(List<StudentQuestionAnswer> studentQuestionAnswerList) {
        this.studentQuestionAnswerList = studentQuestionAnswerList;
    }

    public void setExamInfo(ExamInfo examInfo) {
        this.examInfo = examInfo;
    }

    public ExamInfo getExamInfo() {
        return examInfo;
    }
}
