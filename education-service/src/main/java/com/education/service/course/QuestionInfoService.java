package com.education.service.course;


import com.education.common.constants.EnumConstants;
import com.education.common.exception.BusinessException;
import com.education.common.model.ModelBeanMap;
import com.education.common.model.QuestionInfo;
import com.education.common.model.UserAnswerInfo;
import com.education.common.utils.*;
import com.education.mapper.course.CourseQuestionInfoMapper;
import com.education.mapper.course.QuestionInfoMapper;
import com.education.mapper.course.StudentQuestionAnswerMapper;
import com.education.mapper.course.TestPaperQuestionMapper;
import com.education.service.BaseService;
import com.education.service.system.SystemDictService;
import com.education.service.system.SystemDictValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 试题管理service
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/9 15:42
 */
@Service
@Slf4j
public class QuestionInfoService extends BaseService<QuestionInfoMapper> {

    @Autowired
    private StudentQuestionAnswerMapper studentQuestionAnswerMapper;
    @Autowired
    private CourseQuestionInfoMapper courseQuestionInfoMapper;
    @Autowired
    private TestPaperQuestionMapper testPaperQuestionMapper;
    @Autowired
    private SystemDictValueService systemDictValueService;
    @Autowired
    private SubjectInfoService subjectInfoService;
    @Autowired
    private LanguagePointsService languagePointsService;

    @Override
    @Transactional
    public ResultCode deleteById(ModelBeanMap modelBeanMap) {
        try {
            modelBeanMap.put("questionInfoId", modelBeanMap.getInt("id"));
            ModelBeanMap result = studentQuestionAnswerMapper.getStudentQuestionAnswerInfo(modelBeanMap);
            if (ObjectUtils.isNotEmpty(result)) {
                return new ResultCode(ResultCode.FAIL, "试题已被使用.无法删除");
            }
            courseQuestionInfoMapper.delete(modelBeanMap);
            testPaperQuestionMapper.delete(modelBeanMap);
            return new ResultCode(ResultCode.SUCCESS, "删除试题成功");
        } catch (Exception e) {
            log.error("删除试题失败", e);
            throw new BusinessException(new ResultCode(ResultCode.FAIL, "删除试题失败"));
        }
    }

    public Result saveOrUpdate(ModelBeanMap questionInfoMap) {
        String message = "";
        try {
            List<Map> optionList = null;
            // 试题选项
            if (ObjectUtils.isNotEmpty(questionInfoMap.get("options"))) {
                Integer questionType = questionInfoMap.getInt("question_type");
                if (questionType == EnumConstants.QuestionType.SINGLE_QUESTION.getValue()
                        || questionType == EnumConstants.QuestionType.MULTIPLE_QUESTION.getValue()) {
                    optionList = questionInfoMap.getList("options");
                    String options = "";
                    for (Map option : optionList) {
                        options += option.get("option_name") + ",";
                    }
                    questionInfoMap.put("options", options);
                }
            } else {
                questionInfoMap.put("options", null);
            }
            Integer id = questionInfoMap.getInt("id");
            Object answer = questionInfoMap.get("answer");
            if (ObjectUtils.isNotEmpty(answer)) {
                if (answer instanceof String) {
                    String answerStr = (String) answer;
                    if (answerStr.endsWith(",") && answerStr.length() > 1) {
                        questionInfoMap.put("answer", answerStr.substring(0, answerStr.length() - 1));
                    }
                    // 判断题类型 答案为整形,所以此处必须判断数据类型
                } else if (answer instanceof Integer) {
                    questionInfoMap.put("answer", (Integer) answer);
                }
            }
            Date now = new Date();
            if (ObjectUtils.isEmpty(questionInfoMap.get("id"))) {
                message = "添加";
                questionInfoMap.put("create_date", now);
                questionInfoMap.put("update_date", now);
                id = mapper.save(questionInfoMap);
            } else {
                message = "修改";
                questionInfoMap.put("update_date", now);
                mapper.update(questionInfoMap);
            }
            return new Result(ResultCode.SUCCESS, message + "试题成功");
        } catch (Exception e) {
            log.error(message + "试题失败", e);
            throw new BusinessException(new ResultCode(ResultCode.FAIL, message + "试题失败"));
        }
    }

