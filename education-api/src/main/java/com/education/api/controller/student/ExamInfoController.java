package com.education.api.controller.student;

import com.education.business.service.education.ExamInfoService;
import com.education.business.service.education.ExamMonitorService;
import com.education.business.session.UserSessionContext;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.dto.ExamMonitor;
import com.education.model.dto.StudentExamInfoDto;
import com.education.model.request.PageParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 学生端考试记录管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 16:53
 */
@RequestMapping("/student/exam")
@RestController("student-exam")
public class ExamInfoController extends BaseController {

    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private ExamMonitorService examMonitorService;

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
        Integer studentId = UserSessionContext.getStudentId();
        return Result.success(examInfoService.selectExamQuestionAnswer(studentId, examInfoId));
    }

    /**
     * 获取考试结果
     * @param examInfoId
     * @return
     */
    @GetMapping("selectExamInfo/{id}")
   // 暂时注释 @Cacheable(cacheNames = CacheKey.EXAM_CACHE, key = "#examInfoId")
    public Result<StudentExamInfoDto> selectExamInfo(@PathVariable("id") Integer examInfoId) {
        return Result.success(examInfoService.getExamInfoById(examInfoId));
    }

    /**
     * 添加监控中心接口
     * @param examMonitor
     * @return
     */
    @PostMapping("addToExamExamMonitor")
    public Result addToExamExamMonitor(@RequestBody ExamMonitor examMonitor) {
        examMonitorService.addStudentToExamMonitor(examMonitor);
        return Result.success();
    }
}
