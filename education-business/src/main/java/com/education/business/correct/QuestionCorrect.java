package com.education.business.correct;

import cn.hutool.core.collection.CollUtil;
import com.education.business.service.education.StudentQuestionAnswerService;
import com.education.business.service.education.StudentWrongBookService;
import com.education.business.task.TaskManager;
import com.education.business.task.param.WebSocketMessageParam;
import com.education.common.component.SpringBeanManager;
import com.education.common.constants.EnumConstants;
import com.education.common.constants.LocalQueueConstants;
import com.education.common.enums.SocketMessageTypeEnum;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.StudentQuestionAnswer;
import com.education.model.entity.StudentWrongBook;
import com.education.model.request.QuestionAnswer;
import com.education.model.request.StudentQuestionRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/17 20:44
 */
public abstract class QuestionCorrect {

    private StudentQuestionRequest studentQuestionRequest;
    protected ExamInfo examInfo;
    private Integer studentId;

    protected int correctQuestionNumber = 0; // 批改试题数量
    private int rightQuestionNumber = 0; // 答对题数
    private int errorQuestionNumber = 0; // 答错题数
    protected int subjectiveQuestionNumber = 0; // 主观题数量
    protected int objectiveQuestionNumber = 0; // 客观题数量
    private int questionNumber; // 试题总数

    private final List<StudentWrongBook> studentWrongBookList = new ArrayList<>(); // 存储学员考试错题

    protected final List<QuestionAnswer> questionAnswerList;

    // 存储批改的学员答题记录
    protected final List<StudentQuestionAnswer> studentQuestionAnswerList = new ArrayList<>();

    // 存储客观题答题记录
    private final List<StudentQuestionAnswer> objectiveQuestionAnswerList = new ArrayList<>();

    protected Map<Integer, String> questionAnswerInfo; // 存储试题答案信息 key 为试题id value 为试题答案

    public QuestionCorrect(StudentQuestionRequest studentQuestionRequest, ExamInfo examInfo, Map<Integer, String> questionAnswerInfo) {
        this.studentQuestionRequest = studentQuestionRequest;
        this.questionNumber = studentQuestionRequest.getQuestionAnswerList().size();
        this.studentId = studentQuestionRequest.getStudentId();
        this.examInfo = examInfo;
        this.questionAnswerList = studentQuestionRequest.getQuestionAnswerList();
        this.questionAnswerInfo = questionAnswerInfo;
    }


    public int getRightQuestionNumber() {
        return rightQuestionNumber;
    }

    public void setRightQuestionNumber(int rightQuestionNumber) {
        this.rightQuestionNumber = rightQuestionNumber;
    }

    public int getErrorQuestionNumber() {
        return errorQuestionNumber;
    }

    public void setErrorQuestionNumber(int errorQuestionNumber) {
        this.errorQuestionNumber = errorQuestionNumber;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public static boolean isObjectiveQuestion(int questionType) {
        if (questionType == EnumConstants.QuestionType.SINGLE_QUESTION.getValue()
                || questionType == EnumConstants.QuestionType.MULTIPLE_QUESTION.getValue()
                || questionType == EnumConstants.QuestionType.JUDGMENT_QUESTION.getValue()) {
            return true;
        }
        return false;
    }

    public void addRightNumber() {
        this.rightQuestionNumber++;
    }

    public void addErrorNumber() {
        this.errorQuestionNumber++;
    }

    public List<StudentWrongBook> getStudentWrongBookList() {
        return studentWrongBookList;
    }

    protected void newStudentWrongBook(QuestionAnswer questionAnswer) {
        StudentWrongBook studentWrongBook = new StudentWrongBook(this.studentId,
                questionAnswer.getQuestionInfoId(),
                questionAnswer.getQuestionMark());
        studentWrongBook.setStudentAnswer(questionAnswer.getStudentAnswer());
        studentWrongBook.setStudentId(getStudentId());
        studentWrongBook.setCorrectStatus(EnumConstants.CorrectStatus.ERROR.getValue());
        this.addErrorNumber(); // 错题数加1
        this.studentWrongBookList.add(studentWrongBook);
    }

    protected StudentQuestionAnswer createStudentQuestionAnswer(QuestionAnswer questionAnswer) {
        StudentQuestionAnswer studentQuestionAnswer = new StudentQuestionAnswer();
        studentQuestionAnswer.setQuestionInfoId(questionAnswer.getQuestionInfoId());
        studentQuestionAnswer.setStudentId(this.studentId);
        studentQuestionAnswer.setComment(questionAnswer.getComment());
        studentQuestionAnswer.setQuestionPoints(questionAnswer.getQuestionMark());
        studentQuestionAnswer.setStudentAnswer(questionAnswer.getStudentAnswer());
        studentQuestionAnswer.setMark(questionAnswer.getQuestionMark());
        return studentQuestionAnswer;
    }

    public void saveStudentQuestionAnswer(Integer examInfoId, List<StudentQuestionAnswer> studentQuestionAnswerList, List<StudentWrongBook> studentWrongBookList) {
        if (CollUtil.isNotEmpty(studentQuestionAnswerList)) {
            studentQuestionAnswerList.forEach(item -> {
                item.setExamInfoId(examInfoId);
            });
            StudentQuestionAnswerService studentQuestionAnswerService = SpringBeanManager.getBean(StudentQuestionAnswerService.class);
            studentQuestionAnswerService.saveBatch(studentQuestionAnswerList);
        }
        if (CollUtil.isNotEmpty(studentWrongBookList)) {
            StudentWrongBookService studentWrongBookService = SpringBeanManager.getBean(StudentWrongBookService.class);
            studentWrongBookService.saveBatch(studentWrongBookList);
        }
    }

    public List<StudentQuestionAnswer> getStudentQuestionAnswerList() {
        return studentQuestionAnswerList;
    }

    protected void sendStudentMessage() {
        WebSocketMessageParam taskParam = new WebSocketMessageParam(LocalQueueConstants.SYSTEM_SOCKET_MESSAGE);
        taskParam.setSocketMessageTypeEnum(SocketMessageTypeEnum.EXAM_CORRECT);
        taskParam.setStudentId(studentId);
        TaskManager taskManager = SpringBeanManager.getBean(TaskManager.class);
        taskParam.setTestPaperId(examInfo.getTestPaperInfoId());
        taskManager.pushTask(taskParam);
    }

    public List<StudentQuestionAnswer> getObjectiveQuestionAnswerList() {
        return objectiveQuestionAnswerList;
    }

    public void addObjectiveQuestionAnswerList(StudentQuestionAnswer studentQuestionAnswer) {
        this.objectiveQuestionAnswerList.add(studentQuestionAnswer);
    }


    protected long getExamTime() {
        return studentQuestionRequest.getExamTime();
    }

    protected Integer getStudentId() {
        return studentQuestionRequest.getStudentId();
    }

    protected Integer getTestPaperInfoId() {
        return studentQuestionRequest.getTestPaperInfoId();
    }
    /**
     * 批改试题
     */
    public abstract void correctStudentQuestion();

    /**
     * 获取考试记录
     * @return
     */
    public abstract ExamInfo getExamInfo();
}
