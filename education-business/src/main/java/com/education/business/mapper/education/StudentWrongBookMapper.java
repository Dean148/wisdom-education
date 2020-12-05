package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.dto.QuestionInfoAnswer;
import com.education.model.entity.StudentWrongBook;
import com.education.model.request.WrongBookQuery;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 14:26
 */
public interface StudentWrongBookMapper extends BaseMapper<StudentWrongBook> {

    Page<QuestionInfoAnswer> selectPageList(Page<QuestionInfoAnswer> page, WrongBookQuery wrongBookQuery);
}