    public void parserQuestionContentForDoc(ModelBeanMap questionMap, List<ModelBeanMap> dataList, int number, int exportType) {
        String content = questionMap.getStr("content");
        StringBuilder sb = new StringBuilder(number + "、" + content);
        RichHtmlHandler handler = new RichHtmlHandler(content);

        handler.setDocSrcLocationPrex("file:///C:/8595226D");
        handler.setDocSrcParent("file3405.files");
        handler.setNextPartId("01D214BC.6A592540");
        handler.setShapeidPrex("_x56fe__x7247__x0020");
        handler.setSpidPrex("_x0000_i");
        handler.setTypeid("#_x0000_t75");
        handler.handledHtml(false);
        String bodyBlock = handler.getHandledDocBodyBlock();
        String handledBase64Block = "";
        if (handler.getDocBase64BlockResults() != null
                && handler.getDocBase64BlockResults().size() > 0) {
            for (String item : handler.getDocBase64BlockResults()) {
                handledBase64Block += item + "\n";
            }
        }

        String xml = "";
        if (handler.getXmlImgRefs() != null
                && handler.getXmlImgRefs().size() > 0) {
            for (String item : handler.getXmlImgRefs()) {
                xml += item + "\n";
            }
        }

        ModelBeanMap params = new ModelBeanMap();
        params.put("imagesXmlHrefString", xml);
        params.put("imagesBase64String", handledBase64Block);
        params.put("options", null);
        if (ObjectUtils.isNotEmpty(questionMap.get("options"))) {
            params.put("options", (List<Map>)questionMap.get("options"));
        }
        String questionTypeName = EnumConstants.QuestionType.getName((Integer) questionMap.get("question_type"));
        params.put("questionTypeName", questionTypeName);
        params.put("showAnswerAnalysis", true);
        if (exportType == ResultCode.FAIL) {
            params.put("showAnswerAnalysis", false);
        }
        params.put("questionAnswer", questionMap.get("answer"));
        params.put("analysis", questionMap.get("analysis"));
        params.put("userQuestionAnswer", ObjectUtils.isEmpty(questionMap.get("user_answer")) ? "未作答" : questionMap.get("user_answer"));
        params.put("questionContent", bodyBlock);
        dataList.add(params);
    }

    public void parseQuestionOptions(ModelBeanMap questionMap) {
        String options = questionMap.getStr("options");
        // 拼接 'A','B','C','D'选项
        if (ObjectUtils.isNotEmpty(options)) {
            List<Map> optionList = new ArrayList<>();
            String optionsArray[] = ObjectUtils.spilt(options);
            for (int j = 0; j < optionsArray.length; j++) {
                Map optionMap = new HashMap<>();
                String letter = NumberUtils.generateLetter(j);
                optionMap.put("value", letter);
                optionsArray[j] = letter + ". " + optionsArray[j] + "  ";
                optionMap.put("option", optionsArray[j]);
                optionList.add(optionMap);
            }
            questionMap.put("options", optionList);
        }
        List<String> enclosureList = null;
        Object enclosure = questionMap.get("enclosure");
        if (ObjectUtils.isNotEmpty(enclosure)) {
            if (enclosure instanceof String[]) {
                enclosureList = Arrays.asList((String[])enclosure);
            } else {
                enclosureList = Arrays.asList(ObjectUtils.spilt((String)enclosure));
            }
        }
        questionMap.put("enclosureList", enclosureList);
    }

    /**
     * 解析试题
     * @param questionList 试题列表
     * @param userQuestionAnswerMap
     * @param modeType
     * @param resultMap
     */
    public void parserQuestion(List<ModelBeanMap> questionList, Map userQuestionAnswerMap, int modeType, Map resultMap) {
        int i = 1;
        for (ModelBeanMap questionMap : questionList) {
            Integer questionInfoId = (Integer) questionMap.get("id");
            int questionType = (Integer) questionMap.get("question_type");
            String questionName = EnumConstants.QuestionType.getName(questionType);
            questionMap.put("questionName", questionName);
            questionMap.put("question_type", questionType);
            questionMap.put("answer", questionMap.get("answer"));
            questionMap.put("userInfoAnswer", "");
            // 答案附件
            questionMap.put("enclosure", new String[]{});
            questionMap.put("comment", "");
            questionMap.put("source", 0);
            if (ObjectUtils.isNotEmpty(userQuestionAnswerMap)) {
                UserAnswerInfo userAnswerInfo = (UserAnswerInfo) userQuestionAnswerMap.get(questionInfoId);
                if (ObjectUtils.isNotEmpty(userAnswerInfo)) {
                    // 用户答案
                    questionMap.put("userInfoAnswer", userAnswerInfo.getUserAnswer());
                    questionMap.put("source", userAnswerInfo.getMark());
                    // 答案附件
                    questionMap.put("enclosure", userAnswerInfo.getEnclosure());
                    questionMap.put("comment", userAnswerInfo.getComment());
                    questionMap.put("correct_status", userAnswerInfo.getCorrectStatus());
                }

            }

            questionMap.put("video_url", questionMap.get("video_url"));
            questionMap.put("analysis", questionMap.get("analysis"));
            parseQuestionOptions(questionMap);
            String content = (String) questionMap.get("content");
            questionMap.put("content", String.valueOf(i) + "、 " + content);
            questionMap.put("questionInfoId", questionInfoId);
            i++;
        }
    }

