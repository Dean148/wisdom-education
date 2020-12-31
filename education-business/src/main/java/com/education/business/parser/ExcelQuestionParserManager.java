package com.education.business.parser;

import com.education.common.constants.EnumConstants;

/**
 * 试题解析管理器
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/31 9:52
 */
public class ExcelQuestionParserManager {

    private ExcelQuestionParser excelQuestionParser;

    private static final ExcelQuestionParserManager me = new ExcelQuestionParserManager();

    public static ExcelQuestionParserManager build() {
        return me;
    }

    public void setExcelQuestionParser(ExcelQuestionParser excelQuestionParser) {
        this.excelQuestionParser = excelQuestionParser;
    }

    public ExcelQuestionParser getExcelQuestionParser() {
        return excelQuestionParser;
    }

    private ExcelQuestionParserManager() {

    }

    public ExcelQuestionParser createExcelQuestionParser(Integer questionType) {
        if (this.getExcelQuestionParser() != null) {
            return getExcelQuestionParser();
        }

        if (questionType == EnumConstants.QuestionType.SINGLE_QUESTION.getValue()
           || questionType == EnumConstants.QuestionType.MULTIPLE_QUESTION.getValue()) {
            return new SelectQuestionParser();
        } else if (questionType == EnumConstants.QuestionType.JUDGMENT_QUESTION.getValue()) {
            return new JudgmentQuestionParser();
        } else if (questionType == EnumConstants.QuestionType.FILL_QUESTION.getValue()) {
            return new ExerciseSubjectiveQuestionParser();
        }
        return new DefaultQuestionParser();
    }
}
