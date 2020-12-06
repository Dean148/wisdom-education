package com.education.api.controller.admin.education;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.service.education.GradeInfoService;
import com.education.common.base.BaseController;
import com.education.common.model.PageInfo;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.Result;
import com.education.model.entity.GradeInfo;
import com.education.model.request.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 年级管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 12:42
 */
@RestController
@RequestMapping("/system/gradeInfo")
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
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(GradeInfo.class)
                .like(ObjectUtils.isNotEmpty(gradeInfo.getName()), GradeInfo::getName, gradeInfo.getName())
                .eq(ObjectUtils.isNotEmpty(gradeInfo.getSchoolType()),
                        GradeInfo::getSchoolType, gradeInfo.getSchoolType());
        return Result.success(gradeInfoService.selectPage(pageParam, queryWrapper));
    }

    /**
     * 保存或添加年级
     * @param gradeInfo
     * @return
     */
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody GradeInfo gradeInfo) {
        gradeInfoService.saveOrUpdate(gradeInfo);
        return Result.success();
    }

    /**
     * 删除年级
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable Integer id) {
        gradeInfoService.removeById(id);
        return Result.success();
    }
}
