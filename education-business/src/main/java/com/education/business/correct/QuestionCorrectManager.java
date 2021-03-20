package com.education.business.correct;

import com.education.common.constants.EnumConstants;

/**
 * 试题批改管理器
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/20 20:55
 */
public class QuestionCorrectManager {

    private static final QuestionCorrectManager questionCorrectManager = new QuestionCorrectManager();

    public static QuestionCorrectManager build(Integer correctType) {
        return questionCorrectManager;
    }

    public QuestionCorrect createQuestionCorrect(Integer correctType) {
        if (correctType == EnumConstants.CorrectType.SYSTEM.getValue()) {
            return null;
        }
        return null;
    }
}
