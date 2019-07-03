package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;


/**
 * @Description：序列化json对象的一个工具类
 * @Author：GodFan
 * @Date2019/7/1 20:26
 * @Version V1.0
 **/
@Slf4j
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //序列化的操作
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);
        //取消默认转化时间戳（timestamp）形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空的bean转换为json时的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        //所有的日期格式都同一为以下格式
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));
        //忽略在json中存在，但是在java对象中找不到对应属性的情况，放置出现错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
    }

    /**
     * @Description:将非字符串的对象转换为字符串
     * @Author GodFan
     * @Date 2019/7/1
     * @Version V1.0
     **/
    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn(" Parse object to String error", e);
            return null;
        }
    }

    /**
     * @Description:用来封装返回格式化好的字符串对象
     * @Author GodFan
     * @Date 2019/7/1
     * @Version V1.0
     **/
    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn(" Parse object to String error", e);
            return null;
        }
    }

    /**
     * @Description:把一个字符串转化为一个对象
     * @Author GodFan
     * @Date 2019/7/1
     * @Version V1.0
     **/
    public static <T> T String2Object(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object Error", e);
            return null;
        }
    }

    /**
     * @Description:String类型反序列化称为对象，安全的方法
     * @Author GodFan
     * @Date 2019/7/1
     * @Version V1.0
     **/
    public static <T> T String2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (Exception e) {
            log.warn("Parse String to Object Error", e);
            return null;
        }
    }

    /**
     * @Description:String类型反序列化称为对象，安全的方法
     * @Author GodFan
     * @Date 2019/7/1
     * @Version V1.0
     **/
    public static <T> T String2Obj(String str, Class<?> collectionClass, Class<?>... elementClass) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClass);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object Error", e);
            return null;
        }
    }
}
