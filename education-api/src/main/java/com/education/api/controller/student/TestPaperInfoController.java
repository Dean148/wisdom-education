package com.education.api.controller.student;

import com.education.business.service.education.StudentQuestionAnswerService;
import com.education.business.service.education.TestPaperInfoService;
import com.education.business.service.education.TestPaperQuestionInfoService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.dto.TestPaperInfoDto;
import com.education.model.request.PageParam;
import com.education.model.request.StudentQuestionRequest;
import com.education.model.request.TestPaperQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学生端试卷管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 10:26
 */
@RestController("student-testPaperInfo")
@RequestMapping("/student/testPaperInfo")
public class TestPaperInfoController extends BaseController {

    @Autowired
    private TestPaperInfoService testPaperInfoService;
    @Autowired
    private StudentQuestionAnswerService studentQuestionAnswerService;

    /**
     * 试卷列表
     * @param pageParam
     * @param testPaperInfoDto
     * @return
     */
    @GetMapping("list")
    public Result list(PageParam pageParam, TestPaperInfoDto testPaperInfoDto) {
        return Result.success(testPaperInfoService.selectPageList(pageParam, testPaperInfoDto));
    }

    /**
     * 获取试卷试题
     * @param id
     * @return
     */
    @GetMapping("selectPaperQuestionById")
    public Result selectPaperQuestionById(Integer id) {
        PageParam pageParam = new PageParam(); // 设置不分页
        TestPaperQuestionRequest testPaperQuestionRequest = new TestPaperQuestionRequest();
        testPaperQuestionRequest.setTestPaperInfoId(id);
        return Result.success(testPaperInfoService.selectPaperQuestionList(pageParam, testPaperQuestionRequest)) ;
    }

    /**
     * 提交试卷试题
     * @param studentQuestionRequest
     * @return
     */
    @PostMapping("commitPaper")
    public Result commitPaper(StudentQuestionRequest studentQuestionRequest) {
        studentQuestionAnswerService.commitTestPaperInfoQuestion(studentQuestionRequest);
        return Result.success("提交成功");
    }
}
