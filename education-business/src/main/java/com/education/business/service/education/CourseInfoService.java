package com.education.business.service.education;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.mapper.education.CourseInfoMapper;
import com.education.business.service.BaseService;
import com.education.common.constants.EnumConstants;
import com.education.common.constants.LockKey;
import com.education.common.exception.BusinessException;
import com.education.common.model.PageInfo;
import com.education.common.utils.ResultCode;
import com.education.model.dto.CourseInfoDto;
import com.education.model.entity.CourseInfo;
import com.education.model.request.PageParam;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/23 18:44
 */
@Service
public class CourseInfoService extends BaseService<CourseInfoMapper, CourseInfo> {

    @Resource
    private RedissonClient redissonClient;

    public PageInfo<CourseInfoDto> selectPageList(PageParam pageParam, CourseInfo courseInfo) {
        Page<CourseInfoDto> page = new Page<>(pageParam.getPageNumber(), pageParam.getPageSize());
        Page<CourseInfoDto> resultPage = baseMapper.selectPageList(page, courseInfo);
        List<CourseInfoDto> list = resultPage.getRecords();
        list.forEach(item -> {
            Integer studentId = item.getStudentId();
            if (studentId == null) {
                item.setCollectFlag(ResultCode.FAIL);
            } else {
                item.setCollectFlag(ResultCode.SUCCESS);
            }
        });
        return selectPage(resultPage);
    }

    /**
     * 添加或修改课程
     * @param courseInfo
     */
    public void saveOrUpdateCourse(CourseInfo courseInfo) {
        Integer courseId = courseInfo.getId();
        if (courseId != null) {
            CourseInfo course = super.getById(courseId);
            if (!EnumConstants.CourseStatus.DRAUGHT.getValue().equals(course.getStatus())) {
                if (!courseInfo.getSchoolType().equals(courseInfo.getSchoolType())) {
                    throw new BusinessException("已上架课程禁止修改所属阶段");
                }

                if (!courseInfo.getGradeInfoId().equals(courseInfo.getGradeInfoId())) {
                    throw new BusinessException("已上架课程禁止修改所属年级");
                }

                if (!courseInfo.getSubjectId().equals(courseInfo.getSubjectId())) {
                    throw new BusinessException("已上架课程禁止修改所属科目");
                }
            }
        }
        if (EnumConstants.CourseStatus.GROUNDING.getValue().equals(courseInfo.getStatus())) {
            courseInfo.setPushTime(new Date());
        }
        super.saveOrUpdate(courseInfo);
    }

    /**
     * 根据id 删除课程
     * @param courseId
     */
    @Transactional
    public void deleteById(Integer courseId) {
        CourseInfo course = super.getById(courseId);
        if (!EnumConstants.CourseStatus.DRAUGHT.getValue().equals(course.getStatus())) {
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

    /**
     * 评论数量加1
     * @param id
     */
    public void increaseCommentNumber(Integer id) {
        baseMapper.increaseCommentNumber(id);
    }

    @Transactional
    public void updateCommentNumberAndValuateMark(Integer courseId, BigDecimal valuateMarkParam) {
        this.increaseCommentNumber(courseId);
        RLock lock = redissonClient.getLock(LockKey.COURSE_INFO_VALUATE + courseId);
        try {
            lock.lock();
            // 重新计算课程分数
            CourseInfo courseInfo = super.getById(courseId);
            BigDecimal valuateMark = courseInfo.getValuateMark();
            valuateMark = valuateMark.add(valuateMarkParam);
            Integer commentNumber = courseInfo.getCommentNumber();
            valuateMark = valuateMark.divide(new BigDecimal(commentNumber));
            courseInfo.setValuateMark(valuateMark);
            super.update(Wrappers.lambdaUpdate(CourseInfo.class)
                    .set(CourseInfo::getValuateMark, valuateMark)
                    .eq(CourseInfo::getId, courseInfo.getId()));
        } finally {
            lock.unlock();
        }
    }

    public void checkCourse(Integer courseId) {
        CourseInfo courseInfo = super.selectFirst(Wrappers.<CourseInfo>lambdaQuery().eq(CourseInfo::getId, courseId));
        if (courseInfo == null) {
            throw new BusinessException("课程不存在!");
        }
        if (EnumConstants.CourseStatus.GROUNDING.getValue().equals(courseInfo.getStatus())) {
            throw new BusinessException("课程已上架，无法删除");
        }
    }
}
