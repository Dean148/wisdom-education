package com.education.api.controller.admin.education;

import com.education.auth.annotation.Logical;
import com.education.auth.annotation.RequiresPermissions;
import com.education.business.service.education.SubjectInfoService;
import com.education.common.annotation.Param;
import com.education.common.annotation.ParamsType;
import com.education.common.annotation.ParamsValidate;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.entity.SubjectInfo;
import com.education.model.request.PageParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 科目管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 13:51
 */
@RestController
@RequestMapping("/system/subject")
public class SubjectInfoController extends BaseController {

    @Resource
    private SubjectInfoService subjectInfoService;

    /**
     * 科目列表
     * @param pageParam
     * @param subjectInfo
     * @return
     */
    @GetMapping
    @RequiresPermissions("system:subject:list")
    public Result list(PageParam pageParam, SubjectInfo subjectInfo) {
        return Result.success(subjectInfoService.selectPageList(pageParam, subjectInfo));
    }

    /**
     * 添加或修改科目
     * @param subjectInfo
     * @return
     */
    @PostMapping
    @ParamsValidate(params = {
        @Param(name = "name", message = "请输入科目名称"),
        @Param(name = "schoolType", message = "请选择科目阶段")
    }, paramsType = ParamsType.JSON_DATA)
    @RequiresPermissions(value = {"system:subject:save", "system:subject:update"}, logical = Logical.OR)
    public Result saveOrUpdate(@RequestBody SubjectInfo subjectInfo) {
        subjectInfoService.saveOrUpdate(subjectInfo);
        return Result.success();
    }

    @DeleteMapping("{id}")
    @RequiresPermissions("system:subject:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        subjectInfoService.deleteById(id);
        return Result.success(ResultCode.SUCCESS, "删除成功");
    }
}
