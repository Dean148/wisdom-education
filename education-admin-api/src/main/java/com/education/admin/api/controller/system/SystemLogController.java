package com.education.admin.api.controller.system;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.common.base.BaseController;
import com.education.common.model.ModelBeanMap;
import com.education.common.model.PageInfo;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.entity.SystemLog;
import com.education.model.request.PageParam;
import com.education.service.system.SystemLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * 系统日志管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2019/4/2 19:26
 */
@RestController
@RequestMapping("/system/log")
public class SystemLogController extends BaseController {

    @Autowired
    private SystemLogService systemLogService;

    @GetMapping("list")
    @RequiresPermissions("system:log:list")
    public Result<PageInfo<SystemLog>> list(PageParam pageParam, SystemLog systemLog) {
        return Result.success(systemLogService.selectPage(pageParam, Wrappers.query(systemLog)));
    }

    @DeleteMapping("{id}")
    @RequiresPermissions("system:log:delete")
    public Result deleteById(@PathVariable Integer id) {
        systemLogService.removeById(id);
        return Result.success();
    }
}
