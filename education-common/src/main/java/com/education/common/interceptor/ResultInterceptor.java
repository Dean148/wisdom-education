package com.education.common.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/7/2 16:28
 * @since 1.0.0
 */
/*@Intercepts(
    {@Signature(method = "handleResultSets", type = ResultSetHandler.class, args = {Statement.class})}
)
@Component*/
public class ResultInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
      /*  Object target = invocation.getTarget();
        Object[] args = invocation.getArgs();
        // 获取到当前的Statement
        Statement stmt =  (Statement) args[0];*/
        /*if (target instanceof ResultSetHandler) {
            target = new DefaultMapResultSetHandler(stmt);
        }*/
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        try {
            if (target instanceof DefaultResultSetHandler) {
                Executor executor = getValue(target, "executor");
                MappedStatement mappedStatement = getValue(target, "mappedStatement");
                ParameterHandler parameterHandler = getValue(target, "parameterHandler");
                BoundSql boundSql = getValue(target, "boundSql");
                RowBounds rowBounds = getValue(target, "rowBounds");
                ResultHandler resultHandler = getValue(target, "resultHandler");
                ObjectFactory objectFactory = getValue(target, "objectFactory");
                return new ModelBeanResultSetHandler(executor, mappedStatement, parameterHandler,
                        resultHandler, boundSql, rowBounds, objectFactory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Plugin.wrap(target, this);
    }

    private <T> T getValue(Object target, String fieldName) throws Exception {
        List<Field> fieldList = new ArrayList();
        addField(fieldList, target.getClass());
        for (Field field : fieldList) {
            if (field.getName().equals(fieldName)) {
                field.setAccessible(true);
                return (T) field.get(target);
            }
        }
        return null;
    }

    private void addField(List<Field> fieldList, Class<?> clazz) {
        Field[] field = clazz.getDeclaredFields();
        fieldList.addAll(Arrays.asList(field));
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != Object.class) {
            this.addField(fieldList, superClass);
        }
    }
}
