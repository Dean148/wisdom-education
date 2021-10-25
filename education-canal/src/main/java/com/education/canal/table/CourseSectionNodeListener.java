package com.education.canal.table;

import com.education.canal.CanalTable;
import com.education.canal.CanalTableListener;
import com.education.model.entity.CourseSectionNode;
import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @create_at 2021/10/6 20:31
 * @since version 1.0.3
 */
@CanalTable(table = "course_section_node")
@Component
public class CourseSectionNodeListener implements CanalTableListener<CourseSectionNode> {

    @Override
    public void onInsert(CourseSectionNode tableData) {

    }

    @Override
    public void onDelete(CourseSectionNode tableData) {

    }

    @Override
    public void onUpdate(CourseSectionNode tableData, CourseSectionNode newTableData) {

    }
}
