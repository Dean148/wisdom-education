package com.education.business.service.education;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.CourseInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.model.PageInfo;
import com.education.model.dto.CourseInfoDto;
import com.education.model.entity.CourseInfo;
import com.education.model.request.PageParam;
import org.springframework.stereotype.Service;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 18:44
 */
@Service
public class CourseInfoService extends BaseService<CourseInfoMapper, CourseInfo> {

    public PageInfo<CourseInfoDto> selectPageList(PageParam pageParam, CourseInfo courseInfo) {
        Page<CourseInfoDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectPageList(page, courseInfo));
    }
}
