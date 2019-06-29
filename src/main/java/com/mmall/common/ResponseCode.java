package com.mmall.common;

/**
 * @program: mmall
 * @description: 枚举返回值类型
 * @author: GodFan
 * @create: 2019-06-18 16:36
 **/
public enum ResponseCode {
    /**
     * @Description:成功状态码为0，返回success信息
     * @Author: GodFan
     * @Date: 2019/6/19
     */
    SUCCESS(0, "SUCCESS"),
    /**
     * @Description:失败类型状态码为1；描述信息error
     * @Author: GodFan
     * @Date: 2019/6/19
     */
    ERROR(1, "ERROR"),
    /**
     * @Description:需要注册返回状态码10
     * @Author: GodFan
     * @Date: 2019/6/19
     */
    NEED_LOGIN(10, "NEED_LOGIN"),
    /**
     * @Description:不合理的参数返回状态码2
     * @Author: GodFan
     * @Date: 2019/6/19
     */
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
