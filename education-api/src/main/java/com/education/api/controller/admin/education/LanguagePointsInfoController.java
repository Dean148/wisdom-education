package com.education.api.controller.admin.education;

import com.education.auth.annotation.Logical;
import com.education.auth.annotation.RequiresPermissions;
import com.education.business.service.education.LanguagePointsInfoService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.dto.LanguagePointsInfoDto;
import com.education.model.entity.LanguagePointsInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 知识点管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 14:14
 */
@RestController
@RequestMapping("/system/languagePointsInfo")
public class LanguagePointsInfoController extends BaseController {

    @Resource
    private LanguagePointsInfoService languagePointsInfoService;

    /**
     * 获取一级知识点列表
     * @param languagePointsInfo
     * @return
     */
    @GetMapping("selectList")
    @RequiresPermissions("system:languagePointsInfo:list")
    public Result<LanguagePointsInfo> selectFirstPoints(LanguagePointsInfo languagePointsInfo) {
        return Result.success(languagePointsInfoService.selectList(languagePointsInfo));
    }

    /**
     * 根据parentId 查找子节点
     * @param parentId
     * @return
     */
    @GetMapping("selectByParentId")
    public Result<LanguagePointsInfoDto> selectByParentId(Integer parentId) {
        LanguagePointsInfo languagePointsInfo = new LanguagePointsInfo();
        languagePointsInfo.setParentId(parentId);
        return Result.success(languagePointsInfoService.selectList(languagePointsInfo));
    }

    /**
     * 添加或修改知识点
     * @param languagePointsInfo
     * @return
     */
    @PostMapping("saveOrUpdate")
    @RequiresPermissions(value = {"system:languagePointsInfo:save", "system:languagePointsInfo:update"}, logical = Logical.OR)
    public Result saveOrUpdate(@RequestBody LanguagePointsInfo languagePointsInfo) {
        languagePointsInfoService.saveOrUpdate(languagePointsInfo);
        return Result.success();
    }

    /**
     * 删除知识点
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @RequiresPermissions("system:languagePointsInfo:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        return Result.success(languagePointsInfoService.deleteById(id));
    }

}
