package com.education.business.service.education;

import com.education.business.mapper.education.StudentWrongBookMapper;
import com.education.business.service.BaseService;
import com.education.model.entity.StudentWrongBook;
import org.springframework.stereotype.Service;

/**
 * 错题本管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 14:24
 */
@Service
public class StudentWrongBookService extends BaseService<StudentWrongBookMapper, StudentWrongBook> {

    public StudentWrongBook newStudentWrongBook(Integer studentId, Integer questionInfoId) {
        StudentWrongBook studentWrongBook = new StudentWrongBook();
        studentWrongBook.setStudentId(studentId);
        studentWrongBook.setQuestionInfoId(questionInfoId);
        return studentWrongBook;
    }
}
