package com.education.core.api;

import com.education.common.utils.Result;
import com.education.common.utils.SpellUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/11/21 19:11
 */
@RestController
public class ApiController {

    /**
     * 获取汉字拼音
     * @param keyWord
     * @return
     */
    @GetMapping("/api/getSpell")
    public Result getSpell(@RequestParam(defaultValue = "") String keyWord) {
        return Result.success(SpellUtils.getSpellHeadChar(keyWord));
    }
}
