//package com.education.elasticsearch;
//
//import com.education.common.utils.ObjectUtils;
//import org.elasticsearch.client.Client;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ExecutionException;
//
//import static org.elasticsearch.client.Requests.refreshRequest;
//
///**
// * @author zengjintao
// * @version 1.0
// * @create_at 2021/8/29 14:43
// */
//public abstract class Elasticsearch {
//
//    static final Logger logger = LoggerFactory.getLogger(Elasticsearch.class);
//
//    protected boolean valueIsNull(String[] ids, List<Map> dataList) {
//        if (ObjectUtils.isEmpty(ids)) {
//            return true;
//        } else if (ids.length != dataList.size()) {
//            throw new RuntimeException("The length of ids is different from that of dataList");
//        }
//        return false;
//    }
//    /**
//     * 刷新索引
//     * @param client
//     * @param indexName
//     */
//    public void refresh(Client client, String indexName) {
//        client.admin().indices().refresh(refreshRequest(indexName)).actionGet();
//    }
//
//
//
//    /**
//     * 根据id删除索引
//     * @param index
//     * @param type
//     * @param id
//     * @return
//     */
//    public abstract boolean deleteDocumentById(String index, String type, String id);
//
//    /**
//     * 根据id更新索引
//     * @param index
//     * @param type
//     * @param id
//     * @param source
//     * @return
//     */
//    public abstract boolean updateById(String index, String type, String id, Map source);
//
//    /**
//     * 删除索引
//     * @param index
//     * @return
//     */
//    public abstract boolean deleteIndex(String index) throws ExecutionException, InterruptedException;
//
//    /**
//     * 批量更新索引
//     * @param index
//     * @param type
//     * @param ids
//     * @param source
//     * @return
//     */
//    public abstract boolean batchUpdateById(String index, String type, Set<String> ids, List<Map> source);
//
//    /**
//     * 批量删除所有
//     * @param index
//     * @param type
//     * @param ids
//     * @return
//     */
//    public abstract boolean batchDeleteById(String index, String type, Set<String> ids);
//
//    /**
//     * 根据字段值获取索引id (term精确查询，注意 field 字段类型必须为keyword类型)
//     * @param index
//     * @param type
//     * @param field
//     * @return
//     */
//    public abstract Set<String> getIndexDocumentId(String index, String type, String field, Object value);
//
//
//
//    /**
//     * 批量更新索引
//     * @param index
//     * @param type
//     * @param dataList
//     * @return
//     */
//    public abstract boolean batchUpdateById(String index, String type, Map<String, Map<String, Object>> dataList);
//
//    /**
//     * 关闭连接
//     * @param client
//     */
//    public void close(Client client) {
//        if (client != null)
//            client.close();
//    }
//
//    /**
//     * 添加文档
//     * @return
//     */
//    public abstract boolean save(String index, String type, Map dataMap);
//
//    /**
//     * 添加文档 使用自定义id
//     * @param index
//     * @param type
//     * @param id
//     * @param dataMap
//     * @return
//     */
//    public abstract boolean save(String index, String type, String id, Map dataMap);
//
//    /**
//     * 批量添加文档
//     * @param dataList
//     * @return
//     */
//    public abstract boolean batchSave(String index, String type, List<Map> dataList);
//
//
//    /**
//     * 批量添加文档 使用自定义id
//     * @param index
//     * @param type
//     * @param ids
//     * @param dataList
//     * @return
//     */
//    public abstract boolean batchSave(String index, String type, String[] ids, List<Map> dataList);
//
//
//    /**
//     * 创建索引
//     * @return
//     */
//    public abstract boolean createIndex(String index) throws IOException;
//
//    public abstract void close();
//
//    /**
//     * 添加字段数据类型
//     * @param index
//     * @param type
//     * @param json
//     * @return
//     */
//    public abstract boolean putMapping(String index, String type, String json);
//
//    /**
//     * 判断索引是否存在
//     * @param index
//     * @return
//     */
//    public abstract boolean indexExists(String index);
//
//    /**
//     * 分组查询 (注: es text字段不支持聚合查询)
//     */
//    public abstract void groupBy();
//}
