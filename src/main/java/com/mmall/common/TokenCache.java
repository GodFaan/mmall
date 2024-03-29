package com.mmall.common;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @program: mmall
 * @description: 校验秘钥
 * @author: GodFan
 * @create: 2019-06-19 09:58
 **/
public class TokenCache {
    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    public static final String TOKEN_PREFIX="token_";
    /**
     * @Description: 设置本地缓存的初始容量为1000，最大值为10000，最大存活时间为12小时；
     * 若果本地缓存超过10000就会使用LRU算法进行清除，是Least Recently Used的缩写，即最近最少使用算法，
     * @Author: GodFan
     * @Date: 2019/6/19
     */
    private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000)
            .expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
                //默认的数据加载实现，当调用get取值的时候，如果key没有对应的值，就调用这个方法进行加载。
                @Override
                public String load(String key) throws Exception {
                    return null;
                }
            });

    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = localCache.get(key);
            if ("null".equals(value)) {
                return null;
            }
            return value;
        } catch (Exception e) {
            logger.error("loclCache get erro", e);
        }
        return null;
    }
}
