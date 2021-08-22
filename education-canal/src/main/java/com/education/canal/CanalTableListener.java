package com.education.canal;


/**
 * canal table 数据监听器
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/8/21 20:18
 */
public interface CanalTableListener<T> {

    /**
     * insert 数据监听
     * @param tableData
     */
    void onInsert(T tableData);

    /**
     * delete 数据监听
     * @param tableData
     */
    void onDelete(T tableData);

    /**
     * update 数据监听
     * @param beforeTableData
     * @param afterTableData
     */
    void onUpdate(T beforeTableData, T afterTableData);

}
