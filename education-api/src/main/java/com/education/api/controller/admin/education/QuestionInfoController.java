package com.education.api.controller.admin.education;

import com.education.business.service.education.QuestionInfoService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.dto.QuestionInfoDto;
import com.education.model.request.PageParam;
import com.education.model.request.QuestionInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 试题管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 10:45
 */
@RestController
@RequestMapping("/system/question")
public class QuestionInfoController extends BaseController {

    @Autowired
    private QuestionInfoService questionInfoService;

    /**
     * 试题列表
     * @param pageParam
     * @param questionInfoQuery
     * @return
     */
    @GetMapping
    public Result list(PageParam pageParam, QuestionInfoQuery questionInfoQuery) {
        return Result.success(questionInfoService.selectPageList(pageParam, questionInfoQuery));
    }

    /**
     * 添加或修改试题
     * @param questionInfoDto
     * @return
     */
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody QuestionInfoDto questionInfoDto) {
        return Result.success(questionInfoService.saveOrUpdateQuestionInfo(questionInfoDto));
    }

    /**
     * 试题详情
     * @param id
     * @return
     */
    @GetMapping("selectById")
    public Result selectById(Integer id) {
        return Result.success(questionInfoService.selectById(id));
    }

    /**
     * 根据id 删除试题
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable Integer id) {
        return Result.success(questionInfoService.deleteById(id));
    }
}
