package com.education.canal;

import com.education.model.entity.GradeInfo;
import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/8/21 20:23
 */
@Component
@CanalTable(table = "grade_info")
public class GradeInfoCanalTableListener implements CanalTableListener<GradeInfo> {

    @Override
    public void onInsert(GradeInfo tableData) {
        System.out.println(tableData);
    }

    @Override
    public void onDelete(GradeInfo tableData) {
        System.out.println(tableData);
    }

    @Override
    public void onUpdate(GradeInfo beforeTableData, GradeInfo afterTableData) {
        System.out.println(beforeTableData);
        System.out.println(afterTableData);
    }
}
