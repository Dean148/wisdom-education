package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.dto.CourseValuateDto;
import com.education.model.entity.CourseValuate;

/**
 * @author zengjintao
 * @create_at 2021/10/17 9:32
 * @since version 1.0.3
 */
public interface CourseValuateMapper extends BaseMapper<CourseValuate> {

    /**
     * 课程评价列表
     * @param page
     * @return
     */
    Page<CourseValuateDto> listPage(Page<CourseValuateDto> page);
}
