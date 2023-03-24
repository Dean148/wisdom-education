package com.education.business.service.education;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.education.business.correct.QuestionCorrect;
import com.education.business.correct.SystemQuestionCorrect;
import com.education.business.correct.TeacherQuestionCorrect;
import com.education.business.mapper.education.ExamInfoMapper;
import com.education.business.message.QueueManager;
import com.education.business.service.BaseService;
import com.education.business.session.UserSessionContext;
import com.education.common.constants.CacheKey;
import com.education.common.constants.CacheTime;
import com.education.common.constants.SystemConstants;
import com.education.common.constants.EnumConstants;
import com.education.common.exception.BusinessException;
import com.education.common.model.PageInfo;
import com.education.common.utils.*;
import com.education.model.dto.ExamCount;
import com.education.model.dto.QuestionInfoAnswer;
import com.education.model.dto.StudentExamInfoDto;
import com.education.model.dto.TestPaperQuestionDto;
import com.education.model.entity.*;
import com.education.model.request.PageParam;
import com.education.model.request.StudentQuestionRequest;
import com.education.model.response.*;
import com.jfinal.kit.Kv;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/22 16:12
 */
@Service
public class ExamInfoService extends BaseService<ExamInfoMapper, ExamInfo> {

    @Resource
    private StudentQuestionAnswerService studentQuestionAnswerService;
    @Resource
    private QuestionInfoService questionInfoService;
    @Resource
    private TestPaperInfoService testPaperInfoService;
    @Resource
    private ExamMonitorService examMonitorService;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private TestPaperInfoSettingService testPaperInfoSettingService;
    @Resource
    private QueueManager queueManager;


    /**
     * 后台考试列表
     * @param pageParam
     * @param studentExamInfoDto
     * @return
     */
    public PageInfo<StudentExamInfoDto> selectExamInfoList(PageParam pageParam, StudentExamInfoDto studentExamInfoDto) {
        Page<StudentExamInfoDto> page = new Page(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectExamList(page, studentExamInfoDto));
    }


    /**
     * 获取学员考试记录列表
     * @param pageParam
     * @param studentExamInfoDto
     * @return
     */
    public PageInfo<StudentExamInfoDto> selectStudentExamInfoList(PageParam pageParam, StudentExamInfoDto studentExamInfoDto) {
        studentExamInfoDto.setStudentId(UserSessionContext.getStudentId());
        Page<StudentExamInfoDto> page = new Page(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectStudentExamList(page, studentExamInfoDto));
    }

    @Transactional
    public QuestionCorrectResponse commitTestPaperInfoQuestion(StudentQuestionRequest studentQuestionRequest) {
        QuestionCorrectResponse questionCorrectResponse = new QuestionCorrectResponse();
        Integer testPaperInfoId = studentQuestionRequest.getTestPaperInfoId();
        studentQuestionRequest.setStudentId(UserSessionContext.getStudentId());
        // 从缓存读取试卷配置，提升并发性能
        TestPaperInfoSetting testPaperInfoSetting = cacheBean.get(CacheKey.PAPER_INFO_SETTING, testPaperInfoId);
        Integer commitAfterType = EnumConstants.CommitAfterType.SHOW_MARK_NOW.getValue();
        if (testPaperInfoSetting != null) {
            commitAfterType = testPaperInfoSetting.getCommitAfterType();
        }
        QuestionCorrect questionCorrect = new SystemQuestionCorrect(studentQuestionRequest,
                queueManager, getQuestionAnswerInfoByPaperId(testPaperInfoId), commitAfterType);
        questionCorrect.correctStudentQuestion();

        ExamInfo examInfo = questionCorrect.getExamInfo();
        // 获取系统评分之后立即返回客户端, 然后通过rabbitmq 异步保存学员答题记录及错题信息
        if (EnumConstants.CommitAfterType.SHOW_MARK_AFTER_CORRECT.getValue().equals(commitAfterType)) {
            this.examMarkSort(examInfo, questionCorrectResponse);
        } else {
            boolean flag = this.queryHasDoTestPaper(testPaperInfoId);
            if (!flag) {
                // 更新试卷参考人数
                testPaperInfoService.updateExamNumber(testPaperInfoId);
            }

            // 同步保存考试、错题、答题记录
            this.save(examInfo);
            List<StudentQuestionAnswer> studentQuestionAnswerList = questionCorrect.getStudentQuestionAnswerList();
            List<StudentWrongBook> studentWrongBookList = questionCorrect.getStudentWrongBookList();
            questionCorrect.saveStudentQuestionAnswer(examInfo.getId(), studentQuestionAnswerList, studentWrongBookList);
        }
        examMonitorService.removeStudent(UserSessionContext.getStudentId(), testPaperInfoId); // 离开考试监控
        questionCorrectResponse.setExamTime(examInfo.getExamTime());
        questionCorrectResponse.setExamInfoId(examInfo.getId());
        return questionCorrectResponse;
    }

