package com.education.api.controller.student;

import com.education.business.service.education.CourseValuateService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.entity.CourseValuate;
import com.education.model.request.PageParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 课程评价接口
 * @author zengjintao
 * @create_at 2021/10/17 9:27
 * @since version 1.0.3
 */
@RestController
@RequestMapping("/student/courseValuate")
public class CourseValuateController extends BaseController {

    @Resource
    private CourseValuateService courseValuateService;

    @GetMapping
    public Result listPage(PageParam pageParam, CourseValuate courseValuate) {
        return Result.success(courseValuateService.listPage(pageParam, courseValuate));
    }

    @PostMapping
    public Result saveOrUpdate(@RequestBody @Validated CourseValuate courseValuate) {
        courseValuateService.saveOrUpdate(courseValuate);
        return Result.success();
    }
}
