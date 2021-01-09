package com.education.business.parser;

import com.education.common.utils.ObjectUtils;
import com.jfinal.json.Jackson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

   protected String[] parserToken(String text) {
        if (text.startsWith(openToken) && endToken.endsWith(endToken)) {
            String[] answerArray = ObjectUtils.spilt(text);
            for (int i = 0; i < answerArray.length; i++) {
                String item = answerArray[i];
                int length = item.length();
                if (i > 0) {
                    answerArray[i] = item.substring(openToken.length() + 1, length - endToken.length());
                } else {
                    answerArray[i] = item.substring(openToken.length(), length - endToken.length());
                }
            }
            return answerArray;
        }
        return null;
    }

}
