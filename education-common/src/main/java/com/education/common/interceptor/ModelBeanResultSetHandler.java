package com.education.common.interceptor;

import com.education.common.model.ModelBean;
import com.education.common.utils.ObjectUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/7/2 16:32
 * @since 1.0.0
 */
public class ModelBeanResultSetHandler extends DefaultResultSetHandler {

    protected MappedStatement mappedStatement;
    protected ObjectFactory objectFactory;
    private static final Logger logger = LoggerFactory.getLogger(ModelBeanResultSetHandler.class);

    public ModelBeanResultSetHandler(Executor executor, MappedStatement mappedStatement,
                                     ParameterHandler parameterHandler, ResultHandler<?> resultHandler,
                                     BoundSql boundSql, RowBounds rowBounds, ObjectFactory objectFactory) {
        super(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
        this.mappedStatement = mappedStatement;
        this.objectFactory = objectFactory;
    }

    @Override
    public List<Object> handleResultSets(Statement stmt) throws SQLException {
        final List<Object> multipleResults = new ArrayList<>();
        List<ResultMap> resultMapList = mappedStatement.getResultMaps();
        ResultSet resultSet = stmt.getResultSet();
        if (ObjectUtils.isNotEmpty(resultMapList)) {
            resultMapList.forEach(resultMap -> {
                handlerValue(resultMap, resultSet, multipleResults);
            });
        }
        return multipleResults;
    }

    private void handlerValue(ResultMap resultMap, ResultSet resultSet, List<Object> multipleResults) {
        Class<?> clazz = resultMap.getType();
        if (clazz.getSuperclass() != ModelBean.class) {
            throw new RuntimeException("resultType 返回值类型需要继承父类" + ModelBean.class);
        }
        try {
            ModelBean modelBean = (ModelBean) objectFactory.create(clazz);
            ResultSetMetaData rs = resultSet.getMetaData();
            int columnCount = rs.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                String columnName = rs.getColumnLabel(i + 1);
                columnNames[i] = columnName;
            }
            while (resultSet.next()) {
                for (String name : columnNames) {
                    Object value =  resultSet.getObject(name);
                    modelBean.setAttr(name, value);
                }
                multipleResults.add(modelBean);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
