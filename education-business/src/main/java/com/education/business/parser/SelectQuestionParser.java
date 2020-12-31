package com.education.business.parser;

/**
 * 选择题解析器
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/31 9:51
 */
public class SelectQuestionParser extends AbstractExcelQuestionParser {

    @Override
    public String parseOptionText(String option) {
        return super.parserToken(option);
    }
}
