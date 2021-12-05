package com.education.api.controller.student;

import com.education.business.service.system.WebsiteConfigService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zengjintao
 * @create_at 2021/11/2 21:01
 * @since version 1.0.3
 */
@RestController("student-webSite")
@RequestMapping("/student/webSite")
public class WebSiteConfigController extends BaseController {

    @Resource
    private WebsiteConfigService websiteConfigService;

    @GetMapping
    public Result getWebSiteConfig() {
        return Result.success(websiteConfigService.getWebSiteConfig());
    }
}
