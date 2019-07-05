package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Description:前台用户控制
 * @Author: GodFan
 * @Date: 2019/6/18
 */
@Controller
@RequestMapping("/user/springsession/")
//使用springsessions是为了减少入侵性
public class UserSpringSessionController {
    @Autowired
    private IUserService iUserService;

    /**
     * @Description:登录
     * @Author: GodFan
     * @Date: 2019/6/19
     */
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    /***
     * 自动将返回值序列化成json
     */
    @ResponseBody
    public Object login(String username, String password, HttpSession session, HttpServletResponse httpServletResponse) {
        ServerResponse<User> response = iUserService.login(username, password);
        if (response.isSuccess()) {

            session.setAttribute(Const.CURRENT_USER, response.getData());
            //登陆成功后将登陆信息存入cookie中，以便于下次操作时提取数据
//            CookieUtil.writeLoginToken(httpServletResponse, session.getId());
//            RedisShardedPoolUtil.setEx(session.getId(), JsonUtil.obj2String(response.getData()), Const.RedisCacheExtime.REDIS_SESSION_EXTIME);
        }
        return response;
    }

    /**
     * @Description:退出登录
     * @Author: GodFan
     * @Date: 2019/6/19
     */
    @RequestMapping(value = "logout.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession session) {
        session.removeAttribute(Const.CURRENT_USER);
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        CookieUtil.delLoginToken(httpServletRequest, httpServletResponse);
//        RedisShardedPoolUtil.del(loginToken);

        return ServerResponse.createBySuccess();
    }


    /**
     * @Description: 获得当前登录用户的所有信息
     * @Author: GodFan
     * @Date: 2019/6/19
     */
    @RequestMapping(value = "get_user_info.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpServletRequest httpServletRequest, HttpSession session) {
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("当前用户未登录，无法获取当前用户的信息！");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.String2Obj(userJsonStr, User.class);
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("当前用户未登录，无法获取当前用户的信息！");
    }

}