package com.education.business.parser;

import com.education.common.utils.ObjectUtils;

/**
 * excel 填空题解析器
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/31 9:44
 */
public class ExerciseSubjectiveQuestionParser extends DefaultQuestionParser {

    @Override
    public String parseAnswerText(String answer) {
        if (ObjectUtils.isNotEmpty(answer)) {
            String[] answerArray = super.parserToken(answer);
            return jackson.toJson(answerArray);
        }
        return null;
    }
}
