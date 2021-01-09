package com.education.business.parser;

import com.education.common.utils.NumberUtils;
import java.util.*;

/**
 * 选择题解析器
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/31 9:51
 */
public class SelectQuestionParser extends AbstractExcelQuestionParser {

  //  [{"label": "A", "option_name": "<p>说的</p>"}, {"label": "B", "option_name": "<p>读书的</p>"}, {"label": "C", "option_name": "<p>ere</p>"}]

    @Override
    public String parseOptionText(String option) {
        String[] optionArray = super.parserToken(option);
        if (optionArray == null) {
            return super.parseOptionText(option);
        }
        List<Map> optionList = new ArrayList<>();
        for (String optionItem : optionArray) {
            Map data = new LinkedHashMap<>();
            String letter = NumberUtils.generateLetter(0);
            data.put("label", letter);
            data.put("option_name", "<p>" + optionItem + "</p>");
            optionList.add(data);
        }
        return jackson.toJson(optionList);
    }
}
