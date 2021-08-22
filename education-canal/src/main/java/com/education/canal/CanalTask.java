package com.education.canal;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.education.business.task.TaskManager;
import com.education.model.entity.BaseEntity;
import com.jfinal.core.converter.TypeConverter;
import com.jfinal.json.Jackson;
import com.jfinal.json.JacksonFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;

/**
 * canal 异步数据监听管理器
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/8/21 20:12
 */
@Component
@Slf4j
public class CanalTask implements ApplicationContextAware, DisposableBean {

    @Autowired
    private TaskManager taskManager;
    private final Map<String, CanalTableListener> canalTableListenerMap = new HashMap();
    private final Map<String, TableInfo> entityMap = new HashMap();
    private final Jackson jackson = (Jackson) JacksonFactory.me().getJson();

    private CanalConnector connector;

    @Value("${canal.open}")
    private boolean canalOpenFlag;
    private final int batchSize = 1000;

    @Override
    public void destroy() throws Exception {
        if (connector != null) {
            connector.disconnect();
        }
    }

    @Data
    private static class ColumnField {
        private String column;

        private Field field;

        private Class<?> clazz;

        public Class<?> getClazz() {
            return field.getDeclaringClass();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (canalOpenFlag) {
            Map<String, CanalTableListener> beansOfType = applicationContext.getBeansOfType(CanalTableListener.class);
            beansOfType.forEach((key, value) -> {
                CanalTable canalTable = value.getClass().getAnnotation(CanalTable.class);
                if (canalTable != null) {
                    //  String dataBase = canalTable.dataBase();
                    String table = canalTable.table();
                    String tableKey = table; // dataBase + ":" + table;
                    canalTableListenerMap.put(tableKey, value);
                }
            });

            List<TableInfo> tableInfoList = TableInfoHelper.getTableInfos();
            tableInfoList.forEach(tableInfo -> {
                String tableName = tableInfo.getTableName();
                // Class<?> tableClass = tableInfo.getEntityType();
                entityMap.put(tableName, tableInfo);
            });

            //   System.out.printf(beansOfType.toString());
            taskManager.pushTask(() -> {
                this.canalListener();
            });
        }
    }


    private void printEntry(List<Entry> entryList) {

        for (Entry entry : entryList) {


            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChange = null;
            try {
                rowChange = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChange.getEventType();
          //  String dataBase = entry.getHeader().getSchemaName();
            String table = entry.getHeader().getTableName();

            TableInfo tableInfo = entityMap.get(table);
            CanalTableListener canalTableListener = canalTableListenerMap.get(table);
            if (tableInfo != null && canalTableListener != null) {
                List<TableFieldInfo> tableFieldInfoList = tableInfo.getFieldList();

                Map<String, ColumnField> columnFieldMapping = new HashMap<>();
                tableFieldInfoList.forEach(item -> {
                    ColumnField columnField = new ColumnField();
                    columnField.setField(item.getField());
                    columnField.setColumn(item.getColumn());
                    columnFieldMapping.put(item.getColumn(), columnField);
                });

                for (RowData rowData : rowChange.getRowDatasList()) {

                    System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                            entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                            entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                            eventType));

                    Map<String, Object> data = null;
                    if (eventType == EventType.DELETE) {
                        data = columnToMap(rowData.getBeforeColumnsList(), columnFieldMapping);
                        canalTableListener.onDelete(jackson.parse(jackson.toJson(data), tableInfo.getEntityType()));
                    } else if (eventType == EventType.INSERT) {
                        data = columnToMap(rowData.getAfterColumnsList(), columnFieldMapping);
                        canalTableListener.onInsert(jackson.parse(jackson.toJson(data), tableInfo.getEntityType()));
                    } else {
                        Map<String, Object> beforeData = columnToMap(rowData.getBeforeColumnsList(), columnFieldMapping);
                        Map<String, Object> afterData = columnToMap(rowData.getAfterColumnsList(), columnFieldMapping);
                        canalTableListener.onUpdate(jackson.parse(jackson.toJson(beforeData), tableInfo.getEntityType()),
                                jackson.parse(jackson.toJson(afterData), tableInfo.getEntityType()));
                    }
                }
            }
        }
    }

    private Map<String, Object> columnToMap(List<Column> columns, Map<String, ColumnField> columnFieldMapping) {
        Map<String, Object> columnMap = new HashMap<>();
        for (Column column : columns) {
            ColumnField columnField = columnFieldMapping.get(column.getName());
            try {
                Object value = null;
                if (column.getName().equals("id")) {
                    value = TypeConverter.me().convert(Integer.class, column.getValue());
                    columnMap.put(column.getName(), value);
                } else {
                    value = TypeConverter.me().convert(columnField.getField().getType(), column.getValue());
                    columnMap.put(columnField.getField().getName(), value);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return columnMap;
    }

    private void canalListener() {
        connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
                11111), "example", "", "");
        connector.connect();
        connector.subscribe(".*\\..*");
        connector.rollback();
        log.info("------------------------------- canal 数据监听服务连接成功 -------------------------");
        while (true) {
            Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
            long batchId = message.getId();
            int size = message.getEntries().size();

            if (batchId != -1 && size != 0) {
                printEntry(message.getEntries());
            }
            connector.ack(batchId); // 提交确认
            // connector.rollback(batchId); // 处理失败, 回滚数据
        }

    }


}
