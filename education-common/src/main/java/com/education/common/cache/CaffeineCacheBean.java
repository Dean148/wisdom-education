package com.education.common.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 基于Caffeine 实现的本地缓存
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/12/17 10:30
 */
public class CaffeineCacheBean implements CacheBean {

    private final Map<String, Cache> cacheMap = new ConcurrentHashMap<>();

    private static final String MAIN_CACHE = "main_cache";

    private static final int NO_EXPIRE = 0;


    @Override
    public <T> T get(String cacheName, Object key) {
        Cache cache = getCache(cacheName, NO_EXPIRE, null);
        return (T) cache.getIfPresent(key);
    }

    private Cache getCache(String cacheName, int liveSeconds, TimeUnit timeUnit) {
        Cache cache = cacheMap.get(cacheName);
        if (cache == null) {
            synchronized (CaffeineCacheBean.class) {
                cache = cacheMap.get(cacheName);
                if (cache == null) {
                    if (liveSeconds == 0) {
                        cache = this.createCache();
                    } else {
                        cache = this.createCache(liveSeconds, timeUnit);
                    }
                    cacheMap.put(cacheName, cache);
                }
            }
        }
        return cache;
    }

    @Override
    public <T> T get(Object key) {
        Cache cache = getCache(MAIN_CACHE, NO_EXPIRE, null);
        return (T) cache.getIfPresent(key);
    }

    @Override
    public void put(String cacheName, Object key, Object value) {
        Cache cache = getCache(cacheName, NO_EXPIRE, null);
        cache.put(key, value);
    }

    @Override
    public void put(Object key, Object value) {
        Cache cache = getCache(MAIN_CACHE, NO_EXPIRE, null);
        cache.put(key, value);
    }

    @Override
    public void put(Object key, Object value, int liveSeconds) {
        Cache cache = getCache(MAIN_CACHE, liveSeconds, TimeUnit.SECONDS);
        cache.put(key, value);
    }

    @Override
    public void put(String cacheName, Object key, Object value, int liveSeconds) {
        Cache cache = getCache(cacheName, liveSeconds, TimeUnit.SECONDS);
        cache.put(key, value);
    }

    @Override
    public void put(String cacheName, Object key, Object value, int liveSeconds, TimeUnit timeUnit) {
        Cache cache = getCache(cacheName, liveSeconds, timeUnit);
        cache.put(key, value);
    }

    @Override
    public void put(Object key, Object value, int liveSeconds, TimeUnit timeUnit) {
        Cache cache = getCache(MAIN_CACHE, liveSeconds, timeUnit);
        cache.put(key, value);
    }

    @Override
    public Collection getKeys(String cacheName) {
        Cache cache = getCache(MAIN_CACHE, 0, null);
        return cache.asMap().keySet();
    }

    @Override
    public Collection getKeys() {
        return null;
    }

    @Override
    public void remove(Object key) {
        Cache cache = getCache(MAIN_CACHE, 0, null);
        cache.invalidate(key);
    }

    @Override
    public void remove() {
        Set<String> keys = this.cacheMap.keySet();
        keys.forEach(key -> {
            this.cacheMap.remove(key);
        });
    }

    @Override
    public void remove(String cacheName, Object key) {
        Cache cache = getCache(cacheName, 0, null);
        cache.invalidate(key);
    }

    @Override
    public void removeAll(String cacheName) {
        Cache cache = getCache(cacheName, 0, null);
        cache.invalidateAll();
    }

    private Caffeine createCaffeine() {
        return Caffeine.newBuilder();
    }

    private Cache createCache() {
        return createCaffeine().build();
    }

    private Cache createCache(long duration, TimeUnit timeUnit) {
        return createCaffeine().expireAfterWrite(duration, timeUnit).build();
    }
}
