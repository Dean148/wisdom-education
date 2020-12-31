package com.education.business.parser;

import com.education.common.utils.ObjectUtils;
import com.jfinal.json.Jackson;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/31 9:48
 */
public abstract class AbstractExcelQuestionParser implements ExcelQuestionParser {

    protected final Jackson jackson = new Jackson();

    protected String openToken = "${";
    protected String endToken = "}";

    public void setOpenToken(String openToken) {
        this.openToken = openToken;
    }

    public String getOpenToken() {
        return openToken;
    }

    protected String parserToken(String text) {
        if (text.startsWith(openToken) && endToken.endsWith(endToken)) {
            String[] answerArray = ObjectUtils.spilt(text);
            return jackson.toJson(answerArray);
        }
        return parseAnswerText(text);
    }
}
