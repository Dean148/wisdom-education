package com.education.api.controller.admin.education;

import com.education.auth.annotation.Logical;
import com.education.auth.annotation.RequiresPermissions;
import com.education.business.service.education.CourseInfoService;
import com.education.common.annotation.Param;
import com.education.common.annotation.ParamsType;
import com.education.common.annotation.ParamsValidate;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.entity.CourseInfo;
import com.education.model.request.PageParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 课程管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 15:03
 */
@RestController
@RequestMapping("/system/course")
public class CourseInfoController extends BaseController {

    @Resource
    private CourseInfoService courseInfoService;


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
}
