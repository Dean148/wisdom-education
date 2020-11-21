package com.education.model.request;

import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 15:14
 */
public class TestPaperQuestionRequest {
    private Integer testPaperInfoId;
    private Integer questionType;
    private String content;
    private List<Integer> questionInfoIds;

    public Integer getTestPaperInfoId() {
        return testPaperInfoId;
    }

    public void setTestPaperInfoId(Integer testPaperInfoId) {
        this.testPaperInfoId = testPaperInfoId;
    }
}
