package com.education.common.upload;

import lombok.Data;

import java.util.Date;

/**
 * @author zengjintao
 * @create_at 2021/10/31 15:53
 * @since version 1.0.3
 */
@Data
public class UploadResult {

    private Date creationDate;
    private String fileUrl;

    public UploadResult(String fileUrl) {
        this.fileUrl = fileUrl;
        this.creationDate = new Date();
    }

    public UploadResult() {

    }
}
