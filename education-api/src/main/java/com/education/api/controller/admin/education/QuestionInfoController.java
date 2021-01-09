package com.education.api.controller.admin.education;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import cn.afterturn.easypoi.util.PoiValidationUtil;
import com.education.business.service.education.QuestionInfoService;
import com.education.common.annotation.Param;
import com.education.common.annotation.ParamsValidate;
import com.education.common.base.BaseController;
import com.education.common.constants.EnumConstants;
import com.education.common.model.QuestionInfoImport;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.Result;
import com.education.common.utils.ResultCode;
import com.education.model.dto.QuestionInfoDto;
import com.education.model.entity.QuestionInfo;
import com.education.model.request.PageParam;
import com.education.model.request.QuestionInfoQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    private final Logger logger = LoggerFactory.getLogger(QuestionInfoController.class);

    /**
     * 试题列表
     * @param pageParam
     * @param questionInfoQuery
     * @return
     */
    @GetMapping
    @RequiresPermissions("system:question:list")
    public Result list(PageParam pageParam, QuestionInfoQuery questionInfoQuery) {
        return Result.success(questionInfoService.selectPageList(pageParam, questionInfoQuery));
    }

    /**
     * 添加或修改试题
     * @param questionInfoDto
     * @return
     */
    @PostMapping("saveOrUpdate")
    @RequiresPermissions(value = {"system:question:save", "system:question:update"}, logical = Logical.OR)
    @ParamsValidate(params = {
        @Param(name = "schoolType", message = "请选择所属阶段"),
        @Param(name = "gradeInfoId", message = "请选择所属年级"),
        @Param(name = "subjectId", message = "请选择所属科目"),
        @Param(name = "questionType", message = "请选择试题类型"),
        @Param(name = "content", message = "请输入试题内容"),
        @Param(name = "answer", message = "请输入试题答案")
    })
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
    @RequiresPermissions("system:question:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        return Result.success(questionInfoService.deleteById(id));
    }

    /**
     * 导入试题
     * @param gradeInfoId
     * @param subjectId
     * @param file
     * @return
     */
    @PostMapping("importQuestion")
    @ParamsValidate(params = {
        @Param(name = "schoolType", message = "请选择导入所属阶段"),
        @Param(name = "gradeInfoId", message = "请选择导入所属年级"),
        @Param(name = "subjectId", message = "请选择导入所属科目")
    })
    public Result importQuestion(
                                 @RequestParam Integer schoolType,
                                 @RequestParam Integer gradeInfoId,
                                 @RequestParam Integer subjectId,
                                 @RequestParam MultipartFile file) {
        String contentType = file.getContentType();
        if (!excelTypes.contains(contentType) && textTypes.contains(contentType)) {
            return Result.fail(ResultCode.FAIL, "只能导入excel或者txt类型文件");
        }
        try {
            ImportParams importParams = new ImportParams();
            importParams.setNeedVerfiy(true); // 设置需要校验

            importParams.setVerifyHandler(new IExcelVerifyHandler<QuestionInfo>() {
                @Override
                public ExcelVerifyHandlerResult verifyHandler(QuestionInfo questionInfo) {
                    ExcelVerifyHandlerResult verifyHandlerResult = new ExcelVerifyHandlerResult();

                    String errorMsg = PoiValidationUtil.validation(questionInfo, importParams.getVerfiyGroup());
                    if (StringUtils.isNotEmpty(errorMsg)) {
                        verifyHandlerResult.setMsg(errorMsg);
                        return verifyHandlerResult;
                    }

                    // 校验试题类型是否合法
                    String questionTypeName = questionInfo.getQuestionTypeName();
                    boolean flag = false;
                    for (EnumConstants.QuestionType value : EnumConstants.QuestionType.values()) {
                        if (value.equals(questionTypeName)) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    }
                    if (!flag) {
                        verifyHandlerResult.setSuccess(false);
                        verifyHandlerResult.setMsg("试题类型不正确");
                    }
                    verifyHandlerResult.setSuccess(true);
                    return verifyHandlerResult;
                }
            });
            ExcelImportResult<QuestionInfo> result = ExcelImportUtil.importExcelMore(file.getInputStream(),
                    QuestionInfo.class, importParams);
            if (ObjectUtils.isEmpty(result)) {
                return Result.fail(ResultCode.FAIL, "导入excel模板内容为空,请添加数据之后再导入");
            }
            // 存在校验失败数据
            if (result.isVerfiyFail() && result.getFailList().size() > 1) {
                List<QuestionInfo> failQuestionInfoList = result.getFailList();
                StringBuilder errorMsg = new StringBuilder("表格第");
                for (int i = 0; i < failQuestionInfoList.size() - 1; i++ ) {
                    int rowNumber = failQuestionInfoList.get(i).getRowNum() + 1;
                    if (i == failQuestionInfoList.size() -1) {
                        errorMsg.append(rowNumber);
                    } else {
                        errorMsg.append(rowNumber + ",");
                    }
                }
                return Result.fail(ResultCode.FAIL, errorMsg.toString() + "行数据错误，" +
                        "请根据表格错误提示进行修改后再导入");
            }
            questionInfoService.importQuestion(schoolType, gradeInfoId, subjectId, result.getList());
            return Result.success(ResultCode.SUCCESS, result.getList().size() + "道试题导入成功");
        } catch (Exception e) {
            logger.error("数据导入失败", e);
        }
        return Result.fail(ResultCode.FAIL, "数据导入失败");
    }
}
