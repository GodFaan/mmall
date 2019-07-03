package com.mmall.controller.common;

import com.mmall.common.Const;
import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisPoolUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description：
 * @Author：GodFan
 * @Date2019/7/3 10:01
 * @Version V1.0
 **/
public class SessionExpireFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isNotEmpty(loginToken)) {
            //判断logintoken是否为空或者“”；
            //如果不为空，则符合条件，继续拿user的信息
            String userJsonStr = RedisPoolUtil.get(loginToken);
            User user = JsonUtil.String2Obj(userJsonStr, User.class);
            if (user != null) {
                //若user不为空，则重置session的时间，调用expire命令
                RedisPoolUtil.expire(loginToken, Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
