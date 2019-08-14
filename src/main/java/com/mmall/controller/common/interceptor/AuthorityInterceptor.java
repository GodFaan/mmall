package com.mmall.controller.common.interceptor;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description：身份认证拦截器
 * @Author：GodFan
 * @Date2019/7/6 16:01
 * @Version V1.0
 **/
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        //请求Controller中的方法名
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //解析HandlerMethod
        String methodName = handlerMethod.getMethod().getName();
        //只获取类的名字
        String className = handlerMethod.getClass().getSimpleName();
        //解析参数，具体的key和value可以通过日志来获取
        StringBuffer requestParamBuffer = new StringBuffer();
        Map paramMap = request.getParameterMap();
        Iterator it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String mapKey = (String) entry.getKey();
            String mapValue = StringUtils.EMPTY;
            //request这个参数的map，里面的value返回的是一个String[]
            Object obj = entry.getValue();
            if (obj instanceof String[]) {
                String[] strs = (String[]) obj;
                mapValue = Arrays.toString(strs);
            }
            requestParamBuffer.append(mapKey).append("=").append(mapValue);
        }
        if (StringUtils.equals(className, "UserManageController") && StringUtils.equals(methodName, "login")) {
            log.info("权限拦截器拦截到请求，classname：{},methodname ：{}", className, methodName);
            //如果拦截到登录请求，不打印参数，因为参数里面有密码，会打印到日志中，防止日志的泄露
            return true;
        }
        log.info("权限拦截器拦截到请求，classaName:{},methodName:{},param:{}", className, methodName, paramMap);
        User user = null;
        String loginToken = CookieUtil.readLoginToken(request);
        if (StringUtils.isNotEmpty(loginToken)) {
            String userJsonStr = RedisShardedPoolUtil.get(loginToken);
            user = JsonUtil.String2Obj(userJsonStr, User.class);
        }
        if (user == null || (user.getRole().intValue() != Const.Role.ROLE_ADMIN)) {
            //返回FALSE，即不会调用controller里的方法
            //此处必须添加reset方法，否则报异常
            response.reset();
            //设之编码，否则乱码
            response.setCharacterEncoding("UTF-8");
            //因为全部是json接口，所以要设置返回值 的类型
            response.setContentType("application/json;chasrset=UTF-8");
            PrintWriter out = response.getWriter();
            //上传的内容因为富文本的控件的 要求，所以要将返回值进行特殊的处理。还需要区分是否登录账户以及是否有上传操作的权限
            if (user == null) {
                if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtextImagUpload")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "请登录管理员！");
                    out.print(JsonUtil.obj2String(resultMap));
                } else {

                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截，用户未登录！")));
                }
            } else {
                if (StringUtils.equals(className, "ProductManageController") && StringUtils.equals(methodName, "richtextImagUpload")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "无权限操作！");
                    out.print(JsonUtil.obj2String(resultMap));
                } else {

                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截，用户无权限！")));
                }
            }
            //清空流中的数据
            out.flush();
            //关闭流
            out.close();
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
