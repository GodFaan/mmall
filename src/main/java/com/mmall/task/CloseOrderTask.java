package com.mmall.task;

import com.github.pagehelper.StringUtil;
import com.mmall.common.Const;
import com.mmall.service.IOrderService;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @Description;定时关单操作
 * @Author：GodFan
 * @Date2019/7/8 15:25
 * @Version V1.0
 **/
@Component
@Slf4j
public class CloseOrderTask {
    @Autowired
    private IOrderService iOrderService;

    //@Scheduled(cron = "0 */1 * * * ?")//每一分钟（每分钟的整数倍）
    public void closeOrderTaskV1() {
        log.info("关闭订单定时任务启动！");
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.oredr.task.time.hour", "2"));
        iOrderService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

    // @Scheduled(cron = "0 */1 * * * ?")//每一分钟（每分钟的整数倍）
    public void closeOrderTaskV2() {
        log.info("关闭订单定时任务启动！");
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "50000"));
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
        if (setnxResult != null && setnxResult.intValue() == 1) {
            //如果返回的值为1，代表设置成功，获取锁
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            log.info("没有或得分布式锁：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }
        log.info("关闭订单定时任务结束");
    }

    //springschedule(在指定的时间间隔内进行轮询操作，完成相关的任务)
    @Scheduled(cron = "0 */1 * * * ?")//每一分钟（每分钟的整数倍）
    public void closeOrderTaskV3() {
        log.info("关闭订单定时任务启动！");
        //获取设置的超时时间
        long lockTimeout = Long.parseLong(PropertiesUtil.getProperty("lock.timeout", "5000"));
        //在redis缓存中设置一个键值对（分布式锁=锁的过期时间），如果这个键值对可以设置成功，就表明可以或得分布式的锁，也就是在指定的时间可以进行关单操作
        Long setnxResult = RedisShardedPoolUtil.setnx(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
        //如果获取了分布式锁，则执行关单业务
        if (setnxResult != null && setnxResult.intValue() == 1) {
            //如果返回的值为1，代表设置成功，获取锁
            closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        } else {
            String lockValue1 = RedisShardedPoolUtil.get(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
            //查到锁的值与当前的时间进行对比查看前人获取的锁是否已经过时，若超时则重新获取锁
            if (lockValue1 != null && System.currentTimeMillis() > Long.valueOf(lockValue1)) {
                //通过当前的时间戳getset操作会给对应的key设置新的值并将旧的值进行返回，这是一个原子的操作
                //当redis返回nil的时候，说明返回值无效，此时也可以获取锁
                String lockValue2 = RedisShardedPoolUtil.getset(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, String.valueOf(System.currentTimeMillis() + lockTimeout));
                if (lockValue2 == null || (lockValue2 != null && StringUtils.equals(lockValue1, lockValue2))) {
                    //真正获取锁成功
                    closeOrder(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                } else {
                    log.info("木有获取到分布式锁啊朋友！：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
                }
            }
            log.info("木有获取到分布式锁啊朋友！：{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        }
        log.info("关闭订单定时任务结束======================================");
    }

    private void closeOrder(String lockName) {
        RedisShardedPoolUtil.expire(lockName, 5);//设置有效期为5秒，防止死锁
        log.info("获取{},threadName:{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        int hour = Integer.parseInt(PropertiesUtil.getProperty("close.oredr.task.time.hour", "2"));
        iOrderService.closeOrder(hour);
        RedisShardedPoolUtil.del(Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK);
        log.info("释放{},Threadname{}", Const.REDIS_LOCK.CLOSE_ORDER_TASK_LOCK, Thread.currentThread().getName());
        log.info("===============================================");
    }
}
