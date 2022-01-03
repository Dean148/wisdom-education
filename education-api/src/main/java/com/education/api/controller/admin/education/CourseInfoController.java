package com.education.api.controller.admin.education;

import com.education.auth.annotation.Logical;
import com.education.auth.annotation.RequiresPermissions;
import com.education.business.service.education.CourseInfoService;
import com.education.business.service.education.CourseSectionNodeService;
import com.education.business.service.education.CourseSectionService;
import com.education.common.annotation.Param;
import com.education.common.annotation.ParamsType;
import com.education.common.annotation.ParamsValidate;
import com.education.common.base.BaseController;
import com.education.common.constants.CacheKey;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.entity.CourseInfo;
import com.education.model.entity.CourseSection;
import com.education.model.entity.CourseSectionNode;
import com.education.model.request.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 课程管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 15:03
 */
@RestController
@RequestMapping("/system/course")
public class CourseInfoController extends BaseController {

    @Autowired
    private CourseInfoService courseInfoService;
    @Autowired
    private CourseSectionService courseSectionService;
    @Autowired
    private CourseSectionNodeService courseSectionNodeService;

    /**
     * 课程列表
     * @param pageParam
     * @param courseInfo
     * @return
     */
    @GetMapping
    @RequiresPermissions("system:course:list")
    public Result list(PageParam pageParam, CourseInfo courseInfo) {
        return Result.success(courseInfoService.selectPageList(pageParam, courseInfo));
    }

    /**
     * 添加或修改课程
     * @param courseInfo
     * @return
     */
    @PostMapping("saveOrUpdate")
    @ParamsValidate(params = {
        @Param(name = "name", message = "请输入课程名称"),
        @Param(name = "headImg", message = "请上传课程封面"),
        @Param(name = "schoolType", message = "请选择课程阶段"),
        @Param(name = "gradeInfoId", message = "请选择年级"),
        @Param(name = "subjectId", message = "请选择所属科目")
    }, paramsType = ParamsType.JSON_DATA)
    @RequiresPermissions(value = {"system:course:save", "system:course:update"}, logical = Logical.OR)
    public Result saveOrUpdate(@RequestBody CourseInfo courseInfo) {
        courseInfoService.saveOrUpdateCourse(courseInfo);
        return Result.success();
    }

    /**
     * 删除课程
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @RequiresPermissions("system:course:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        courseInfoService.deleteById(id);
        return Result.success();
    }

    /**
     * 添加章节
     * @param courseSection
     * @return
     */
    @PostMapping("/section")
    @CacheEvict(cacheNames = CacheKey.COURSE_SECTION, key = "#courseSection.courseId", allEntries = true)
    public Result saveOrUpdateCourseSection(@RequestBody @Validated CourseSection courseSection) {
        return Result.success(courseSectionService.saveOrUpdate(courseSection));
    }

    /**
     * 删除章
     * @param courseId
     * @param sectionId
     * @return
     */
    @DeleteMapping("/section/{courseId}/{sectionId}")
    public Result deleteCourseSection(@PathVariable Integer courseId, @PathVariable Integer sectionId) {
        courseSectionService.delete(courseId, sectionId);
        return Result.success(ResultCode.SUCCESS, "删除成功");
    }

    /**
     * 添加或修改章节课时
     * @param courseSectionNode
     * @return
     */
    @PostMapping("/section/node")
    public Result saveOrUpdateCourseSectionNode(@RequestBody @Validated CourseSectionNode courseSectionNode) {
        return Result.success(courseSectionNodeService.saveOrUpdateCourseSectionNode(courseSectionNode));
    }

    /**
     * 删除章节课程
     * @param courseId
     * @param sectionNodeId
     * @return
     */
    @DeleteMapping("/section/node/{courseId}/{sectionNodeId}")
    public Result deleteCourseSectionNode(@PathVariable Integer courseId, @PathVariable Integer sectionNodeId) {
        courseSectionNodeService.delete(courseId, sectionNodeId);
        return Result.success(ResultCode.SUCCESS, "删除成功");
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
