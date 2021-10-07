package com.education.business.service.education;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.CourseInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.constants.EnumConstants;
import com.education.common.exception.BusinessException;
import com.education.common.model.PageInfo;
import com.education.common.utils.ResultCode;
import com.education.model.dto.CourseInfoDto;
import com.education.model.entity.CourseInfo;
import com.education.model.request.PageParam;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 18:44
 */
@Service
public class CourseInfoService extends BaseService<CourseInfoMapper, CourseInfo> {

    public PageInfo<CourseInfoDto> selectPageList(PageParam pageParam, CourseInfo courseInfo) {
        Page<CourseInfoDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectPageList(page, courseInfo));
    }

    /**
     * 添加或修改课程
     * @param courseInfo
     */
    public void saveOrUpdateCourse(CourseInfo courseInfo) {
        Integer courseId = courseInfo.getId();
        if (courseId != null) {
            CourseInfo course = super.getById(courseId);
            if (course.getStatus() != EnumConstants.CourseStatus.DRAUGHT.getValue()) {
                throw new BusinessException(new ResultCode(ResultCode.FAIL, "非草稿状态课程无法修改"));
            }
        }
        super.saveOrUpdate(courseInfo);
    }

    /**
     * 根据id 删除课程
     * @param courseId
     */
    public void deleteById(Integer courseId) {
        CourseInfo course = super.getById(courseId);
        if (course.getStatus() != EnumConstants.CourseStatus.DRAUGHT.getValue()) {
            throw new BusinessException(new ResultCode(ResultCode.FAIL, "非草稿状态课程无法删除"));
        }
        super.removeById(courseId);
    }

    public void increaseSectionNumber(Integer id) {
        baseMapper.increaseSectionNumber(id);
    }

    public void decreaseSectionNumber(Integer id) {
        baseMapper.decreaseSectionNumber(id);
    }

    public void increaseSectionNodeNumber(Integer id) {
        baseMapper.increaseSectionNodeNumber(id);
    }

    public void decreaseSectionNodeNumber(Integer id) {
        baseMapper.decreaseSectionNodeNumber(id);
    }
}
