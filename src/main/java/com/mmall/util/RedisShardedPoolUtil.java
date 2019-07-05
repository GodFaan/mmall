package com.mmall.util;

import com.mmall.common.RedisPool;
import com.mmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * @Description：redis连接池工具类常用的API的封装
 * @Author：GodFan
 * @Date2019/7/1 19:58
 * @Version V1.0
 **/
@Slf4j
public class RedisShardedPoolUtil {
    /**
     * @Description:设置key的有效期，单位是秒
     * @Author GodFan
     * @Date 2019/7/1
     * @Version V1.0
     **/
    public static Long expire(String key, int exTime) {
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("set key:{} erro", key, e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        return result;
    }

    /**
     * @Description:设置键值对
     * @Author GodFan
     * @Date 2019/7/1
     * @Version V1.0
     **/
    public static String set(String key, String value) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} erro", key, value, e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        return result;
    }

    /**
     * @Description:设置键值对的过时时间，输入的时间的单位是秒
     * @Author GodFan
     * @Date 2019/7/1
     * @Version V1.0
     **/
    public static String setEx(String key, String value, int exTime) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} erro", key, value, e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        return result;
    }

    /**
     * @Description：通过输入的键来获得对应的值
     * @Author GodFan
     * @Date 2019/7/1
     * @Version V1.0
     **/
    public static String get(String key) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("set key:{} error", key, e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        return result;
    }

    /**
     * @Description:根据输入的key的值删除对应的键值对
     * @Author GodFan
     * @Date 2019/7/1
     * @Version V1.0
     **/
    public static Long del(String key) {
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("set key:{} error", key, e);
            RedisShardedPool.returnBrokenResource(jedis);
            return result;
        }
        return result;
    }

    public static void main(String[] args) {
        ShardedJedis jedis = RedisShardedPool.getJedis();
//        RedisShardedPoolUtil.set("erlangshen", "xiaotianquan");
//        String c = RedisShardedPoolUtil.get("erlangshen");
//        RedisShardedPoolUtil.setEx("wan", "mo", 10 * 10);
//        RedisShardedPoolUtil.expire("erlangshen", 23 * 30);
//        RedisShardedPoolUtil.del("erlangshen");
        for(int i=0;i<15;i++){
            jedis.set("key"+i, "value"+i);
        }
        System.out.println("ok");
    }
}
