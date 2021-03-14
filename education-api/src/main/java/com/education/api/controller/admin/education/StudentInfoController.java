package com.education.api.controller.admin.education;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.education.business.service.education.StudentInfoService;
import com.education.common.base.BaseController;
import com.education.common.model.StudentInfoImport;
import com.education.common.utils.*;
import com.education.model.dto.StudentInfoDto;
import com.education.model.entity.StudentInfo;
import com.education.model.request.PageParam;
import com.jfinal.kit.FileKit;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 学员管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 18:15
 */
@RequestMapping("/system/student")
@RestController
public class StudentInfoController extends BaseController {

    @Autowired
    private StudentInfoService studentInfoService;

    /**
     * 学员分页列表
     * @param pageParam
     * @param studentInfo
     * @return
     */
    @GetMapping
    @RequiresPermissions("system:student:list")
    public Result selectPage(PageParam pageParam, StudentInfo studentInfo) {
        return Result.success(studentInfoService.selectPageList(pageParam, studentInfo));
    }

    /**
     * 添加或修改学员
     * @param studentInfoDto
     * @return
     */
    @PostMapping
    @RequiresPermissions(value = {"system:student:save", "system:student:update"}, logical = Logical.OR)
    public Result saveOrUpdate(@RequestBody StudentInfoDto studentInfoDto) {
        if (ObjectUtils.isEmpty(studentInfoDto.getId()) && !checkPassword(studentInfoDto)) {
            return Result.fail(ResultCode.FAIL, "密码与确认密码不一致");
        }
        studentInfoService.saveOrUpdate(studentInfoDto);
        return Result.success();
    }

    private boolean checkPassword(StudentInfoDto studentInfoDto) {
        String password = studentInfoDto.getPassword();
        String confirmPassword = studentInfoDto.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            return false;
        }
        return true;
    }

    /**
     * 重置学员密码
     * @param studentInfoDto
     * @return
     */
    @PostMapping("updatePassword")
    @RequiresPermissions("system:student:updatePassword")
    public Result updatePassword(@RequestBody StudentInfoDto studentInfoDto) {
        if (!checkPassword(studentInfoDto)) {
            return Result.fail(ResultCode.FAIL, "密码与确认密码不一致");
        }
        studentInfoService.updatePassword(studentInfoDto);
        return Result.success();
    }

    /**
     * 删除学员
     * @param id
     * @return
     */
    @DeleteMapping("{id}")
    @RequiresPermissions("system:student:deleteById")
    public Result deleteById(@PathVariable Integer id) {
        studentInfoService.removeById(id);
        return Result.success();
    }

    /**
     * excel 导入学员
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("importStudent")
    @RequiresPermissions("system:student:import")
    public Result importStudent(@RequestParam MultipartFile file) throws Exception {
        if (!excelTypes.contains(file.getContentType())) {
            return Result.fail(ResultCode.FAIL, "只能导入excel文件");
        }

        InputStream inputStream = file.getInputStream();
        ImportParams importParams = new ImportParams();
        importParams.setNeedVerfiy(true);
        importParams.setSaveUrl(baseUploadPath + "/image"); // 设置头像上传路径
        ExcelImportResult importResult = ExcelImportUtil.importExcelMore(inputStream,
                StudentInfoImport.class,
                importParams);

        // 存在数据校验失败
        if (importResult.isVerfiyFail() && ObjectUtils.isNotEmpty(importResult.getFailList())) {
            List<StudentInfoImport> failQuestionInfoList = importResult.getFailList();
            StringBuilder errorMsg = new StringBuilder("表格第");
            for (int i = 0; i < failQuestionInfoList.size(); i++ ) {
                int rowNumber = failQuestionInfoList.get(i).getRowNum() + 1;
                if (i == failQuestionInfoList.size() -1) {
                    errorMsg.append(rowNumber);
                } else {
                    errorMsg.append(rowNumber + ",");
                }
            }

            String targetPath = "/student/importExcelError/";
            String errorExcelUrl = targetPath
                    + ObjectUtils.generateUuId() + ".xlsx";
            String errorExcelPath = FileUtils.getUploadPath() + errorExcelUrl;
            File targetFile = new File(FileUtils.getUploadPath() + targetPath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            FileOutputStream fos = new FileOutputStream(errorExcelPath);
            importResult.getFailWorkbook().write(fos);

            return Result.success(ResultCode.EXCEL_VERFIY_FAIL, errorMsg.toString() + "行数据错误，" +
                    "请根据表格错误提示进行修改后再导入", errorExcelUrl);
        }

        int successCount = studentInfoService.importStudentFromExcel(importResult.getList());
        return Result.success(successCount);
    }
}
