package com.education.common.base;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.education.common.cache.CacheBean;
import com.education.common.exception.BusinessException;
import com.education.common.model.StudentInfoImport;
import com.education.common.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/3/8 11:33
 */
public abstract class BaseController {


    @Resource(name = "redisCacheBean")
    protected CacheBean cacheBean;
    @Resource
    protected CacheBean ehcacheBean;
    @Value("file.uploadPath")
    protected String baseUploadPath;

    protected static final Set<String> excelTypes = new HashSet<String>() {
        {
            add("application/x-xls");
            add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            add("application/vnd.ms-excel");
            add("text/xml");
        }
    };

    protected static final Set<String> textTypes = new HashSet<String>() {
        {
            add("text/plain");
        }
    };


    protected void importExcel(InputStream inputstream,
                               Class<?> pojoClass,
                               ImportParams params) {
    }

}
