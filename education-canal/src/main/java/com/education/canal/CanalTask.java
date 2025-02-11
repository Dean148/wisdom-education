package com.education.canal;

import cn.hutool.json.JSONUtil;
import com.alibaba.otter.canal.protocol.exception.CanalClientException;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.education.business.task.TaskManager;
import com.jfinal.core.converter.TypeConverter;
import com.jfinal.json.Jackson;
import com.jfinal.json.JacksonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;

import javax.annotation.Resource;

/**
 * canal 异步数据监听管理器
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/8/21 20:12
 */
@Component
public class CanalTask implements ApplicationContextAware, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(CanalTask.class);
    @Resource
    private TaskManager taskManager;
    private final Map<String, CanalTableListener> canalTableListenerMap = new HashMap();
    private final Map<String, TableInfo> entityMap = new HashMap();
    private final Jackson jackson = (Jackson) JacksonFactory.me().getJson();
    @Autowired
    private CanalProperties canalProperties;

    private CanalConnector connector;
    // 批量从 canal 服务器获取数据的最多数目
    private final int batchSize = 1000;

    @Override
    public void destroy() throws Exception {
        if (connector != null) {
            connector.disconnect();
        }
    }

    private static class ColumnField {
        private String column;

        private Field field;

        private Class<?> clazz;

        public Class<?> getClazz() {
            return field.getDeclaringClass();
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public void setClazz(Class<?> clazz) {
            this.clazz = clazz;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (canalProperties.isOpenFlag()) {
            Map<String, CanalTableListener> beansOfType = applicationContext.getBeansOfType(CanalTableListener.class);
            this.scanCanalListenerBean(beansOfType);
        }
    }

    private void scanCanalListenerBean(Map<String, CanalTableListener> beansOfType) {
        beansOfType.forEach((key, value) -> {
            CanalTable canalTable = value.getClass().getAnnotation(CanalTable.class);
            if (canalTable != null) {
                String table = canalTable.table();
                canalTableListenerMap.put(table, value);
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
            // Thread.currentThread().setName("Canal Connection Task");
            this.canalListener();
        });
    }


    private void printEntry(List<Entry> entryList) throws Exception {

        for (Entry entry : entryList) {

            logger.info("canal监听到数据: " + JSONUtil.toJsonStr(entry));
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());


            EventType eventType = rowChange.getEventType();
            //  String dataBase = entry.getHeader().getSchemaName();
            String table = entry.getHeader().getTableName();

            TableInfo tableInfo = entityMap.get(table);
            CanalTableListener canalTableListener = canalTableListenerMap.get(table);
            if (tableInfo != null && canalTableListener != null) {
                List<TableFieldInfo> tableFieldInfoList = tableInfo.getFieldList();

                String primaryKey = tableInfo.getKeyColumn();
                Class<?> primaryKeyClazz = tableInfo.getKeyType();

                Map<String, ColumnField> columnFieldMapping = new HashMap<>();
                tableFieldInfoList.forEach(item -> {
                    ColumnField columnField = new ColumnField();
                    columnField.setField(item.getField());
                    columnField.setColumn(item.getColumn());
                    columnFieldMapping.put(item.getColumn(), columnField);
                });

                for (RowData rowData : rowChange.getRowDatasList()) {
                    logger.info(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                            entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                            entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                            eventType));

                    Map<String, Object> data;
                    if (eventType == EventType.DELETE) {
                        data = columnToMap(rowData.getBeforeColumnsList(), primaryKey, primaryKeyClazz, columnFieldMapping);
                        canalTableListener.onDelete(jackson.parse(jackson.toJson(data), tableInfo.getEntityType()));
                    } else if (eventType == EventType.INSERT) {
                        data = columnToMap(rowData.getAfterColumnsList(), primaryKey, primaryKeyClazz, columnFieldMapping);
                        canalTableListener.onInsert(jackson.parse(jackson.toJson(data), tableInfo.getEntityType()));
                    } else {
                        Map<String, Object> beforeData = columnToMap(rowData.getBeforeColumnsList(), primaryKey, primaryKeyClazz, columnFieldMapping);
                        Map<String, Object> afterData = columnToMap(rowData.getAfterColumnsList(), primaryKey, primaryKeyClazz, columnFieldMapping);
                        canalTableListener.onUpdate(jackson.parse(jackson.toJson(beforeData), tableInfo.getEntityType()),
                                jackson.parse(jackson.toJson(afterData), tableInfo.getEntityType()));
                    }
                }
            }
        }
    }

    private Map<String, Object> columnToMap(List<Column> columns, String primaryKey, Class<?> primaryKeyClazz, Map<String, ColumnField> columnFieldMapping) throws Exception {
        Map<String, Object> columnMap = new HashMap<>();
        for (Column column : columns) {
            ColumnField columnField = columnFieldMapping.get(column.getName());
            Object value;
            TypeConverter typeConverter = TypeConverter.me();
            if (column.getName().equals(primaryKey)) {
                value = typeConverter.convert(primaryKeyClazz, column.getValue());
                columnMap.put(column.getName(), value);
            } else {
                value = typeConverter.convert(columnField.getField().getType(), column.getValue());
                columnMap.put(columnField.getField().getName(), value);
            }
        }
        return columnMap;
    }


    private void retryConnectionCanal() {
        long sleep = 10000; // 休眠10秒
        try {
            logger.info("Start Connection Canal Server....................");
            connector = CanalConnectors.newSingleConnector(new InetSocketAddress(canalProperties.getHost(),
                    canalProperties.getPort()), canalProperties.getDestination(), canalProperties.getUserName(), canalProperties.getPassword());
            connector.connect();
        } catch (Exception e) {
            logger.error("Canal Client Connection Error", e);
            try {
                Thread.sleep(sleep);
                this.retryConnectionCanal();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }


    private void canalListener() {
        this.retryConnectionCanal();
        connector.subscribe(".*\\..*");
        logger.info("Canal Client 数据监听服务连接成功........................");
        while (true) {
            try {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();

                if (batchId != -1 && size != 0) {
                    printEntry(message.getEntries());
                }
                connector.ack(batchId); // 提交确认
            } catch (Exception e) {
                if (e instanceof CanalClientException) {
                    this.retryConnectionCanal();
                } else {
                    connector.rollback(); // 处理失败, 回滚数据
                    logger.error("Canal Client Listener Data Error", e);
                }
            }
        }
    }
}
