package com.education.api;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.education.common.cache.CaffeineCacheBean;
import com.education.common.model.QuestionInfoImport;
import org.junit.Test;
import java.io.File;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/17 10:21
 */
public class CacheTest {

    @Test
    public void poiTest() throws Exception {
        File file = new File("F:\\idea\\test.xlsx");
        ImportParams importParams = new ImportParams();
        importParams.setNeedVerfiy(true); // 设置需要校验
        ExcelImportResult<QuestionInfoImport> result = ExcelImportUtil.importExcelMore(file,
                QuestionInfoImport.class, importParams);
    }

    @Test
    public void testParserToken() {
        String value = "{十点多4554}";
        value = value.substring("{".length(), value.length() - "|".length());
        System.out.println(value);
        System.out.println();
      //  System.out.println(new DefaultQuestionParser().parserToken("${十点多}, ${{十点多4554}}, ${十点sdsds多4554}") );
    }

    @Test
    public void cache() throws InterruptedException {
        /*Cache cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.SECONDS)
                .build();

        cache.put("test", "test1");

        System.out.println(cache.getIfPresent("test"));

        Thread.sleep(5000);
        System.out.println(cache.getIfPresent("test"));

        Thread.sleep(6000);
        System.out.println(cache.getIfPresent("test"));*/

      /*  CaffeineCacheElement caffeineCacheElement = new CaffeineCacheElement("1");
        caffeineCacheElement.setCreateTime(new Date());

        caffeineCacheElement.setLiveSeconds(10);

        Thread.sleep(8000);
        System.out.println(caffeineCacheElement.isTimeOut());*/

        CaffeineCacheBean caffeineCacheBean = new CaffeineCacheBean();
        caffeineCacheBean.put("test", 11, 6);
        Thread.sleep(6010);
        System.out.println((Object) caffeineCacheBean.get("test"));
    }
}
