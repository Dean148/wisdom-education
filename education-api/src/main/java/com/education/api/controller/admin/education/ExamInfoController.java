package com.education.api.controller.admin.education;

import com.education.business.service.education.ExamInfoService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.dto.StudentExamInfoDto;
import com.education.model.entity.ExamInfo;
import com.education.model.entity.TestPaperInfo;
import com.education.model.request.PageParam;
import com.education.model.request.StudentQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 考试管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 19:55
 */
@RestController("/system")
@RequestMapping("/system/exam")
public class ExamInfoController extends BaseController {

    @Autowired
    private ExamInfoService examInfoService;

    @GetMapping
    public Result list(PageParam pageParam, StudentExamInfoDto studentExamInfoDto) {
        return Result.success(examInfoService.selectStudentExamInfoList(pageParam, studentExamInfoDto));
    }

    /**
     * 获取学员试卷试题列表
     * @param studentId
     * @param examInfoId
     * @return
     */
    @GetMapping("getExamQuestionList/{studentId}/{examInfoId}")
    public Result getExamQuestionList(@PathVariable Integer studentId, @PathVariable Integer examInfoId) {
        return Result.success(examInfoService.selectExamQuestionAnswer(studentId, examInfoId));
    }

    /**
     * 批改学员试卷
     * @param studentQuestionRequest
     * @return
     */
    @PostMapping("correctExamQuestion")
    public Result correctExamQuestion(@RequestBody StudentQuestionRequest studentQuestionRequest) {
        examInfoService.correctStudentExam(studentQuestionRequest);
        return Result.success(ResultCode.SUCCESS, "批改成功");
    }

    /**
     * 考试统计
     * @param testPaperInfo
     * @return
     */
    @GetMapping("countExam")
    public Result countExam(PageParam pageParam, TestPaperInfo testPaperInfo) {
        return Result.success(examInfoService.countExam(pageParam, testPaperInfo));
    }
}
