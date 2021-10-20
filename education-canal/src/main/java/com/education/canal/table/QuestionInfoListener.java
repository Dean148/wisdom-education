package com.education.canal.table;

import com.education.canal.CanalTable;
import com.education.canal.CanalTableListener;
import com.education.model.entity.QuestionInfo;
import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @version 1.6.4
 * @create_at 2021年9月30日 0030 16:32
 */
@Component
@CanalTable(table = "question_info")
public class QuestionInfoListener implements CanalTableListener<QuestionInfo> {

    @Override
    public void onInsert(QuestionInfo tableData) {

    }

    @Override
    public void onDelete(QuestionInfo tableData) {

    }

    @Override
    public void onUpdate(QuestionInfo beforeTableData, QuestionInfo afterTableData) {

    }
}
