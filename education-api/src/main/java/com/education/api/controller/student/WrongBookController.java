package com.education.api.controller.student;

import com.education.business.service.education.StudentWrongBookService;
import com.education.business.task.TaskManager;
import com.education.business.task.TaskParam;
import com.education.business.task.WebSocketMessageTask;
import com.education.common.base.BaseController;
import com.education.common.constants.EnumConstants;
import com.education.common.model.PageInfo;
import com.education.common.utils.Result;
import com.education.model.dto.QuestionInfoAnswer;
import com.education.model.request.PageParam;
import com.education.model.request.WrongBookQuery;
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

    @Autowired
    private TaskManager taskManager;


    /**
     * 错题本列表
     * @param pageParam
     * @param wrongBookQuery
     * @return
     */
    @GetMapping
    public Result<PageInfo<QuestionInfoAnswer>> list(PageParam pageParam, WrongBookQuery wrongBookQuery) {
        wrongBookQuery.setStudentId(studentWrongBookService.getStudentInfo().getId());


        TaskParam taskParam = new TaskParam(WebSocketMessageTask.class);
        taskParam.put("message_type", EnumConstants.MessageType.EXAM_CORRECT.getValue());
        taskParam.put("sessionId", "99462b64-9f1d-4c0e-86bf-4256540880ba");
        taskManager.pushTask(taskParam);
        return Result.success(studentWrongBookService.selectPageList(pageParam, wrongBookQuery));
    }
}
