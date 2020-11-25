package com.education.api.controller.admin.education;

import com.education.business.service.education.TestPaperInfoService;
import com.education.business.service.education.TestPaperQuestionInfoService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.dto.TestPaperInfoDto;
import com.education.model.dto.TestPaperQuestionDto;
import com.education.model.entity.TestPaperInfo;
import com.education.model.entity.TestPaperQuestionInfo;
import com.education.model.request.PageParam;
import com.education.model.request.TestPaperQuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 试卷管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/20 21:21
 */
@RestController
@RequestMapping("/system/testPaperInfo")
public class TestPaperInfoController extends BaseController {

    @Autowired
    private TestPaperInfoService testPaperInfoService;
    @Autowired
    private TestPaperQuestionInfoService testPaperQuestionInfoService;

    /**
     * 试卷列表
     * @param pageParam
     * @param testPaperInfoDto
     * @return
     */
    @GetMapping
    public Result list(PageParam pageParam, TestPaperInfoDto testPaperInfoDto) {
        return Result.success(testPaperInfoService.selectPageList(pageParam, testPaperInfoDto));
    }

    /**
     * 添加或修改试卷
     * @param testPaperInfo
     * @return
     */
    @PostMapping
    public Result saveOrUpdate(@RequestBody TestPaperInfo testPaperInfo) {
        testPaperInfoService.saveOrUpdate(testPaperInfo);
        return Result.success();
    }

    /**
     * 获取试卷试题列表
     * @param testPaperQuestionRequest
     * @return
     */
    @GetMapping("getPaperQuestionList")
    public Result selectPaperQuestionList(PageParam pageParam, TestPaperQuestionRequest testPaperQuestionRequest) {
        return Result.success(testPaperInfoService.selectPaperQuestionList(pageParam, testPaperQuestionRequest));
    }

    /**
     * 修改试卷试题分数或者排序
     * @param testPaperQuestionDto
     * @return
     */
    @PostMapping("updatePaperQuestionMarkOrSort")
    public Result updatePaperQuestionMarkOrSort(@RequestBody TestPaperQuestionDto testPaperQuestionDto) {
        testPaperQuestionInfoService.updatePaperQuestionMarkOrSort(testPaperQuestionDto);
        return Result.success();
    }

    /**
     * 保存试卷试题
     * @param testPaperQuestionInfoList
     * @return
     */
    @PostMapping("saveTestPaperInfoQuestion")
    public Result saveTestPaperInfoQuestion(@RequestBody List<TestPaperQuestionInfo> testPaperQuestionInfoList) {
        Date now = new Date();
        testPaperQuestionInfoList.forEach(item -> {
            item.setCreateDate(now);
        });
        testPaperQuestionInfoService.saveBatch(testPaperQuestionInfoList);
        return Result.success();
    }
}
