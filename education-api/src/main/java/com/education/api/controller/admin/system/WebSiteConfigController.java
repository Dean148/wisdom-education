package com.education.api.controller.admin.system;

import com.education.business.service.system.WebsiteConfigService;
import com.education.common.base.BaseController;
import com.education.common.utils.Result;
import com.education.model.entity.WebsiteConfig;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @author zengjintao
 * @create_at 2021/11/2 19:24
 * @since version 1.0.3
 */
@RestController
@RequestMapping("/system/webSite")
public class WebSiteConfigController extends BaseController {

    @Resource
    private WebsiteConfigService websiteConfigService;

    @GetMapping
    public Result getWebSiteConfig() {
        return Result.success(websiteConfigService.getWebSiteConfig());
    }

    @PostMapping
    public Result saveOrUpdate(@RequestBody WebsiteConfig websiteConfig) {
        websiteConfigService.saveOrUpdate(websiteConfig);
        return Result.success();
    }
}
