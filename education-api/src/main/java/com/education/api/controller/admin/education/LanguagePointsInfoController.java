package com.education.api.controller.admin.education;

import com.education.business.service.education.LanguagePointsInfoService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.entity.LanguagePointsInfo;
import com.education.model.request.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 知识点管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/19 14:14
 */
@RestController
@RequestMapping("/system/languagePointsInfo")
public class LanguagePointsInfoController extends BaseController {

    @Autowired
    private LanguagePointsInfoService languagePointsInfoService;

    /**
     * 获取一级知识点列表
     * @param languagePointsInfo
     * @return
     */
    @GetMapping("selectFirstPoints")
    public Result selectFirstPoints(LanguagePointsInfo languagePointsInfo) {
        return Result.success(languagePointsInfoService.selectFirstPoints(languagePointsInfo));
    }

    /**
     * 根据parentId 查找子节点
     * @param parentId
     * @return
     */
    @GetMapping("selectByParentId")
    public Result selectByParentId(Integer parentId) {
        return Result.success(languagePointsInfoService.selectByParentId(parentId));
    }

    /**
     * 获取知识点科目列表
     * @param pageParam
     * @return
     */
    @GetMapping("selectSubjectList")
    public Result selectSubjectList(PageParam pageParam) {
        return null;
    }
}
