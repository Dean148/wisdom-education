package com.education.business.mapper.education;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.model.dto.StudentExamInfoDto;
import com.education.model.entity.ExamInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 16:11
 */
public interface ExamInfoMapper extends BaseMapper<ExamInfo> {

    /**
     * 学员考试记录列表
     * @param page
     * @param studentExamInfoDto
     * @return
     */
    Page<StudentExamInfoDto> selectStudentExamList(Page<StudentExamInfoDto> page, StudentExamInfoDto studentExamInfoDto);


    StudentExamInfoDto selectById(@Param("id") Integer id);
}
