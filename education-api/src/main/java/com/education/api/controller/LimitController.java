package com.education.api.controller;

import com.education.common.disabled.RateLimitLock;
import com.education.common.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 限流测试接口
 * @author zengjintao
 * @version 1.0
 * @create_date 2020/6/12 10:41
 * @since 1.0.0
 */
@RestController
@RequestMapping("/limit")
public class LimitController {

    @GetMapping
    @RateLimitLock(limit = 20)
    public Result limit() {
        return Result.success("访问接口");
    }
    @GetMapping("submit")
    public Result submit(@RequestParam Map params) {
        System.out.println("执行submit方法保存数据" + params);
        return Result.success("success");
    }
}
