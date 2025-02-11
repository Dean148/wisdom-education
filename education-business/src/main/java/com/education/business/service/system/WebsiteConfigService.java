package com.education.business.service.system;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.education.business.mapper.system.WebsiteConfigMapper;
import com.education.business.service.BaseService;
import com.education.common.exception.BusinessException;
import com.education.common.utils.ObjectUtils;
import com.education.model.dto.WebsiteConfigDto;
import com.education.model.entity.WebsiteConfig;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public boolean saveOrUpdate(WebsiteConfigDto websiteConfig) {
        List<String> list = websiteConfig.getCarouselImageList();
        if (list.size() > 3) {
            throw new BusinessException("最多只能上传三张轮播图");
        }

        String carouselImage = websiteConfig.getCarouselImage();
        if (StrUtil.isNotBlank(carouselImage)) {
            list = ObjectUtils.spiltToList(carouselImage);
        }
        StringBuilder carouselImageStr = new StringBuilder();
        list.forEach(image -> carouselImageStr.append(image).append(StrUtil.COMMA));
        websiteConfig.setCarouselImage(carouselImage.substring(0, carouselImage.length() - 1));
        return super.saveOrUpdate(websiteConfig);
    }
}
