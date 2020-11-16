package com.education.core.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.common.base.BaseController;
import com.education.common.model.PageInfo;
import com.education.common.utils.Result;
import com.education.model.entity.SystemDictValue;
import com.education.model.request.PageParam;
import com.education.service.system.SystemDictValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典值管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/16 10:26
 */
@RestController
@RequestMapping("/api/dictValue")
public class DictValueController extends BaseController {

    @Autowired
    private SystemDictValueService systemDictValueService;

    /**
     * 字典类型值列表
     * @param dictId
     * @return
     */
    @GetMapping("selectByDictId")
    public Result<PageInfo<SystemDictValue>> selectByDictId(PageParam pageParam, Integer dictId) {
        QueryWrapper<SystemDictValue> queryWrapper = Wrappers.<SystemDictValue>query().eq("system_dict_id", dictId);
        return Result.success(systemDictValueService.selectPage(pageParam, queryWrapper));
    }

    /**
     * 添加或修改字典类型值
     * @param systemDictValue
     * @return
     */
    @PostMapping
    public Result saveOrUpdate(@RequestBody SystemDictValue systemDictValue) {
        systemDictValueService.saveOrUpdate(systemDictValue);
        return Result.success();
    }

    /**
     * 删除字典类型值
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    public Result deleteById(@PathVariable Integer id) {
        systemDictValueService.removeById(id);
        return Result.success();
    }
}
