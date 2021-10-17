package com.education.business.task;

import com.education.business.service.education.CourseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

/**
 * 课程评价提交异步更新课程评价数量、评价分数
 * @author zengjintao
 * @create_at 2021/10/17 10:35
 * @since version 1.0.3
 */
@Component
public class CourseValuateMessageListener implements TaskListener {

    @Autowired
    private CourseInfoService courseInfoService;

    @Override
    public void onMessage(TaskParam taskParam) {
        Integer courseId = taskParam.getInt("courseId");
        BigDecimal valuateMark = taskParam.getBigDecimal("valuateMark");
        courseInfoService.updateCommentNumberAndValuateMark(courseId, valuateMark);
    }
}
