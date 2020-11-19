package com.education.core.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.business.service.education.GradeInfoService;
import com.education.common.base.BaseController;
import com.education.common.model.PageInfo;
import com.education.common.utils.Result;
import com.education.model.entity.GradeInfo;
import com.education.model.request.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 年级管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 12:42
 */
@RestController
@RequestMapping("/api/gradeInfo")
public class GradeInfoController extends BaseController {

    @Autowired
    private GradeInfoService gradeInfoService;

    /**
     * 年级列表
     * @param pageParam
     * @param gradeInfo
     * @return
     */
    @GetMapping
    public Result<PageInfo<GradeInfo>> list(PageParam pageParam, GradeInfo gradeInfo) {
        return Result.success(gradeInfoService.selectPage(pageParam, new QueryWrapper(gradeInfo)));
    }
}
