package com.education.common.config;

import com.education.common.cache.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CacheBeanConfiguration {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        RedisSerializer redisSerializer = new FstRedisSerializer();
       // ObjectSerializer objectSerializer = new ObjectSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
     /*   redisTemplate.setHashValueSerializer(objectSerializer);
        redisTemplate.setHashKeySerializer(objectSerializer);*/
        redisTemplate.afterPropertiesSet();

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
      /*  Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);


        ObjectMapper objectMapper = new ObjectMapper();
        //  objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);


        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();*/
        return redisTemplate;
    }


    @Bean
    public CacheBean redisCacheBean(RedisTemplate redisTemplate) {
        return new RedisCacheBean(redisTemplate);
    }

    @Bean
    public CacheManager ehCacheManager(net.sf.ehcache.CacheManager cacheManager) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

    @Bean
    public net.sf.ehcache.CacheManager cacheManager() {
        return net.sf.ehcache.CacheManager.create(this.getClass()
                .getClassLoader()
                .getResourceAsStream("ehcache.xml"));
    }


    @Bean
    public CacheBean ehcacheBean(net.sf.ehcache.CacheManager cacheManager) {
        return new EhcacheBean(cacheManager);
    }

}
