package com.mmall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.*;

/**
 * @Description：操作cookie的工具类
 * @Author：GodFan
 * @Date2019/7/1 23:22
 * @Version V1.0
 **/
@Slf4j
public class CookieUtil {
    //设置cookie的作用域，以自定义的网址为结束
    private final static String COOKIE_DOMAIN = "itgodfan.com";//值得注意的是，在使用tomcat8.5 以后的版本时，不能加"."，否则出错
    private final static String COOKIE_NAME = "mmall_login_token";

    /**
     * @Description:读取cookie中的值，cookie的存储方式是以键值对的形式存储的
     * @Author GodFan
     * @Date 2019/7/3
     * @Version V1.0
     **/
    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                log.info("read cookieName:{},cookieValue:{}", ck.getName(), ck.getValue());
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    log.info("return cookieName :{},cookieValue:{}", ck.getName(), ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    /**
     * @Description:写入cookie的工具类
     * @Author GodFan
     * @Date 2019/7/1
     * @Version V1.0
     **/
    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        //禁止通过http脚本访问cookie，增加安全性，防止脚本攻击带来的信息泄露的风险，提高一定的安全性，不能完全保证防止脚本攻击
        ck.setHttpOnly(true);
        //设置目录在当前目录下
        ck.setPath("/");
        //设置cookie的存活时间，单位是秒；
        //如果这个不设置的话，cookie就不会被写入硬盘中，而是写在内存中，只在当前页面有效；
        //如果是-1，代表永久有效
        ck.setMaxAge(60 * 60 * 24 * 365);
        log.info("write cookie:{},cookieValaue:{}", ck.getName(), ck.getValue());
        response.addCookie(ck);
    }

    /**
     * @Description:删除cookie
     * @Author GodFan
     * @Date 2019/7/3
     * @Version V1.0
     **/
    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (StringUtils.equals(ck.getName(), COOKIE_NAME)) {
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//设置成0，代表删除此cookie
                    log.info("del cookieName:{} ,ckookieValue:{}", ck.getName(), ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }

}
