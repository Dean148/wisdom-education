package com.education.api.controller.admin.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.auth.annotation.RequiresPermissions;
import com.education.business.service.system.SystemLogService;
import com.education.common.base.BaseController;
import com.education.common.model.PageInfo;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.Result;
import com.education.model.entity.SystemLog;
import com.education.model.request.PageParam;
import com.education.model.request.SystemLogQuery;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * 系统日志管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/2 19:26
 */
@RestController
@RequestMapping("/system/log")
public class SystemLogController extends BaseController {

    @Resource
    private SystemLogService systemLogService;

    /**
     * 日志列表
     * @param pageParam
     * @param systemLogQuery
     * @return
     */
    @GetMapping("list")
    @RequiresPermissions("system:log:list")
    public Result<PageInfo<SystemLog>> list(PageParam pageParam, SystemLogQuery systemLogQuery) {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(SystemLog.class)
                .gt(ObjectUtils.isNotEmpty(systemLogQuery.getStartTime()),
                        SystemLog::getCreateDate, systemLogQuery.getStartTime())
                .le(ObjectUtils.isNotEmpty(systemLogQuery.getEndTime()),
        SystemLog::getCreateDate, systemLogQuery.getEndTime())
                .orderByDesc(SystemLog::getId);
        return Result.success(systemLogService.selectPage(pageParam, queryWrapper));
    }

    @DeleteMapping("{id}")
    @RequiresPermissions("system:log:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        systemLogService.removeById(id);
        return Result.success();
    }

    /**
     * 清空所以操作日志
     * @return
     */
    @DeleteMapping
    public Result delete() {
        systemLogService.removeAll();
        return Result.success();
    }
}
