package com.education.business.service.education;

import com.education.business.mapper.education.TestPaperInfoSettingMapper;
import com.education.business.service.BaseService;
import com.education.model.entity.TestPaperInfoSetting;
import org.springframework.stereotype.Service;

/**
 * 试卷设置业务层
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/30 21:26
 */
@Service
public class TestPaperInfoSettingService extends BaseService<TestPaperInfoSettingMapper, TestPaperInfoSetting> {

    public TestPaperInfoSetting selectByTestPaperInfoId(Integer testPaperInfoId) {

        //cacheBean.get("")
        return null;
    }
}
