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
    // 导入失败试题集合
    private List<QuestionInfo> failImportQuestionList;


    public ExcelResult getExcelResult() {
        return excelResult;
    }

    /**
     * 获取校验失败数据
     * @return
     */
    public List<QuestionInfo> getFailQuestionList() {
        if (excelResult != null) {
            this.failImportQuestionList = this.excelResult.getExcelImportResult().getFailList();
        }
        return this.failImportQuestionList;
    }

    public void setFailImportQuestionList(List<QuestionInfo> failImportQuestionList) {
        this.failImportQuestionList = failImportQuestionList;
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
