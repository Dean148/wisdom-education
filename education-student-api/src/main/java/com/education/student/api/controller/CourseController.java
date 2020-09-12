package com.education.student.api.controller;

import com.education.common.base.BaseController;
import com.education.common.model.ModelBeanMap;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.mapper.course.CourseInfoMapper;
import com.education.service.course.CourseInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 学生端课程管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/5/3 9:01
 */
@RequestMapping("/student/course")
@RestController
public class CourseController extends BaseController {

    @Autowired
    private CourseInfoService courseInfoService;

    /**
     * 获取科目课程列表
     * @param params
     * @return
     */
    @GetMapping("getCourseList")
    public Result getCourseList(@RequestParam Map params) {
        params.put("studentId", courseInfoService.getFrontUserInfo().get("student_id"));
        return courseInfoService.pagination(params, CourseInfoMapper.class,
                CourseInfoMapper.GET_RECOMMEND_COURSE_LIST);
    }

    /**
     * 获取课程试题
     * @param courseId
     * @return
     */
    @GetMapping("getCourseQuestion")
    public Result getCourseQuestion(Integer courseId) {
        return Result.success(courseInfoService.getCourseQuestion(courseId));
    }


    /**
     * 提交试题
     * @param modelBeanMap
     * @return
     */
    @PostMapping("commitQuestion")
    @ApiOperation("提交试题接口")
    public ResultCode commitQuestion(@RequestBody ModelBeanMap modelBeanMap) {
        List<ModelBeanMap> userInfoAnswerList = modelBeanMap.getModelBeanMapList("userInfoAnswerList");
        Integer courseId = modelBeanMap.getInt("courseId");
        return courseInfoService.commitQuestion(courseId, userInfoAnswerList);
    }


    /**
     * 课程收藏
     * @param courseBeanMap
     * @return
     */
    @PostMapping("collectCourse")
    public Result collectCourse(@RequestBody ModelBeanMap courseBeanMap) {
        return courseInfoService.collectCourse(courseBeanMap);
    }
}
