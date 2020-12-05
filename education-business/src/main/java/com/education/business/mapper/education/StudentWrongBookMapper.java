package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.dto.StudentWrongBookDto;
import com.education.model.entity.StudentWrongBook;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 14:26
 */
public interface StudentWrongBookMapper extends BaseMapper<StudentWrongBook> {

    Page<StudentWrongBookDto> selectPageList(Page<StudentWrongBookDto> page, StudentWrongBook studentWrongBook);
}
