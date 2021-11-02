package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @author zengjintao
 * @create_at 2021年11月2日 0002 17:53
 * @since version 1.6.5
 */
@TableName("WebsiteConfig")
public class WebsiteConfig extends BaseEntity<WebsiteConfig> {

    @TableId("logo_url")
    private String logoUrl;
    @TableId("carousel_image")
    private String carouselImage;
}
