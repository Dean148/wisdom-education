package com.education.business.message;

import com.education.model.entity.ExamInfo;
import com.education.model.entity.StudentQuestionAnswer;
import com.education.model.entity.StudentWrongBook;
import java.io.Serializable;
import java.util.List;

/**
 * @Auther: 66
 * @Date: 2021/4/2 15:49
 * @Version:2.1.0
 */
public class ExamMessage implements Serializable {

    /**
     * 错题本列表
     */
    private List<StudentWrongBook> studentWrongBookList;

    /**
     * 答题记录列表
     */
    private List<StudentQuestionAnswer> studentQuestionAnswerList;

    /**
     * 消息唯一标识
     */
    private String messageId;

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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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
