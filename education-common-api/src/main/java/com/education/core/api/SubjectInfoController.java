package com.education.core.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.business.service.education.SubjectInfoService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.entity.SubjectInfo;
import com.education.model.request.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 科目管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 13:51
 */
@RestController
@RequestMapping("/api/subject")
public class SubjectInfoController extends BaseController {

    @Autowired
    private SubjectInfoService subjectInfoService;

    /**
     * 科目列表
     * @param pageParam
     * @param subjectInfo
     * @return
     */
    @GetMapping
    public Result list(PageParam pageParam, SubjectInfo subjectInfo) {
        return Result.success(subjectInfoService.selectPageList(pageParam, subjectInfo));
    }

    /**
     * 添加或修改科目
     * @param subjectInfo
     * @return
     */
    @PostMapping
    public Result saveOrUpdate(@RequestBody SubjectInfo subjectInfo) {
        subjectInfoService.saveOrUpdate(subjectInfo);
        return Result.success();
    }
}
