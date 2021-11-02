package com.education.model.entity;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.education.common.utils.ObjectUtils;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author zengjintao
 * @create_at 2021年11月2日 0002 17:53
 * @since version 1.6.5
 */
@TableName("website_config")
public class WebsiteConfig extends BaseEntity<WebsiteConfig> {

    @TableField("logo_url")
    @NotBlank(message = "请上传网站logo")
    private String logoUrl;

    @NotBlank(message = "请上传网站轮播图")
    @TableField("carousel_image")
    private String carouselImage;

    @TableField(exist = false)
    private List<String> carouselImageList;

    public void setCarouselImageList(List<String> carouselImageList) {
        this.carouselImageList = carouselImageList;
    }

    public List<String> getCarouselImageList() {
        if (StrUtil.isNotBlank(carouselImage)) {
            return ObjectUtils.spiltToList(carouselImage);
        }
        return carouselImageList;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCarouselImage() {
        return carouselImage;
    }

    public void setCarouselImage(String carouselImage) {
        this.carouselImage = carouselImage;
    }
}
