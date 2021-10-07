package com.education.api.controller.student;

import com.education.business.service.education.CourseInfoService;
import com.education.business.service.education.CourseSectionService;
import com.education.common.base.BaseController;
import com.education.common.constants.CacheKey;
import com.education.common.utils.Result;
import com.education.model.entity.CourseInfo;
import com.education.model.request.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 课程管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 18:42
 */
@RestController("student-courseInfo")
@RequestMapping("/student/courseInfo")
public class CourseInfoController extends BaseController {

    @Autowired
    private CourseInfoService courseInfoService;
    @Autowired
    private CourseSectionService courseSectionService;

    /**
     * 课程列表
     * @param pageParam
     * @param courseInfo
     * @return
     */
    @GetMapping
    public Result selectPageList(PageParam pageParam, CourseInfo courseInfo) {
        return Result.success(courseInfoService.selectPageList(pageParam, courseInfo));
    }

    /**
     * 课程章节列表
     * @param courseId
     * @return
     */
    @GetMapping("/{courseId}/section")
    public Result selectSectionByCourseId(@PathVariable Integer courseId) {
        return Result.success(courseSectionService.selectListByCourseId(courseId));
    }
}
