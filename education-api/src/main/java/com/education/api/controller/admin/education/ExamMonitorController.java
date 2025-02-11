package com.education.api.controller.admin.education;

import com.education.business.service.education.ExamMonitorService;
import com.education.common.base.BaseController;
import com.education.common.model.PageInfo;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.Result;
import com.education.model.dto.ExamMonitor;
import com.education.model.request.PageParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * 考试监控接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/29 15:17
 */
@RestController
@RequestMapping("/system/examMonitor")
public class ExamMonitorController extends BaseController {

    @Resource
    private ExamMonitorService examMonitorService;

    /**
     * 获取试卷正在考试学员
     * @param testPaperInfoId
     * @return
     */
    @GetMapping("/selectByTestPaperInfoId/{testPaperInfoId}")
    public Result<PageInfo<ExamMonitor>> selectByTestPaperInfoId(PageParam pageParam, @PathVariable Integer testPaperInfoId) {
        List<ExamMonitor> studentInfoList = examMonitorService.getExamMonitorByTestPaperInfoId(testPaperInfoId);
        return Result.success(ObjectUtils.selectPageList(pageParam.getPageNumber(), pageParam.getPageSize(), studentInfoList));
    }
}
