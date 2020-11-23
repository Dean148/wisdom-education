package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.CourseInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.model.PageInfo;
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

    public PageInfo<CourseInfo> selectPageList(PageParam pageParam, CourseInfo courseInfo) {
        LambdaQueryWrapper queryWrapper = Wrappers.<CourseInfo>lambdaQuery(courseInfo);
             //   .eq(CourseInfo::isRecommendIndexFlag, courseInfo.isRecommendIndexFlag())
             //   .eq(CourseInfo::getGradeInfoId, getStudentInfo().getGradeInfoId());
        return selectPage(pageParam, queryWrapper);
    }
}