    public Map getQuestionUserAnswer(List<ModelBeanMap> userQuestionAnswerList) {
        Map<Integer, Object> userQuestionAnswerMap = new HashMap<>();
        if (ObjectUtils.isNotEmpty(userQuestionAnswerList)) {
            userQuestionAnswerList.forEach(item -> {
                String userInfoAnswer = (String) item.get("userInfoAnswer");
                Integer questionInfoId = (Integer) item.get("question_info_id");
                if (ObjectUtils.isEmpty(questionInfoId)) {
                    questionInfoId = (Integer) item.get("id");
                }
                UserAnswerInfo userAnswerInfo = new UserAnswerInfo();
                userAnswerInfo.setId(questionInfoId);
                userAnswerInfo.setMark((Integer)item.get("mark"));
                userAnswerInfo.setCorrectStatus((Integer) item.get("correct_status"));
                userAnswerInfo.setComment((String)item.get("comment"));
                if ((Integer) item.get("question_type") == EnumConstants.QuestionType.MULTIPLE_QUESTION.getValue()) {
                    if (ObjectUtils.isNotEmpty(userInfoAnswer)) {
                        String answerArray[] = ObjectUtils.spilt(userInfoAnswer);
                        userAnswerInfo.setUserAnswer(answerArray);
                    }
                } else {
                    userAnswerInfo.setUserAnswer(userInfoAnswer);
                }
                String enclosure = (String) item.get("enclosure");
                if (ObjectUtils.isNotEmpty(enclosure)) {
                    String[] enclosureArray = ObjectUtils.spilt(enclosure);
                    userAnswerInfo.setEnclosure(enclosureArray);
                }
                userQuestionAnswerMap.put(questionInfoId, userAnswerInfo);
            });
        }
        return userQuestionAnswerMap;
    }

    public void parserQuestion(List<ModelBeanMap> questionList) {
        questionList.forEach(questionMap -> {
            Integer questionType = (Integer) questionMap.get("question_type");
            String questionTypeName = EnumConstants.QuestionType.getName(questionType);
            questionMap.put("questionTypeName", questionTypeName);
            String options = questionMap.getStr("options");
            if (ObjectUtils.isNotEmpty(options)) {
                this.parseQuestionOptions(questionMap);
            }
        });
    }

    /**
     * 试题信息导入
     * @param questionInfoList
     * @return
     */
    public void importQuestionFromExcel(List<QuestionInfo> questionInfoList) {
        List<Map> data = new ArrayList<>();
        for (QuestionInfo questionInfo : questionInfoList) {
            if (ObjectUtils.isEmpty(questionInfo.getContent())) {
                continue;
            }
            Map params = new HashMap<>();
            Integer questionType = systemDictValueService.getDictValueByName(SystemDictService.QUESTION_TYPE, questionInfo.getQuestionType());
            params.put("question_type", questionType); // 获取试题类型
            String answer = questionInfo.getAnswer();
            if (questionType == EnumConstants.QuestionType.JUDGMENT_QUESTION.getValue()) {
               params.put("answer", "对".equals(answer) ? Boolean.TRUE : Boolean.FALSE);
            } else {
               params.put("answer", answer);
            }
            params.put("content", questionInfo.getContent());
            params.put("options", questionInfo.getOptions());
            params.put("analysis", questionInfo.getAnalysis());
            String gradeName = questionInfo.getGradeInfoName();
            ModelBeanMap gradeTypeInfo = systemDictValueService.getDictValueForMapByName(SystemDictService.GRADE_TYPE, gradeName);
            Integer gradeType = gradeTypeInfo.getInt("code");
            params.put("grade_type", gradeTypeInfo.get("code"));
            params.put("school_type", gradeTypeInfo.get("parent_id"));
            Integer subjectId = getSubjectId(questionInfo.getSubjectName(), gradeType);
            params.put("subject_id", subjectId);
            Date now = new Date();
            params.put("create_date", now);
            data.add(params);
        }
        Map dataMap = new HashMap<>();
        dataMap.put("list", data);
        mapper.batchSave(dataMap);
    }

    private Integer getSubjectId(String subjectName, Integer gradeTypeId) {
        Map params = new HashMap<>();
        params.put("name", subjectName);
        params.put("gradeType", gradeTypeId);
        ModelBeanMap subjectInfo = subjectInfoService.findByNameAndGradeType(params);
        if (ObjectUtils.isNotEmpty(subjectInfo)) {
            return subjectInfo.getInt("id");
        }
        return null;
    }

    public Map findById(Integer questionInfoId) {
        ModelBeanMap questionInfo = mapper.findById(questionInfoId);
        // 获取科目列表
        Map params = new HashMap<>();
        params.put("grade_type", questionInfo.get("grade_type"));
        params.put("subjectId", questionInfo.get("subject_id"));
        Result<ModelBeanMap> result = subjectInfoService.pagination(params);
        questionInfo.put("subjectList", result.getData().get("dataList"));
        // 获取知识点列表
        result = languagePointsService.pagination(params);
        List<ModelBeanMap> languagePointsList = (List<ModelBeanMap>) result.getData().get("dataList");
        questionInfo.put("languagePointsList", MapTreeUtils.buildTreeData(languagePointsList));
        List<Integer> parentIds = MapTreeUtils.getParentIds(languagePointsList, questionInfo.getInt("language_points_id"));
        Collections.reverse(parentIds); // 从小到大来排序，与前端组件id顺序保持一致
        questionInfo.put("languagePointsIds", parentIds);
        return questionInfo;
    }

}