    /**
     * 查询学员是否已经作答过当前试卷
     * @param testPaperId
     * @return
     */
    public boolean queryHasDoTestPaper(Integer testPaperId) {
        LambdaQueryWrapper lambdaQueryWrapper = Wrappers.<ExamInfo>lambdaQuery().eq(ExamInfo::getTestPaperInfoId, testPaperId)
                .eq(ExamInfo::getStudentId, UserSessionContext.getStudentId());
        return super.selectFirst(lambdaQueryWrapper) != null;
    }

    /**
     * 系统评分成绩排序
     * @param examInfo
     * @param questionCorrectResponse
     */
    private void examMarkSort(ExamInfo examInfo, QuestionCorrectResponse questionCorrectResponse) {
        // redis 计算分数排行榜
        Set<ZSetOperations.TypedTuple<StudentInfo>> tuples = new HashSet<>();
        Integer systemMark = examInfo.getSystemMark();
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setId(UserSessionContext.getStudentId());
        studentInfo.setName(UserSessionContext.getStudentUserSession().getName());
        DefaultTypedTuple tuple = new DefaultTypedTuple(studentInfo, systemMark.doubleValue());
        tuples.add(tuple);
        String sortKey = CacheKey.EXAM_SORT_KEY + examInfo.getTestPaperInfoId();
        redisTemplate.opsForZSet().add(sortKey, tuples);
        // 取出排行榜1-10的学员
        Set<StudentInfo> studentScore = redisTemplate.opsForZSet().reverseRange(sortKey, 1, 10);
        questionCorrectResponse.setStudentInfoSet(studentScore);
        questionCorrectResponse.setStudentMark(systemMark); // 返回考试分数
    }

    /**
     * 从缓存获取试卷试题答案 (开考的时候已经将试卷试题进行缓存)
     * @param testPaperInfoId
     * @return
     */
    public Map<Integer, String> getQuestionAnswerInfoByPaperId(Integer testPaperInfoId) {
        List<TestPaperQuestionDto> testPaperQuestionDtoList = testPaperInfoService.selectPaperQuestionListByCache(testPaperInfoId);
        Map<Integer, String> questionAnswerInfo = new HashMap<>();
        testPaperQuestionDtoList.forEach(questionItem -> {
            questionAnswerInfo.put(questionItem.getQuestionInfoId(), questionItem.getAnswer());
        });
        return questionAnswerInfo;
    }

 // 暂时注释  @Cacheable(cacheNames = CacheKey.EXAM_CACHE, key = "#studentId + ':' + #examInfoId")
    public QuestionGroupResponse selectExamQuestionAnswer(Integer studentId, Integer examInfoId) {
        StudentExamInfoDto studentExamInfoDto = this.getExamInfoById(examInfoId);
        List<QuestionInfoAnswer> examQuestionAnswerList = studentQuestionAnswerService
                .getQuestionAnswerByExamInfoId(studentId, examInfoId);
        List<QuestionGroupItemResponse> list = questionInfoService.groupQuestion(examQuestionAnswerList);
        ExamQuestionGroupResponse examQuestionResponse = new ExamQuestionGroupResponse();
        examQuestionResponse.setQuestionGroupItemResponseList(list);
        examQuestionResponse.setTotalQuestion(list.size());
        examQuestionResponse.setStudentExamInfoDto(studentExamInfoDto);
        return examQuestionResponse;
    }

    public StudentExamInfoDto getExamInfoById(Integer examInfoId) {
        return baseMapper.selectById(examInfoId);
    }

