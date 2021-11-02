package com.education.api.controller.admin.system;

import cn.hutool.core.util.StrUtil;
import com.education.business.service.system.WebsiteConfigService;
import com.education.business.webSocket.WebSocketConfig;
import com.education.common.base.BaseController;
import com.education.common.exception.BusinessException;
import com.education.common.utils.ObjectUtils;
import com.education.common.utils.Result;
import com.education.model.entity.WebsiteConfig;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
        List<String> list = websiteConfig.getCarouselImageList();
        if (list.size() > 3) {
            throw new BusinessException("最多只能上传三张轮播图");
        }
        StringBuilder carouselImage = new StringBuilder();
        for (String image : list) {
            carouselImage.append(image).append(StrUtil.COMMA);
        }
        websiteConfig.setCarouselImage(carouselImage.substring(0, carouselImage.length() - 1));
        websiteConfigService.saveOrUpdate(websiteConfig);
        return Result.success();
    }
}
