package com.education.api.controller.student;

import com.education.business.service.education.ExamInfoService;
import com.education.common.base.BaseController;
import com.education.common.constants.CacheKey;
import com.education.common.utils.Result;
import com.education.model.dto.StudentExamInfoDto;
import com.education.model.request.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生端考试记录管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 16:53
 */
@RequestMapping("/student/exam")
@RestController("student-exam")
public class ExamInfoController extends BaseController {

    @Autowired
    private ExamInfoService examInfoService;


    /**
     * 考试记录列表
     * @param pageParam
     * @param studentExamInfoDto
     * @return
     */
    @GetMapping
    public Result selectExamInfoList(PageParam pageParam, StudentExamInfoDto studentExamInfoDto) {
        return Result.success(examInfoService.selectStudentExamInfoList(pageParam, studentExamInfoDto));
    }


    /**
     * 获取考试试题及答案
     * @param examInfoId
     * @return
     */
    @GetMapping("selectExamQuestionAnswer/{id}")
    public Result selectExamQuestionAnswer(@PathVariable("id") Integer examInfoId) {
        Integer studentId = examInfoService.getStudentInfo().getId();
        return Result.success(examInfoService.selectExamQuestionAnswer(studentId, examInfoId));
    }

    /**
     * 获取考试结果
     * @param examInfoId
     * @return
     */
    @GetMapping("selectExamInfo/{id}")
    @Cacheable(cacheNames = CacheKey.EXAM_CACHE, key = "#examInfoId")
    public Result<StudentExamInfoDto> selectExamInfo(@PathVariable("id") Integer examInfoId) {
        return Result.success(examInfoService.getExamInfoById(examInfoId));
    }
}
