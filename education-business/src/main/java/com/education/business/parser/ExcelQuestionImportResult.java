package com.education.business.parser;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import cn.afterturn.easypoi.util.PoiValidationUtil;
import com.education.common.constants.EnumConstants;
import com.education.common.exception.BusinessException;
import com.education.common.utils.FileUtils;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.ResultCode;
import com.education.model.entity.QuestionInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/16 15:01
 */
public class ExcelQuestionImportResult extends QuestionImportResult {

    private final Logger logger = LoggerFactory.getLogger(ExcelQuestionImportResult.class);

    public ExcelQuestionImportResult(InputStream inputStream) {
        super(inputStream);
    }

    public ExcelQuestionImportResult(MultipartFile file) throws IOException {
        super(file);
    }

    @Override
    public List<QuestionInfo> readTemplate() {
        try {
            ImportParams importParams = new ImportParams();
            importParams.setNeedVerfiy(true); // 设置需要校验
            // 设置excel文件路径
            importParams.setSaveUrl(FileUtils.getUploadPath() + importParams.getSaveUrl());
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
            ExcelImportResult<QuestionInfo> result = ExcelImportUtil.importExcelMore(super.getInputStream(),
                    QuestionInfo.class, importParams);
            if (ObjectUtils.isEmpty(result) || ObjectUtils.isEmpty(result.getList())) {
                throw new BusinessException(new ResultCode(ResultCode.FAIL, "导入excel模板内容为空,请添加数据之后再导入"));
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
                throw new BusinessException(new ResultCode(ResultCode.FAIL,
                        errorMsg.toString() + "行数据错误，" +
                                "请根据表格错误提示进行修改后再导入"));
            }
            return result.getList();
        } catch (Exception e) {
            logger.error("模板数据解析异常", e);
        }
        return null;
    }
}
