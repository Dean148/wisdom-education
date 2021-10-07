package com.education.business.service.education;

import com.education.business.mapper.education.CourseSectionMapper;
import com.education.business.service.BaseService;
import com.education.common.constants.CacheKey;
import com.education.model.dto.CourseSectionDto;
import com.education.model.entity.CourseSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author zengjintao
 * @create_at 2021/10/6 10:01
 * @since version 1.0.3
 */
@Service
public class CourseSectionService extends BaseService<CourseSectionMapper, CourseSection> {

    @Autowired
    private CourseInfoService courseInfoService;

    @Override
    @Transactional
    public boolean saveOrUpdate(CourseSection courseSection) {
        boolean flag = super.saveOrUpdate(courseSection);
        if (courseSection.getId() == null) {
            courseInfoService.increaseSectionNumber(courseSection.getCourseId());
        }
        return flag;
    }

    @Cacheable(cacheNames = CacheKey.COURSE_SECTION + "#3600", key = "#courseId")
    public List<CourseSectionDto> selectListByCourseId(Integer courseId) {
        return baseMapper.selectListByCourseId(courseId);
    }
}
