package com.education.business.service.education;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.StudentWrongBookMapper;
import com.education.business.service.BaseService;
import com.education.common.constants.EnumConstants;
import com.education.common.model.PageInfo;
import com.education.common.utils.ObjectUtils;
import com.education.model.dto.StudentWrongBookDto;
import com.education.model.entity.StudentWrongBook;
import com.education.model.request.PageParam;
import com.education.model.response.QuestionGroupItemResponse;
import com.education.model.response.QuestionGroupResponse;
import com.education.model.response.WrongQuestionGroupItemResponse;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 错题本管理
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/25 14:24
 */
@Service
public class StudentWrongBookService extends BaseService<StudentWrongBookMapper, StudentWrongBook> {


    public QuestionGroupResponse selectPageList(PageParam pageParam, StudentWrongBook studentWrongBook) {
        Page<StudentWrongBookDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        PageInfo pageInfo = selectPage(baseMapper.selectPageList(page, studentWrongBook));
        List<StudentWrongBookDto> studentWrongBookDtoList = pageInfo.getDataList();
        QuestionGroupResponse questionGroupResponse = new QuestionGroupResponse();
        questionGroupResponse.setTotalQuestion(pageInfo.getTotal());
        if (ObjectUtils.isNotEmpty(studentWrongBookDtoList)) {
            List<QuestionGroupItemResponse> questionGroupItemResponseList = new ArrayList<>();
            // 对试题类型进行分组统计
            for (EnumConstants.QuestionType questionType : EnumConstants.QuestionType.values()) {
                WrongQuestionGroupItemResponse wrongQuestionGroupItemResponse = new WrongQuestionGroupItemResponse();
                int value = questionType.getValue();
                List<StudentWrongBookDto> questionList = studentWrongBookDtoList.stream()
                        .filter(examQuestionAnswer -> value == examQuestionAnswer.getQuestionType().intValue())
                        .collect(Collectors.toList());
                if (ObjectUtils.isNotEmpty(questionList)) {
                    questionList.forEach(question -> {
                        question.setQuestionTypeName(EnumConstants.QuestionType.getName(question.getQuestionType()));
                    });
                    wrongQuestionGroupItemResponse.setStudentWrongBookDtoList(questionList);
                    wrongQuestionGroupItemResponse.setQuestionTypeName(questionType.getName());
                    questionGroupItemResponseList.add(wrongQuestionGroupItemResponse);
                }
            }
            questionGroupResponse.setQuestionGroupItemResponseList(questionGroupItemResponseList);
        }
        return questionGroupResponse;
    }
}
