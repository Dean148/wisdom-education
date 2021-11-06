package com.education.business.service.system;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.business.mapper.system.WebsiteConfigMapper;
import com.education.business.service.BaseService;
import com.education.model.entity.WebsiteConfig;
import org.springframework.stereotype.Service;

/**
 * 网站设置
 * @author zengjintao
 * @create_at 2021年11月2日 0002 17:55
 * @since version 1.6.5
 */
@Service
public class WebsiteConfigService extends BaseService<WebsiteConfigMapper, WebsiteConfig> {

    public WebsiteConfig getWebSiteConfig() {
        return super.selectFirst(new QueryWrapper<>());
    }
}
