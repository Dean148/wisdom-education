package com.education.business.service.education;

import com.education.business.mapper.education.CourseSectionNodeMapper;
import com.education.business.service.BaseService;
import com.education.model.entity.CourseSectionNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zengjintao
 * @create_at 2021/10/6 11:00
 * @since version 1.0.3
 */
@Service
public class CourseSectionNodeService extends BaseService<CourseSectionNodeMapper, CourseSectionNode> {

    @Autowired
    private CourseInfoService courseInfoService;

    @Override
    @Transactional
    public boolean saveOrUpdate(CourseSectionNode courseSectionNode) {
        if (courseSectionNode.getId() == null) {
            courseInfoService.increaseSectionNodeNumber(courseSectionNode.getId());
        }
        return super.saveOrUpdate(courseSectionNode);
    }
}
