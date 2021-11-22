package com.education.common.config;import cn.hutool.core.lang.Assert;import com.education.common.constants.AuthConstants;import com.education.common.enums.OssPlatformEnum;import com.education.common.model.JwtToken;import com.education.common.model.WeChatInfo;import com.education.common.upload.FileUpload;import com.education.common.upload.LocalFileUpload;import com.education.common.upload.TencentOssFileUpload;import com.education.common.utils.ObjectUtils;import com.jfinal.weixin.sdk.api.ApiConfig;import com.jfinal.weixin.sdk.api.ApiConfigKit;import org.hibernate.validator.HibernateValidator;import org.redisson.Redisson;import org.redisson.api.RedissonClient;import org.redisson.config.Config;import org.redisson.config.SingleServerConfig;import org.springframework.beans.factory.annotation.Value;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import javax.annotation.Resource;import javax.validation.Validation;import javax.validation.Validator;import javax.validation.ValidatorFactory;/** * 系统bean 配置 * @author zengjintao * @create 2019/3/29 14:01 * @since 1.0 **/@Configurationpublic class BeanConfiguration {    @Bean    public JwtToken JwtToken() {        return new JwtToken(AuthConstants.EDUCATION_SECRET_KEY);    }    @Value("${weChat.appId}")    private String appId;    @Value("${weChat.token}")    private String token;    @Value("${weChat.appSecret}")    private String appSecret;    @Value("${spring.redis.host}")    private String redisUrl;    @Value("${spring.redis.port}")    private Integer redisPort = 6379;    @Value("${spring.redis.password}")    private String redisPassword;    @Bean    public WeChatInfo weChatInfo() {        WeChatInfo weChatInfo = new WeChatInfo();        weChatInfo.setAppId(appId);        weChatInfo.setToken(token);        weChatInfo.setAppSecret(appSecret);        return weChatInfo;    }    @Bean    public Validator validator(){        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )                .configure()                .addProperty( "hibernate.validator.fail_fast", "true" ) // 配置校验失败立即停止                .buildValidatorFactory();        Validator validator = validatorFactory.getValidator();        return validator;    }    @Bean    public ApiConfig apiConfig(WeChatInfo weChatInfo) {        ApiConfig apiConfig = new ApiConfig();        apiConfig.setAppId(weChatInfo.getAppId());        apiConfig.setAppSecret(weChatInfo.getAppSecret());        ApiConfigKit.setThreadLocalAppId(weChatInfo.getAppId());        ApiConfigKit.putApiConfig(apiConfig);        return apiConfig;    }    @Bean    public RedissonClient redissonClient() {        Config config = new Config();        SingleServerConfig serverConfig = config.useSingleServer()                .setAddress("redis://" + redisUrl + ":" + redisPort);        if (ObjectUtils.isNotEmpty(redisPassword)) {           serverConfig.setPassword(redisPassword);        }        return Redisson.create(config);    }    @Resource    private OssProperties ossProperties;    @Value("spring.profiles.active")    private String env;    @Value("spring.application.name")    private String applicationName;    /**     * 文件上传配置     * @return     */    @Bean    public FileUpload fileUpload() {        String platform = ossProperties.getPlatform();        Assert.notBlank(platform, () -> new RuntimeException("platform can not be null or empty!"));        if (platform.equals(OssPlatformEnum.LOCAL.getValue())) {            return new LocalFileUpload(ossProperties, applicationName);        } else if (platform.equals(OssPlatformEnum.LOCAL.getValue())) {            return new TencentOssFileUpload(ossProperties, env, applicationName);        }        throw new RuntimeException("can not init FileUpload Bean!");    }}