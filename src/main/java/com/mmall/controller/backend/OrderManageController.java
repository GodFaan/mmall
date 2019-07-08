package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import com.mmall.vo.OrderVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Description：后台管理
 * @Author：GodFan
 * @Date2019/6/26 14:59
 * @Version V1.0
 **/
@Controller
@RequestMapping("/manage/order")
public class OrderManageController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpServletRequest httpServletRequest, @RequestParam(value = "pagenum", defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("当前用户未登录，无法获取当前用户的信息！");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.String2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员账户");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //填充增加业务的逻辑
//            return iOrderService.manageList(pageNum, pageSize);
//        } else {
//            return ServerResponse.createByErrorMessage("无操作权限！");
//        }
        //将所有的用户登录与权限判断交给拦截器
        return iOrderService.manageList(pageNum, pageSize);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(HttpServletRequest httpServletRequest, Long orderNo) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("当前用户未登录，无法获取当前用户的信息！");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.String2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员账户");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //填充增加业务的逻辑
//            return iOrderService.manageDetail(orderNo);
//        } else {
//            return ServerResponse.createByErrorMessage("无操作权限！");
//        }
        //将所有的用户登录与权限判断交给拦截器
        return iOrderService.manageDetail(orderNo);
    }

    /**
     * @Description:后台搜索信息
     * @Author GodFan
     * @Date 2019/6/26
     * @Version V1.0
     **/
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(HttpServletRequest httpServletRequest, Long orderNo, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum
            , @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("当前用户未登录，无法获取当前用户的信息！");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.String2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员账户");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //填充增加业务的逻辑
//            return iOrderService.manageSearch(orderNo, pageNum, pageSize);
//        } else {
//            return ServerResponse.createByErrorMessage("无操作权限！");
//        }
        //将所有的用户登录与权限判断交给拦截器
        return iOrderService.manageSearch(orderNo, pageNum, pageSize);
    }

    /**
     * @Description:根据订单的状态进行发货操作
     * @Author GodFan
     * @Date 2019/6/26
     * @Version V1.0
     **/
    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> OrderSendGoods(HttpServletRequest httpServletRequest, Long orderNo) {
//        User user = (User) session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if (StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("当前用户未登录，无法获取当前用户的信息！");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.String2Obj(userJsonStr, User.class);
//        if (user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录管理员账户");
//        }
//        if (iUserService.checkAdminRole(user).isSuccess()) {
//            //填充增加业务的逻辑
//            return iOrderService.manageSendGoods(orderNo);
//        } else {
//            return ServerResponse.createByErrorMessage("无操作权限！");
//        }
        //将所有的用户登录与权限判断交给拦截器
        return iOrderService.manageSendGoods(orderNo);
    }

}
