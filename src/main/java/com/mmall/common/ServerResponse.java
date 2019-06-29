package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * @program: 返回值情况
 * @description: 响应
 * @author: GodFan
 * @create: 2019-06-18 16:22
 **/
//保证序列化json的时候，如果是null对象，key也会消失
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)

public class ServerResponse<T> implements Serializable {
    //状态码
    private int status;
    //注释信息
    private String msg;
    //参数信息
    private T data;

    private ServerResponse(int status) {

        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    /*序列化之后不再显示，使之不再序列化的结果之中*/
    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }
    /**
     * @Description: 成功时
     * @Author: GodFan
     * @Date: 2019/6/18
     */
    /**
     * @Description: 不传入参数时
     * @Author: GodFan
     * @Date: 2019/6/18
     */
    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    /**
     * @Description: 传入string类型的参数时
     * @Author: GodFan
     * @Date: 2019/6/18
     */
    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }

    /**
     * @Description: 传入泛型数据
     * @Author: GodFan
     * @Date: 2019/6/18
     */
    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }

    /**
     * @Description: 传入string类型和泛型类型的数据时
     * @Author: GodFan
     * @Date: 2019/6/18
     */
    public static <T> ServerResponse<T> createBySuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * @Description: 失败时
     * @Author: GodFan
     * @Date: 2019/6/18
     */
    /**
     * @Description: 没有输入参数时
     * @Author: GodFan
     * @Date: 2019/6/18
     */
    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    /**
     * @Description: 输入错误信息
     * @Author: GodFan
     * @Date: 2019/6/18
     */
    public static <T> ServerResponse<T> createByErrorMessage(String errorMessage) {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(), errorMessage);
    }

    /**
     * @Description: 输入错误码，错误信息
     * @Author: GodFan
     * @Date: 2019/6/18
     */
    public static <T> ServerResponse<T> createByErrorCodeMessage(int erroCode, String errorMessage) {
        return new ServerResponse<>(erroCode, errorMessage);
    }
}
