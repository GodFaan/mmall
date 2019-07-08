package com.mmall.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description：设置一个全局处理异常的类
 * @Author：GodFan
 * @Date2019/7/6 15:30
 * @Version V1.0
 **/
@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //这一行的日志一定要打印，否则在后台看不到对应的异常
        log.error("{} Exception", request.getRequestURI(), ex);
        //当使用Jackson2.x时，使用MappingJackson2JsonView，因为本项目使用的是1.9。
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());
        modelAndView.addObject("status", ResponseCode.ERROR.getCode());
        modelAndView.addObject("msg", "接口异常，详情查看服务端的日志");
        modelAndView.addObject("data", ex.toString());
        return modelAndView;
    }
}
