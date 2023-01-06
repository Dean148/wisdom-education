package com.education.api.controller.student;

import com.education.business.service.education.ExamInfoService;
import com.education.business.service.education.ExamMonitorService;
import com.education.business.service.education.TestPaperInfoService;
import com.education.common.annotation.FormLimit;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.dto.ExamMonitor;
import com.education.model.dto.TestPaperInfoDto;
import com.education.model.request.PageParam;
import com.education.model.request.StudentQuestionRequest;
import com.education.model.request.TestPaperQuestionRequest;
import com.education.model.response.StudentExamRate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 学生端试卷管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 10:26
 */
@RestController("student-testPaperInfo")
@RequestMapping("/student/testPaperInfo")
public class TestPaperInfoController extends BaseController {

    @Resource
    private TestPaperInfoService testPaperInfoService;
    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private ExamMonitorService examMonitorService;

    /**
     * 试卷列表
     * @param pageParam
     * @param testPaperInfoDto
     * @return
     */
    @GetMapping("list")
    public Result list(PageParam pageParam, TestPaperInfoDto testPaperInfoDto) {
        testPaperInfoDto.setPublishFlag(true);
        return Result.success(testPaperInfoService.selectPageList(pageParam, testPaperInfoDto));
    }

    /**
     * 获取试卷试题
     * @param id
     * @return sync = true 目前貌似只针对本地缓存有效 （该方式主要用户高并发下的同步操作，从而减少对数据库的请求）
     */
    @GetMapping("selectPaperQuestionById")
    public Result selectPaperQuestionById(Integer id) {
        PageParam pageParam = new PageParam(); // 设置不分页
        TestPaperQuestionRequest testPaperQuestionRequest = new TestPaperQuestionRequest();
        testPaperQuestionRequest.setTestPaperInfoId(id);
        return Result.success(testPaperInfoService.selectPaperQuestionListByCache(pageParam, testPaperQuestionRequest));
    }

    /**
     * 提交试卷试题
     * @param studentQuestionRequest
     * @return
     */
    @PostMapping("commitPaper")
    @FormLimit(message = "请勿重复提交考试")
    public Result commitPaper(@RequestBody StudentQuestionRequest studentQuestionRequest) {
        return Result.success(ResultCode.SUCCESS,
                "提交成功",
                examInfoService.commitTestPaperInfoQuestion(studentQuestionRequest));
    }

    /**
     * 更新学员考试答题进度
     * @param studentExamRate
     * @return
     */
    @PostMapping("updateStudentExamRate")
    public Result updateStudentExamRate(@RequestBody StudentExamRate studentExamRate) {
        ExamMonitor examMonitor = examMonitorService.getExamMonitorStudent(studentExamRate.getTestPaperInfoId(),
                examInfoService.getStudentId());
        examMonitor.setAnswerQuestionCount(studentExamRate.getAnswerQuestionCount());
        examMonitor.setTestPaperInfoId(studentExamRate.getTestPaperInfoId());
        examMonitorService.addStudentToExamMonitor(examMonitor);
        return Result.success();
    }
}
