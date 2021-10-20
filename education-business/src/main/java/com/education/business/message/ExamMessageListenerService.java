package com.education.business.message;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.service.education.ExamInfoService;
import com.education.business.service.education.StudentQuestionAnswerService;
import com.education.business.service.education.StudentWrongBookService;
import com.education.business.service.education.TestPaperInfoService;
import com.education.business.service.system.SystemMessageLogService;
import com.education.common.constants.Constants;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.MessageLog;
import com.education.model.entity.StudentQuestionAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/4/5 19:21
 */
@Service
public class ExamMessageListenerService {

    @Autowired
    private StudentWrongBookService studentWrongBookService;
    @Autowired
    private SystemMessageLogService systemMessageLogService;
    @Autowired
    private StudentQuestionAnswerService studentQuestionAnswerService;
    @Autowired
    private ExamInfoService examInfoService;
    @Autowired
    private TestPaperInfoService testPaperInfoService;

    /**
     * 注意  @Transactional 不能和 @RabbitListener 一起使用
     * 因此将业务方法单独抽出来
     * @param examMessage
     * @param messageId
     * @throws Exception
     */
    @Transactional
    public void doExamCommitMessageBiz(ExamMessage examMessage, String messageId) throws Exception {
        // 保存考试记录
        ExamInfo examInfo = examMessage.getExamInfo();
        examInfo.setCreateDate(new Date());
        examInfoService.save(examInfo);
        // 批量保存学员错题
        if (examMessage.getStudentWrongBookList() != null) {
            studentWrongBookService.saveBatch(examMessage.getStudentWrongBookList());
        }
        // 保存学员答题记录
        List<StudentQuestionAnswer> studentQuestionAnswerList = examMessage.getStudentQuestionAnswerList();
        studentQuestionAnswerList.stream().forEach(item -> item.setExamInfoId(examInfo.getId()));

        studentQuestionAnswerService.saveBatch(studentQuestionAnswerList);

        LambdaUpdateWrapper updateWrapper = Wrappers.lambdaUpdate(MessageLog.class)
                .set(MessageLog::getStatus, Constants.CONSUME_SUCCESS)
                .eq(MessageLog::getCorrelationDataId, messageId);
        systemMessageLogService.update(null, updateWrapper);
        // 更新试卷参考人数
        testPaperInfoService.updateExamNumber(examInfo.getTestPaperInfoId());
    }
}
