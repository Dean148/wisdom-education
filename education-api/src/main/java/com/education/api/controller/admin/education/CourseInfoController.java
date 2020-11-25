package com.education.api.controller.admin.education;

import com.education.business.service.education.CourseInfoService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.entity.CourseInfo;
import com.education.model.request.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * 课程列表
     * @param pageParam
     * @param courseInfo
     * @return
     */
    @GetMapping
    public Result list(PageParam pageParam, CourseInfo courseInfo) {
        return Result.success(courseInfoService.selectPageList(pageParam, courseInfo));
    }

    /**
     * 添加或修改课程
     * @param courseInfo
     * @return
     */
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody CourseInfo courseInfo) {
        courseInfoService.saveOrUpdate(courseInfo);
        return Result.success();
    }

    /**
     * 删除课程
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result saveOrUpdate(@PathVariable Integer id) {
        courseInfoService.removeById(id);
        return Result.success();
    }
}
