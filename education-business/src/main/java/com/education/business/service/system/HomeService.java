package com.education.business.service.system;


import com.education.business.service.education.QuestionInfoService;
import com.education.business.service.education.StudentInfoService;
import com.education.business.service.education.TestPaperInfoService;
import com.jfinal.kit.Kv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 首页数据统计
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/12 11:29
 */
@Service
public class HomeService {

    private static final Logger logger = LoggerFactory.getLogger(HomeService.class);
    @Autowired
    private QuestionInfoService questionInfoService;
    @Autowired
    private TestPaperInfoService testPaperInfoService;
    @Autowired
    private StudentInfoService studentInfoService;

    public Kv countData() {
        int questionNumber = questionInfoService.count();
        int testPaperInfoNumber = testPaperInfoService.count();
        int studentNumber = studentInfoService.count();
        return Kv.create().set("questionNumber", questionNumber)
                .set("testPaperInfoNumber", testPaperInfoNumber)
                .set("studentNumber", studentNumber);
    }


    /**
     * 获取考试记录统计
     * @param resultMap
     *//*
    public void setExamInfoData(Map resultMap) {
        Date now = new Date();
        String startTime = DateUtils.getDayBefore(DateUtils.getSecondDate(now), 7);
        String endTime = DateUtils.getDayBefore(DateUtils.getSecondDate(now), 1);
        Map params = new HashMap<>();
        // 获取近七天的开始时间和结束时间
        params.put("startTime", startTime + " 00:00:00");
        params.put("endTime", endTime + " 23:59:59");

        List<ModelBeanMap> dataList = examInfoService.countByDateTime(params);
        Map dataTimeMap = new HashMap<>();
        dataList.forEach(data -> {
            String day = data.getStr("day_group");
            Integer examNumber = data.getInt("exam_number");
            dataTimeMap.put(day, examNumber);
        });

        List<String> weekDateList = DateUtils.getSectionByOneDay(8);
        // 近七天日期集合
        weekDateList.remove(weekDateList.size() - 1); // 移除最后一天，也就是当天的日期
        List<ModelBeanMap> resultDataList = new ArrayList<>();
        weekDateList.forEach(day -> {
            ModelBeanMap item = new ModelBeanMap();
            item.put("day_group", day);
            item.put("exam_number", ObjectUtils.isNotEmpty(dataTimeMap.get(day)) ? dataTimeMap.get(day) : 0);
            resultDataList.add(item);
        });
        resultMap.put("examInfoData", resultDataList);
    }


    public Result getRegionInfoData() {
        List<ModelBeanMap> data = schoolService.getSchoolRegionInfo();
        return Result.success(data);
    }*/
}
