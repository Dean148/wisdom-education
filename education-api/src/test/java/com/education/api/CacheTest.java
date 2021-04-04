package com.education.api;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.education.common.cache.CaffeineCacheBean;
import com.education.common.model.QuestionInfoImport;
import com.jfinal.kit.HttpKit;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/17 10:21
 */
public class CacheTest {

    public static void main(String[] args) {

        Map headers = new HashMap<>();
        headers.put("token", "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MTc0Mjc1ODgsInN1YiI6Ijg5IiwiZXhwIjoxNjE3NDM0Nzg4fQ.tmMFkRuVjP4cX3D5nTpl6gkGhq_Qf9js5BF5XDXdyr0");
        headers.put("Content-type", "application/json");
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                String params = "{\n" +
                        " \"examTime\":5,\n" +
                        " \"testPaperInfoId\":9,\n" +
                        " \"questionAnswerList\":\n" +
                        "  [\n" +
                        "\t  {\n" +
                        "\t\t  \"questionInfoId\":1750,\n" +
                        "\t\t  \"answer\":\"<p>是多少</p>\",\n" +
                        "\t\t  \"questionType\":3,\n" +
                        "\t\t  \"questionMark\":50,\n" +
                        "\t\t  \"studentAnswer\":\"\"\n" +
                        "\t  },\n" +
                        "\t  {\n" +
                        "\t\t  \"questionInfoId\":1748,\n" +
                        "\t\t  \"answer\":\"B,C\",\n" +
                        "\t\t  \"questionType\":2,\n" +
                        "\t\t  \"questionMark\":50,\n" +
                        "\t\t  \"studentAnswer\":\"A,B\"\n" +
                        "\t  }\n" +
                        "  ]\n" +
                        "}";
                long start = System.currentTimeMillis();
                String content = HttpKit.post("http://127.0.0.1/student/testPaperInfo/commitPaper", params, headers);
                System.err.println("耗时:" + (System.currentTimeMillis() - start));
            }).start();
        }
    }

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
}
