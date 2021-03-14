package com.education.model.dto;

import com.education.common.model.ExcelResult;
import com.education.model.entity.QuestionInfo;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/14 16:12
 */
public class ExcelQuestionData {

    private ExcelResult excelResult;
    private List<QuestionInfo> questionInfoList;


    public ExcelResult getExcelResult() {
        return excelResult;
    }

    /**
     * 获取校验失败数据
     * @return
     */
    public List<QuestionInfo> getFailQuestionList() {
        return this.excelResult.getExcelImportResult().getFailList();
    }

    public void setExcelResult(ExcelResult excelResult) {
        this.excelResult = excelResult;
    }

    public List<QuestionInfo> getQuestionInfoList() {
        return questionInfoList;
    }

    public void setQuestionInfoList(List<QuestionInfo> questionInfoList) {
        this.questionInfoList = questionInfoList;
    }
}
