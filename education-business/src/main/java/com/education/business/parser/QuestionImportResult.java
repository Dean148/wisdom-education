package com.education.business.parser;

import com.education.model.dto.ExcelQuestionData;
import com.education.model.entity.QuestionInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/16 15:03
 */
public abstract class QuestionImportResult {

    private final InputStream inputStream;
    private MultipartFile file;

    private HttpServletResponse response;

    public InputStream getInputStream() {
        return inputStream;
    }

    public QuestionImportResult(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public QuestionImportResult(MultipartFile file) throws IOException {
        this.file = file;
        this.inputStream = file.getInputStream();
    }

    public QuestionImportResult(MultipartFile file, HttpServletResponse response) throws IOException {
        this.file = file;
        this.inputStream = file.getInputStream();
        this.response = response;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public abstract ExcelQuestionData readTemplate();
}
