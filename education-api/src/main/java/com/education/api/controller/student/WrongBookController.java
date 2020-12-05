package com.education.api.controller.student;

import com.education.business.service.education.StudentWrongBookService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.entity.StudentWrongBook;
import com.education.model.request.PageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 错题本管理接口
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/4 17:56
 */
@RestController
@RequestMapping("/student/wrongBook")
public class WrongBookController extends BaseController {

    @Autowired
    private StudentWrongBookService studentWrongBookService;

    /**
     * 错题本列表
     * @param pageParam
     * @param studentWrongBook
     * @return
     */
    @GetMapping
    public Result list(PageParam pageParam, StudentWrongBook studentWrongBook) {
        return Result.success(studentWrongBookService.selectPageList(pageParam, studentWrongBook));
    }
}
