/**
 * 
 */
package com.education.api.config.interceptor;


import com.education.auth.PermissionInterceptor;
import com.education.common.config.OssProperties;
import com.education.common.enums.OssPlatformEnum;

import com.education.business.interceptor.FormLimitInterceptor;
import com.education.business.interceptor.LogInterceptor;
import com.education.business.interceptor.ParamsValidateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * 系统配置
 * @author zengjintao
 * @version 1.0
 * @create_at 2017年9月27日下午8:03:45
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

	@Autowired
	private LogInterceptor logInterceptor;
	@Autowired
	private AdminAuthInterceptor authInterceptor;
	@Autowired
	private ParamsValidateInterceptor paramsValidateInterceptor;
	@Autowired
	private FormLimitInterceptor formLimitInterceptor;
	@Autowired
	private StudentAuthInterceptor studentAuthInterceptor;

	@Resource
	private OssProperties ossProperties;


	//不需要拦截的url
	private static final List<String> noInterceptorUrl = new ArrayList<String>() {
		{
			add("/system/unAuth");
			add("/system/login");
			add("/auth/**");
			add("/api/image");
		}
	};

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor).addPathPatterns("/**");
		registry.addInterceptor(formLimitInterceptor).addPathPatterns("/**");
		registry.addInterceptor(paramsValidateInterceptor).addPathPatterns("/**");
		registry.addInterceptor(authInterceptor)
				.excludePathPatterns(noInterceptorUrl)
				.addPathPatterns("/api/**")
				.addPathPatterns("/system/**");
		registry.addInterceptor(studentAuthInterceptor)
				.excludePathPatterns("/student/login")
				.addPathPatterns("/student/**");
		registry.addInterceptor(new PermissionInterceptor());
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		/*ModelBeanConfig modelBeanConfig = new ModelBeanConfig(jdbcTemplate);
		resolvers.add(new RequestModelBeanBodyMethodArgumentResolver());
		resolvers.add(new RequestModelBeanMethodArgumentResolver());*/
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		 //配置静态资源路径
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		if (OssPlatformEnum.LOCAL.getValue().equals(ossProperties.getPlatform())) {
			//配置文件上传虚拟路径
			registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + ossProperties.getBucketName());
		}
	}
}
