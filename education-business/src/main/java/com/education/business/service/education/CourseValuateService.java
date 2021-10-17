package com.education.business.service.education;

import com.education.business.mapper.education.CourseValuateMapper;
import com.education.business.service.BaseService;
import com.education.common.enums.ValuateTypeEnum;
import com.education.common.exception.BusinessException;
import com.education.model.entity.CourseValuate;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

/**
 * @author zengjintao
 * @create_at 2021/10/17 9:32
 * @since version 1.0.3
 */
@Service
public class CourseValuateService extends BaseService<CourseValuateMapper, CourseValuate> {

    @Override
    public boolean saveOrUpdate(CourseValuate courseValuate) {
        Integer level = courseValuate.getLevel();
        if (level > 10) {
            throw new BusinessException("评价分数不能超过10分");
        }
        if (level > 0 && level < 5) {
            courseValuate.setValuateType(ValuateTypeEnum.NEGATIVE.getValue());
        } else if (level >= 8 && level <= 10) {
            courseValuate.setValuateType(ValuateTypeEnum.NEGATIVE.getValue());
        } else {
            courseValuate.setValuateType(ValuateTypeEnum.NEGATIVE.getValue());
        }
        return super.saveOrUpdate(courseValuate);
    }
}