    /**
     * 批改学员试卷
     * @param studentQuestionRequest
     */
    @Transactional
    public void correctStudentExam(StudentQuestionRequest studentQuestionRequest) {
        ExamInfo examInfo = super.getById(studentQuestionRequest.getExamInfoId());
        if (ResultCode.SUCCESS.equals(examInfo.getCorrectFlag())) {
            throw new BusinessException("试卷已被批改");
        }
        Integer studentId = studentQuestionRequest.getStudentId();

        // 先删除原先的主观题答题记录
        studentQuestionAnswerService.deleteByExamInfoId(studentId, examInfo.getId());
        studentQuestionRequest.setTestPaperInfoId(examInfo.getTestPaperInfoId());

        QuestionCorrect questionCorrect = new TeacherQuestionCorrect(studentQuestionRequest, examInfo,
                getQuestionAnswerInfoByPaperId(studentQuestionRequest.getTestPaperInfoId()));
        questionCorrect.correctStudentQuestion();


        examInfo = questionCorrect.getExamInfo();
        examInfo.setAdminId(UserSessionContext.getAdminUserId());
        super.updateById(examInfo);

        List<StudentQuestionAnswer> studentQuestionAnswerList = questionCorrect.getObjectiveQuestionAnswerList();
        List<StudentWrongBook> studentWrongBookList =questionCorrect.getStudentWrongBookList();
        Date now = new Date();
        studentQuestionAnswerList.forEach(item -> item.setCreateDate(now));
        // 重新保存主观题答题记录
        // 保存后台教师指定错题
        questionCorrect.saveStudentQuestionAnswer(examInfo.getId(), studentQuestionAnswerList, studentWrongBookList);
    }

    public PageInfo<TestPaperInfoReport> selectExamReportList(PageParam pageParam, TestPaperInfo testPaperInfo) {
        Page<TestPaperInfoReport> page = new Page(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectExamReportList(page, testPaperInfo));
    }

    public ExamInfoDetail examDetailReport(Integer testPaperInfoId) {
        TestPaperInfo testPaperInfo = testPaperInfoService.getById(testPaperInfoId);
        Integer mark = testPaperInfo.getMark();
        double passMark = NumberUtils.doubleToBigDecimal(mark * SystemConstants.PASS_MARK_RATE);
        double niceMark = NumberUtils.doubleToBigDecimal(mark * SystemConstants.NICE_MARK_RATE);
        Kv params = Kv.create().set("testPaperInfoId", testPaperInfoId).set("passMark", passMark)
                .set("niceMark", niceMark)
                .set("mark", mark);
        ExamInfoDetail examInfoDetail = baseMapper.selectExamInfoDetail(params);
        examInfoDetail.setPassExamMark(passMark);
        examInfoDetail.setNiceExamMark(niceMark);
        examInfoDetail.setExamTime(DateUtils.secondToHourMinute(testPaperInfo.getExamTime()));
        examInfoDetail.setExamNumber(testPaperInfo.getExamNumber());
        return examInfoDetail;
    }

    public PageInfo<ExamInfoReport> getExamRankingList(PageParam pageParam, Integer testPaperInfoId) {
        Page<ExamInfoReport> page = new Page(pageParam.getPageNumber(), pageParam.getPageSize());
        return selectPage(baseMapper.selectExamListByTestPaperInfoId(page, testPaperInfoId));
    }

    /**
     * 获取近七天考试记录统计
     * @return
     */
    public List<ExamCount> selectExamInfoData() {
        Date now = new Date();
        String startTime = DateUtils.getDayBefore(DateUtils.getSecondDate(now), 7);
        String endTime = DateUtils.getDayBefore(DateUtils.getSecondDate(now), 1);
        // 获取近七天的开始时间和结束时间
        startTime += " 00:00:00";
        endTime += " 23:59:59";

        List<ExamCount> dataList = baseMapper.countByDateTime(startTime, endTime);
        Map<String, Integer> dataTimeMap = new HashMap<>();
        dataList.forEach(data -> {
            String day = data.getDayGroup();
            Integer examNumber = data.getExamNumber();
            dataTimeMap.put(day, examNumber);
        });

        List<String> weekDateList = DateUtils.getSectionByOneDay(8);
        // 近七天日期集合
        weekDateList.remove(weekDateList.size() - 1); // 移除最后一天，也就是当天的日期
        List<ExamCount> resultDataList = new ArrayList<>();
        weekDateList.forEach(day -> {
            ExamCount item = new ExamCount();
            item.setDayGroup(day);
            item.setExamNumber(ObjectUtils.isNotEmpty(dataTimeMap.get(day)) ? dataTimeMap.get(day) : 0);
            resultDataList.add(item);
        });

        return resultDataList;
    }
}
