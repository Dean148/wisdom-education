package com.education.api;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.education.common.cache.CaffeineCacheBean;
import com.education.common.model.QuestionInfoImport;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.File;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/17 10:21
 */
public class CacheTest {

    @Test
    public void readTxt() {
       /* StringBuilder result = new StringBuilder();
        try{
            File file = new File("F:\\idea\\questionImport.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            QuestionInfo questionInfo = null;
            List<QuestionInfo> questionInfoList = new ArrayList();
            Integer questionType = null;

            while ((s = br.readLine()) != null ) {//使用readLine方法，一次读一行
                ExcelQuestionParser excelQuestionParser = null;
                if (questionType != null) {
                   excelQuestionParser = ExcelQuestionParserManager.build()
                     .createExcelQuestionParser(questionType);
                }
                if (s.startsWith("[题干]")) {
                    questionInfo = new QuestionInfo();
                    questionInfo.setContent(s);
                } else if (s.startsWith("[类型]")) {
                    int questionTypeValueLength = "[类型]".length();
                    String questionTypeName = s.substring(questionTypeValueLength, s.length());
                    for (EnumConstants.QuestionType item : EnumConstants.QuestionType.values()) {
                        if (item.getName().equals(questionTypeName)) {
                           questionType = item.getValue();
                           break;
                        }
                    }
                    questionInfo.setQuestionType(questionType);
                } else if (s.startsWith("[答案]")) {
                    int questionTypeValueLength = "[答案]".length();
                    String content = s.substring(questionTypeValueLength, s.length());
                    String answer = excelQuestionParser.parseAnswerText(content);
                    questionInfo.setAnswer(answer);
                } else if (s.startsWith("[解析]")) {
                    int questionTypeValueLength = "[答案]".length();
                    String content = s.substring(questionTypeValueLength, s.length());
                    String optionText = excelQuestionParser.parseOptionText(content);
                    questionInfo.setAnalysis(optionText);
                    questionInfoList.add(questionInfo);
                }
                // result.append(System.lineSeparator()+s);
            }
            br.close();
            System.out.println(questionInfoList);
            System.out.println(result.toString().split("\n\n"));
        }catch(Exception e){
            e.printStackTrace();
        }*/
    }

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


    static int dataBase = 0;// 数据库阅读量0

 //   private static ThreadLocal<Integer> map = new ThreadLocal<>();
    public static void main(String[] args) throws InterruptedException {
     //   ApplicationContext application = SpringApplication.run(Application11.class, args);
      //  RedisTemplate redisTemplate = (RedisTemplate) application.getBean("redisTemplate");
     //   RedisUtil redisUtil = new RedisUtil(redisTemplate);
     //   redisUtil.del("test");
     //   redisUtil.set("test", 0);



    //    map.set(10);
     //   System.out.println("main" + map.get());

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (true) {
                    // Thread.sleep(100);
                    int read = 0;// (int) redisUtil.get("test");
                    if (read > 0) {
                     //   redisUtil.decr("test", read);
                        dataBase += read;
                    }
                    System.err.println(dataBase);
                }
            }
        }).start();


       /* Thread.sleep(2000);
        for (int i = 0 ; i< 10000; i++) {
            MyThread myThread = new MyThread(redisUtil, 1);
            myThread.start();
        }*/
    }

    static class MyThread extends Thread {

       /* private int i = 0;
        private RedisUtil redisUtil;
        MyThread(RedisUtil redisUtil, int i) {
            this.i = i;
            this.redisUtil = redisUtil;
        }

        @Override
        public void run() {
            // System.err.println(Thread.currentThread().getName() + ": " + i);
            redisUtil.incr("test", i);
            // redisUtil.set("test", (Integer)redisUtil.get("test") + i);
        }*/
    }
}
