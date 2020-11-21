package com.education.model.dto;

import com.education.model.entity.TestPaperQuestionInfo;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 14:05
 */
public class TestPaperQuestionDto extends TestPaperQuestionInfo {

    private String content;
    private Integer updateType; // 更新字段

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
