package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @Description：redis连接池工具类常用的API的封装
 * @Author：GodFan
 * @Date2019/7/1 19:58
 * @Version V1.0
 **/
@Slf4j
public class RedisPoolUtil {
    /**
     * @Description:设置key的有效期，单位是秒
     * @Author GodFan
     * @Date 2019/7/1
     * @Version V1.0
     **/
    public static Long expire(String key, int exTime) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("set key:{} erro", key, e);
            RedisPool.returnBrokenResource(jedis);
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
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} erro", key, value, e);
            RedisPool.returnBrokenResource(jedis);
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
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} erro", key, value, e);
            RedisPool.returnBrokenResource(jedis);
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
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("set key:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
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
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("set key:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        return result;
    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getJedis();
        RedisPoolUtil.set("erlangshen", "xiaotianquan");
        String c = RedisPoolUtil.get("erlangshen");
        RedisPoolUtil.setEx("wan", "mo", 10 * 10);
        RedisPoolUtil.expire("erlangshen", 23 * 30);
        RedisPoolUtil.del("erlangshen");
        System.out.println("ok");
    }
}
